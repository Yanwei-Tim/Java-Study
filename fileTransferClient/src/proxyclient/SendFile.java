package proxyclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class SendFile {
	// sender id, maybe auth token
	private String initiatorID;
	
	// receiver id, maybe auth token
  	private String targetID;
  	
  	// path of file to be sent
  	private String filePath;
  	
  	private String digest;
  	
  	private Socks5Proxy localProxy;
  	private int localProxyPort;

  	//private StreamHost selectedHost;
  	private String selectedAddr;
  	
  	public SendFile(String initiatorID, String targetID, String filePath) {
  		this.initiatorID = initiatorID;
  		this.targetID = targetID;
  		this.filePath = filePath;
  	}
	
	/*
	 * Start local socks5 proxy service.
	 */
	public boolean startLocalProxy() throws IOException {
		
		localProxy = Socks5Proxy.getSocks5Proxy();

		if (localProxy != null && localProxy.isRunning()) {
			digest = Utils.createDigest(initiatorID, targetID);
			localProxy.addTransfer(digest);
			localProxyPort = localProxy.getBoundPort();
			
			return true;
		} else {
			return false;
		}
	}
	
	public void doSend() throws Exception {
		StreamHost selectedHost;
		if (selectedAddr.equals(Config.PROXYSERVER_ADDR)) { // selected host is proxy server
			selectedHost = new StreamHost("", selectedAddr, Config.PROXYSERVER_PORT);
		} else {
			selectedHost = new StreamHost(initiatorID, selectedAddr, localProxyPort);
		}
		
		Socks5Client socks5Client = new Socks5ClientForInitiator(selectedHost, digest, initiatorID);

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			socket = socks5Client.getSocket(Config.TIMEOUT);
			outputStream = socket.getOutputStream();
			inputStream = new FileInputStream(filePath);
			writeToStream(inputStream, outputStream);
		} catch (FileNotFoundException e) {
			System.err.println("file not found.");
			throw e;
		} 
//		catch (IOException e) {
//			//e.printStackTrace();
//		} catch (InterruptedException e) {
//			//e.printStackTrace();
//		} 
		catch (TimeoutException e) {
			System.err.println("timeout error.");
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}

				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				System.err.println("error closing streams and socket.");
			}
		}

		System.out.println("file sending ended.");

		localProxy.removeTransfer(digest);
	}

	protected static void writeToStream(final InputStream in, final OutputStream out)
			throws IOException
    {
		final int BUFFER_SIZE = 8192;
		final byte[] b = new byte[BUFFER_SIZE];
		int count = 0;
		long totalBytes = 0;
		
		long startTime = System.currentTimeMillis();
        do {
			// write to the output stream
			try {
				out.write(b, 0, count);
				totalBytes += count;
				System.out.println("Sender: send bytes:" + count);
			} catch (IOException e) {
				throw new IOException("error writing to output stream", e);
			}

			// read more bytes from the input stream
			try {
				count = in.read(b);
			} catch (IOException e) {
				throw new IOException("error reading from input stream", e);
			}
		} while (count != -1);
        long diffMillis = System.currentTimeMillis() - startTime;

        System.out.println("Total sent Bytes: " + totalBytes);
        System.out.println("Consumed time: " +  Utils.millisToTime(diffMillis));
	}
	
	public int getLocalProxyPort() {
		return localProxyPort;
	}
	
	public void setSelectedAddr(String selectedAddr) {
		this.selectedAddr = selectedAddr;
	}
}
