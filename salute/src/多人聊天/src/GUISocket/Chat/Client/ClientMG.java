package GUISocket.Chat.Client;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class ClientMG {
	private static final ClientMG clientmg =new ClientMG();
	private ClientMG() {}
	public static ClientMG getClientMG() {
		return clientmg;
	}
	SocketThread thd;
	private ClientForm win;
	public void  setWinForm(ClientForm w) {
		this.win =  w;
	}
	
	public void setLogTxt(String str) {
		win.textLog.append(str+"\n");
	}	
	public void addItems(String [] users) {
		//添加到客户端的List控件中。
		for(int i = 0;i<users.length;i++) {
			addUser(users[i]);
		}
	}
	//添加用户
	public void addUser(String user) {
		win.itemUsers.addElement(user);
	}
	public void clearUser() {
		win.itemUsers.clear();
	}
	public void removerUser(String user) {
		win.itemUsers.removeElement(user);
	}
	public boolean connect(String IP,int port, String username) {		
		try {
			Socket socket =new Socket(IP, port);  //初始化连接服务器			
			thd =new SocketThread(socket,username);
			thd.start();
			return true;
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		} 		
	}
	public boolean close() {
		if(thd.socketQuit()){
			clearUser();//清空列表
			return true;
		}else {
			return false;
		}
	}
	public String getCurrThdName() {
		if (thd!=null)
			return thd.getName().trim();
		else
			return "";
	}
	public void sendMSG(String str) {
		if (thd!=null)
			thd.sendMSG(str);
	}
}
