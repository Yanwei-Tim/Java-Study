package proxyclient;

public class StreamHost {
    private final String ID; 

    private final String addr;

    private int port = 0;
    
    public StreamHost(final String ID, final String address, final int port) {
    	this.ID = ID;
        this.addr = address;
        this.port = port;
    }
    
    public String getID() {
        return ID;
    }

    public String getAddress() {
        return addr;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

}
