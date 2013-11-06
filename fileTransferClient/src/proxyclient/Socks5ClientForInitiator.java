package proxyclient;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

/**
 * Implementation of a SOCKS5 client used on the initiators side. This is needed because connecting
 * to the local SOCKS5 proxy differs form the regular way to connect to a SOCKS5 proxy. Additionally
 * a remote SOCKS5 proxy has to be activated by the initiator before data can be transferred between
 * the peers.
 */
class Socks5ClientForInitiator extends Socks5Client {
	
	private String initiatorID;

    /**
     * Creates a new SOCKS5 client for the initiators side.
     * 
     * @param streamHost containing network settings of the SOCKS5 proxy
     * @param digest identifying the SOCKS5 Bytestream
     * @param target the target ID of the SOCKS5 Bytestream
     */
    public Socks5ClientForInitiator(StreamHost streamHost, String digest, String initiatorID) {
        super(streamHost, digest);
        this.initiatorID = initiatorID;
    }

    public Socket getSocket(int timeout) throws IOException, InterruptedException,
                    TimeoutException {
        Socket socket = null;

        // check if stream host is the local SOCKS5 proxy
        if (this.streamHost.getID().equals(initiatorID)) {
            Socks5Proxy socks5Server = Socks5Proxy.getSocks5Proxy();
            socket = socks5Server.getSocket(this.digest);
            if (socket == null) {
                throw new IOException("target is not connected to SOCKS5 proxy");
            }
        }
        else {
            socket = super.getSocket(timeout);

//            try {
//                activate();
//            }
//            catch (Exception e) {
//                socket.close();
//                throw new IOException("activating SOCKS5 Bytestream failed", e);
//            }

        }

        return socket;
    }
}
