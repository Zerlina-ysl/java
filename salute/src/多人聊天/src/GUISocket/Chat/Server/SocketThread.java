package GUISocket.Chat.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import GUISocket.Chat.Client.ClientMG;

public class SocketThread extends Thread {
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw  = null;		
	public SocketThread(Socket socket) {		
		this.socket = socket;
	}	
	public void run() {	
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));			
			String str ="";
//			循环监听客户端的数据
			while((str = br.readLine())!=null) {
				String comment[] = str.split("\\|");
				ServerMG.getClientMG().setLogTxt(str);
				if(comment[0].equals("LOGIN")) {
					String userName = comment[1];
					this.setName(userName);
					ServerMG.getClientMG().adduser(this);
					ServerMG.getClientMG().getOnlineName(this);
					ServerMG.getClientMG().addNewName(this);
				}else if(comment[0].equals("MSG")) {
					//发送一对一消息的代码，格式：MSG|SenderName|RecName|MSGInfo
					String SenderName = comment[1];
					String RecName = comment[2];
					String msg = comment[3];
					if(RecName.equals("ALL")) {
						ServerMG.getClientMG().sendMSGtoALL(msg,this);
					}else {
						SocketThread s = ServerMG.getClientMG().getScoketByName(RecName);
						s.sendMSG("MSG|" + SenderName + "|" + msg);
					}
				}else if(comment[0].equals("OFFLINE")) {
					String offline_name = comment[1];
					ServerMG.getClientMG().deluserlist(offline_name, this);//处理下线用户信息：DEL|username
					ServerMG.getClientMG().deluser(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {			
			try {
				if (pw != null) pw.close();
				if (br != null) br.close();
				if (socket != null) socket.close();					
			} catch (IOException e) {					
				e.printStackTrace();
			}	
		}		
	}
	public void sendMSG(String send) {
		pw.println(send);
		pw.flush();
	}
	public void socketQuit() {
		try {
			//发送下线
			String closemsg = "CLOSE";
			sendMSG(closemsg);
			if (pw != null) pw.close();
			if (br != null) br.close();
			if (socket != null) socket.close();	
			//return true;
		} catch (IOException e) {					
			e.printStackTrace();
		//	return false;
		}	
	}
}
