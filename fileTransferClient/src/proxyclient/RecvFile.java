package proxyclient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class RecvFile {
	
	// store file path for received file
	private String fileStorePath;
	
	// sender id, maybe auth token
	private String initiatorID;
		
	// receiver id, maybe auth token
	private String targetID;
	
	// stream hosts to be choose one from
  	Collection<StreamHost> streamHosts;
  	
  	StreamHost selectedHost = null;
  	
  	Socket socket;
  	String digest;
  	
  	public RecvFile(String initiatorID, String targetID, String fileStorePath, Collection<StreamHost> streamHosts) {
  		this.initiatorID = initiatorID;
  		this.targetID = targetID;
  		this.fileStorePath = fileStorePath;
  		this.streamHosts = streamHosts;
  	}

  	public void trySocks5Connection() throws Exception {

  		digest = Utils.createDigest(initiatorID, targetID);
  		for (StreamHost streamHost : streamHosts) {
  			final Socks5Client socks5Client = new Socks5Client(streamHost, digest);
            try {
                // connect to SOCKS5 proxy with a timeout
                socket = socks5Client.getSocket(Config.TIMEOUT);
                selectedHost = streamHost;
                System.out.println("Selected host: " + selectedHost.getAddress() + ":" + selectedHost.getPort());
                break;
            }
            catch (TimeoutException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
            	e.printStackTrace();
            }
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
  	}
  	
  	public void doRecv() throws Exception {
  		OutputStream outputStream = null;
  		InputStream inputStream = null;
  		try {
  			inputStream = socket.getInputStream();
  			outputStream = new FileOutputStream(fileStorePath);
  			writeToStream(inputStream, outputStream);
  		} 
  		catch (FileNotFoundException e) {
  			System.err.println("file not found.");
  			throw e;
  		} 
  		catch (IOException e) {
  			System.err.println("file receiving error.");
  			throw e;
  		} 
  		finally {
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

  		System.out.println("file receiving ended.");
  	}
  	
	protected static void writeToStream(final InputStream in, final OutputStream out)
			throws IOException
    {
		final int BUFFER_SIZE = 8192;
		final byte[] b = new byte[BUFFER_SIZE];
		int count = 0;
		long totalBytes = 0;

        do {
			// write to the output stream
			try {
				out.write(b, 0, count);
				totalBytes += count;
				System.out.println("Receiver: recv bytes:" + count);
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
        
        System.out.println("Total Received Bytes: " + totalBytes);  
	}
	
	public StreamHost getSelectedHost() {
		return selectedHost;
	}
}
