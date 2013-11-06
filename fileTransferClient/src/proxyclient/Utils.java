
package proxyclient;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;


/**
 * A collection of utility methods for SOcKS5 messages.
 */
public class Utils {

    /**
     * Used by the hash method.
     */
    private static MessageDigest digest = null;

    /**
     * Hashes a String using the SHA-1 algorithm and returns the result as a
     * String of hexadecimal numbers. This method is synchronized to avoid
     * excessive MessageDigest object creation. If calling this method becomes
     * a bottleneck in your code, you may wish to maintain a pool of
     * MessageDigest objects instead of using this method.
     * <p>
     * A hash is a one-way function -- that is, given an
     * input, an output is easily computed. However, given the output, the
     * input is almost impossible to compute. This is useful for passwords
     * since we can store the hash and a hacker will then have a very hard time
     * determining the original password.
     *
     * @param data the String to compute the hash of.
     * @return a hashed version of the passed-in String
     */
    public synchronized static String hash(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("SHA-1");
            }
            catch (NoSuchAlgorithmException nsae) {
                System.err.println("Failed to load the SHA-1 MessageDigest. ");
            }
        }
        // Now, compute hash.
        try {
            digest.update(data.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return encodeHex(digest.digest());
    }

    /**
     * Encodes an array of bytes as String representation of hexadecimal.
     *
     * @param bytes an array of bytes to convert to a hex string.
     * @return generated hex string.
     */
    public static String encodeHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);

        for (byte aByte : bytes) {
            if (((int) aByte & 0xff) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toString((int) aByte & 0xff, 16));
        }

        return hex.toString();
    }
    
    /**
     * Returns a SHA-1 digest of the given parameters as specified.
     */
    public static String createDigest(String initiatorID, String targetID) {
        StringBuilder b = new StringBuilder();
        b.append(initiatorID).append(targetID);
        return Utils.hash(b.toString());
    }
    
	public static List<String> getActiveIpAddresses()  {
		
		List<String> ipAddresses = new LinkedList<String>();
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = (InetAddress) addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                    	String ipAddr = addr.getHostAddress();
                    	System.out.println(iface.getDisplayName() + ": " + ipAddr);
                    	ipAddresses.add(ipAddr);
                    }
                }
            }
		} catch (SocketException e) {
			
		}
		
		return ipAddresses;
	}
	
	public static String secondToTime(Long seconds) {
		String hour;
		String minute;
		String second;
		int ss = seconds.intValue();
		int s = ss % 60;
		int m = ss / 60;
		int h = m / 60;
		m = (m < 60) ? m : m % 60;
		hour = (h < 10) ? "0" + h + ":" : h + ":";
		minute = (m < 10) ? "0" + m + ":" : m + ":";
		second = (s < 10) ? "0" + s : String.valueOf(s);
		return hour + minute + second;
	}
	
	public static String millisToTime(long millis) {
		long seconds = millis / 1000;
		long frag = millis % 1000;
		return secondToTime(seconds) + "." + frag;
	}

}
