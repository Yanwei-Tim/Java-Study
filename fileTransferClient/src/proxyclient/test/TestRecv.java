package proxyclient.test;

import java.util.ArrayList;
import java.util.Collection;

import proxyclient.Config;
import proxyclient.RecvFile;
import proxyclient.StreamHost;

public class TestRecv {
	
	public static void main(String[] args) {

		// todo: 接收到发送方的发送文件请求通知，包括文件信息、(多个)streamhost的信息

		String fileStorePath = "/home/sgf/wormhole/fileTransferDemo/fileTransferOnline/files/recvFiles/test1.zip";
		String initiatorToken = "12345";
		String targetToken = "abcde";

		Collection<StreamHost> streamHosts = new ArrayList<StreamHost>();
		streamHosts.add(new StreamHost(initiatorToken, "192.168.1.213", 65535));
		streamHosts.add(new StreamHost("", Config.PROXYSERVER_ADDR, Config.PROXYSERVER_PORT));

		RecvFile recvFile = new RecvFile(initiatorToken, targetToken, fileStorePath, streamHosts);
		try {
			recvFile.trySocks5Connection();
		} catch (Exception e) {

		}

		if (recvFile.getSelectedHost() == null) {
			System.err.println("Failed to connect to any proxy host.");
			return;
		}

		// todo: 将成功连接的streamhost通知给发起方

		try {
			recvFile.doRecv();
		} catch (Exception e) {
			System.err.println("Recv file failed.");
			return;
		}

		System.out.println("Recv file succeeded.");

	}
}
