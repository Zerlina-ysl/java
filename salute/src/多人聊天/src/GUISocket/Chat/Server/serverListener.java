package GUISocket.Chat.Server;

import java.io.IOException;
import java.net.*;

import GUISocket.Chat.Server.SocketThread;
import GUISocket.Chat.Server.ServerMG;
public class serverListener extends Thread {
	ServerSocket server;
	volatile boolean bFlag=true;
	public serverListener(ServerSocket server) {
		this.server = server;
	}
	public void run() {
		while(bFlag) {
			try {
				Socket socket = server.accept();   //������������ʼ,����ʽ����					
				ServerMG.getClientMG().setLogTxt("�ͻ������ӣ�"+socket);
				SocketThread thd = new SocketThread(socket);
				thd.start();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
	}
	public void stopListener() {
		bFlag=false;
	}
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
