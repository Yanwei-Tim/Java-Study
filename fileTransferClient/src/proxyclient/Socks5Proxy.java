package proxyclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Socks5Proxy class represents a local SOCKS5 proxy server. 

 * If your application is running on a machine with multiple network interfaces, maybe 
 * there are several local addresses.
 * 
 * This Implementation has the following limitations:
 * <ul>
 * <li>only supports the no-authentication authentication method</li>
 * <li>only supports the <code>connect</code> command and will not answer correctly to other
 * commands</li>
 * <li>only supports requests with the domain address type and will not correctly answer to requests
 * with other address types</li>
 * </ul>
 * (see <a href="http://tools.ietf.org/html/rfc1928">RFC 1928</a>)
 */
public class Socks5Proxy {

    /* SOCKS5 proxy singleton */
    private static Socks5Proxy socks5Server;

    /* reusable implementation of a SOCKS5 proxy server process */
    private Socks5ServerProcess serverProcess;

    /* thread running the SOCKS5 server process */
    private Thread serverThread;

    /* server socket to accept SOCKS5 connections */
    private ServerSocket serverSocket;

    /* assigns a connection to a digest */
    private final Map<String, Socket> connectionMap = new ConcurrentHashMap<String, Socket>();

    /* list of digests connections should be stored */
    private final List<String> allowedConnections = Collections.synchronizedList(new LinkedList<String>());

    //private final Set<String> localAddresses = Collections.synchronizedSet(new LinkedHashSet<String>());
    
    private int boundPort; 

    /**
     * Private constructor.
     */
    private Socks5Proxy() {
        this.serverProcess = new Socks5ServerProcess();
    }

    
    public static synchronized Socks5Proxy getSocks5Proxy() throws IOException {
        if (socks5Server == null) {
            socks5Server = new Socks5Proxy();
        }
        socks5Server.start();
        
        return socks5Server;
    }

    /**
     * Starts the local SOCKS5 proxy server. 
     */
    public synchronized void start() throws IOException {
        if (isRunning()) {
            return;
        }
    
    	int minPort = 1024;
    	int port = 65535;
        for (; port >= minPort; port--) {
            try {
                this.serverSocket = new ServerSocket(port);
                boundPort = port;
                System.out.println("Local proxy service started, port:" + port);
                
                break;
            }
            catch (IOException e) {
                // port is used, try next one
            }
        }

        if (this.serverSocket != null) {
            this.serverThread = new Thread(this.serverProcess);
            this.serverThread.start();
        }
        else {
        	System.err.println("Failed to start local SOCKS5 proxy.");
        	throw new IOException("Failed to start local SOCKS5 proxy.");
        }
    }

    /**
     * Stops the local SOCKS5 proxy server.
     */
    public synchronized void stop() {
        if (!isRunning()) {
            return;
        }

        try {
            this.serverSocket.close();
        }
        catch (IOException e) {
            // do nothing
        }

        if (this.serverThread != null && this.serverThread.isAlive()) {
            try {
                this.serverThread.interrupt();
                this.serverThread.join();
            }
            catch (InterruptedException e) {
                // do nothing
            }
        }
        
        this.serverThread = null;
        this.serverSocket = null;
    }

    /**
     * Returns the port of the local SOCKS5 proxy server. If it is not running -1 will be returned.
     * 
     * @return the port of the local SOCKS5 proxy server or -1 if proxy is not running
     */
    public int getPort() {
        if (!isRunning()) {
            return -1;
        }
        return this.serverSocket.getLocalPort();
    }

    /**
     * Returns the socket for the given digest. A socket will be returned if the given digest has
     * been in the list of allowed transfers (see {@link #addTransfer(String)}) while the peer
     * connected to the SOCKS5 proxy.
     * 
     * @param digest identifying the connection
     * @return socket or null if there is no socket for the given digest
     */
    protected Socket getSocket(String digest) {
        return this.connectionMap.get(digest);
    }

    /**
     * Add the given digest to the list of allowed transfers. Only connections for allowed transfers
     * are stored and can be retrieved by invoking {@link #getSocket(String)}. All connections to
     * the local SOCKS5 proxy that don't contain an allowed digest are discarded.
     * 
     * @param digest to be added to the list of allowed transfers
     */
    protected void addTransfer(String digest) {
        this.allowedConnections.add(digest);
    }

    /**
     * Removes the given digest from the list of allowed transfers. After invoking this method
     * already stored connections with the given digest will be removed.
     * <p>
     * The digest should be removed after establishing the SOCKS5 Bytestream is finished, an error
     * occurred while establishing the connection or if the connection is not allowed anymore.
     * 
     * @param digest to be removed from the list of allowed transfers
     */
    protected void removeTransfer(String digest) {
        this.allowedConnections.remove(digest);
        this.connectionMap.remove(digest);
    }

    public boolean isRunning() {
        return this.serverSocket != null;
    }
    
    public int getBoundPort() {
    	return this.boundPort;
    }

    /**
     * Implementation of a simplified SOCKS5 proxy server.
     */
    private class Socks5ServerProcess implements Runnable {

        public void run() {
            while (true) {
                Socket socket = null;

                try {
                    if (Socks5Proxy.this.serverSocket.isClosed()
                                    || Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    socket = Socks5Proxy.this.serverSocket.accept();

                    // receive socks5 connection request, and negotiate connection.
                    establishConnection(socket);
                }
                catch (SocketException e) {
                    /*
                     * do nothing, if caused by closing the server socket, thread will terminate in
                     * next loop
                     */
                }
                catch (Exception e) {
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    }
                    catch (IOException e1) {
                        /* do nothing */
                    }
                }
            }

        }

        /**
         * Negotiates a SOCKS5 connection and stores it on success.
         * 
         * @param socket connection to the client
         * @throws IOException if a network error occurred
         */
        private boolean establishConnection(Socket socket) throws IOException {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // first byte is version should be 5
            int b = in.read();
            if (b != 5) {
                //throw new IOException("Only SOCKS5 supported");
            	return false;
            }

            // second byte number of authentication methods supported
            b = in.read();

            // read list of supported authentication methods
            byte[] auth = new byte[b];
            in.readFully(auth);

            byte[] authMethodSelectionResponse = new byte[2];
            authMethodSelectionResponse[0] = (byte) 0x05; // protocol version

            // only authentication method 0, no authentication, supported
            boolean noAuthMethodFound = false;
            for (int i = 0; i < auth.length; i++) {
                if (auth[i] == (byte) 0x00) {
                    noAuthMethodFound = true;
                    break;
                }
            }

            if (!noAuthMethodFound) {
                authMethodSelectionResponse[1] = (byte) 0xFF; // no acceptable methods
                out.write(authMethodSelectionResponse);
                out.flush();
                //throw new IOException("Authentication method not supported");
                return false;
            }

            authMethodSelectionResponse[1] = (byte) 0x00; // no-authentication method
            out.write(authMethodSelectionResponse);
            out.flush();

            // receive connection request
            //byte[] connectionRequest = Socks5Utils.receiveSocks5Message(in);
            byte[] connectionRequest;
            byte[] header = new byte[5];
            in.readFully(header, 0, 5);
            if (header[3] != (byte) 0x03) {
                //throw new IOException("Unsupported SOCKS5 address type");
            	return false;
            }
            int addressLength = header[4];
            connectionRequest = new byte[7 + addressLength];
            System.arraycopy(header, 0, connectionRequest, 0, header.length);
            in.readFully(connectionRequest, header.length, addressLength + 2);

            // extract digest
            String responseDigest = new String(connectionRequest, 5, connectionRequest[4]);

            // return error if digest is not allowed
            if (!Socks5Proxy.this.allowedConnections.contains(responseDigest)) {
                connectionRequest[1] = (byte) 0x05; // set return status to 5 (connection refused)
                out.write(connectionRequest);
                out.flush();

                //throw new IOException("Connection is not allowed");
                return false;
            }

            connectionRequest[1] = (byte) 0x00; // set return status to 0 (success)
            out.write(connectionRequest);
            out.flush();

            // store connection
            Socks5Proxy.this.connectionMap.put(responseDigest, socket);
            
            return true;
        }

    }

}
