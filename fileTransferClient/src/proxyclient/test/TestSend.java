package proxyclient.test;

import proxyclient.Config;
import proxyclient.SendFile;

public class TestSend {
	
	public static void main(String[] args) {
		String initiatorToken = "12345";
		String targetToken = "abcde";
		String filePath = 
				"/home/sgf/Downloads/hazelcast-2.5.zip";

		SendFile sendFile = new SendFile(initiatorToken, targetToken, filePath);

		boolean localProxyStarted = false;
		try {
			localProxyStarted = sendFile.startLocalProxy();
		} catch (Exception e) {
			
		}
		if (localProxyStarted) {
			System.out.println("local proxy started, listen port:" + sendFile.getLocalProxyPort());
		} else {
			System.err.println("local proxy start failed." );
		}

		// todo: 发送握手协议给接收方，告知对方已启动代理服务的ip地址列表，包括本地的（多个）ip地址和proxyServer的ip
		// ... 
		// todo: 同步等待接收方反馈: 接收方同意接收文件，并且返回已选择的ip（可能是发送端的某个ip地址或proxyServer）
		
		// 假设接收方选择的是发送方本地的一个ip
		//StreamHost selectedHost = new StreamHost(initiatorToken, "192.168.108.213", sendFile.getLocalProxyPort());
		String selectedAddr = "192.168.108.180";
		// 假设接收方选择的是proxyServer
//		String selectedAddr = Config.PROXYSERVER_ADDR;
		
		sendFile.setSelectedAddr(selectedAddr);
		
		try {
			sendFile.doSend();
		} catch (Exception e) {
			System.err.println("send file failed.");
		}
	}
}
