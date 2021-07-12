package GUISocket.Chat.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread extends Thread {	
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw  = null;		
	public SocketThread(Socket socket,String sName) {
		super(sName);
		this.socket = socket;
	}	
	public void run() {	
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));			
//			String sUser = "LOGIN|"+this.getName();
//			sendMSG(sUser);//���͵�¼�û���;
			String str ="";
//			���͵�¼��Ϣ��LOGIN|Username   
			String sUser = this.getName();
			String sSend = "LOGIN|"+sUser;  //��¼��Ϣ
			sendMSG(sSend);
			while((str = br.readLine())!=null) {		//			ѭ�������ͻ��˵�����		
//				ClientMG.getClientMG().setLogTxt(str);	
				String comments[] = str.split("\\|");
				if(comments[0].equals("USERLIST")) {
					//���û������б���
					String User [] = comments[1].split("\\_");
					ClientMG.getClientMG().addItems(User);
				}else if(comments[0].equals("ADD")) {
					//������û�
					ClientMG.getClientMG().addUser(comments[1]);
				}else if(comments[0].equals("MSG")) {
					String sender = comments[1];
					String msg = comments[2];
					ClientMG.getClientMG().setLogTxt("["+sender+"˵:]");
					ClientMG.getClientMG().setLogTxt(msg);
				}else if(comments[0].equals("DEL")) {
					String off_name = comments[1];
					ClientMG.getClientMG().removerUser(off_name);
				}else if(comments[0].equals("CLOSE")) {
					ClientMG.getClientMG().setLogTxt("�������ѹر�");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {			
			try {
				ClientMG.getClientMG().clearUser();
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
	public boolean socketQuit() {
		try {
			//��������
			String closemsg = "OFFLINE|" + this.getName();
			sendMSG(closemsg);
			if (pw != null) pw.close();
			if (br != null) br.close();
			if (socket != null) socket.close();	
			return true;
		} catch (IOException e) {					
			e.printStackTrace();
			return false;
		}	
	}
}
