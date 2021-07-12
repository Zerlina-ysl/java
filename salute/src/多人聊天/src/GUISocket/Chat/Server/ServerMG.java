package GUISocket.Chat.Server;

import java.util.ArrayList;



//import text.Client.Server.SocketThread;

public class ServerMG {

	private static final ServerMG servermg =new ServerMG();
	private ServerMG() {}
	public static ServerMG getClientMG() {
		return servermg;
	}
	ArrayList<SocketThread> userlist = new ArrayList<>();
	private ServerForm win;
	public void  setWinForm(ServerForm w) {
		this.win =  w;
	}
	//д�������־
	public void setLogTxt(String str) {
		win.textLog.append(str+"\n");
	}
	//��������û�
	public synchronized void adduser(SocketThread st) {
		userlist.add(st);
	}
	//ɾ�������û�
	public synchronized void deluser(SocketThread st) {
		userlist.remove(st);
	}
	//����û�
	public void clearuser() {
		userlist.clear();
	}
	//���͸������û��������û�
	public void getOnlineName(SocketThread st) {
		if(userlist.size()>0) {	
			String username = "";
			for(int i = 0;i<userlist.size();i++) {
				SocketThread s = userlist.get(i);
				if(s.equals(st)) //�ų������¼
					continue;
				username+=s.getName()+"_";
			} 
			if(!username.equals(""))
				st.sendMSG("USERLIST|"+username);
		}
	}
	//�����µ�¼�û�
	public void addNewName(SocketThread st) {
		
			for(int i = 0; i<userlist.size();i++) {
				SocketThread s = userlist.get(i);
				if(s.equals(st)) 
					continue;
				s.sendMSG("ADD|" + st.getName());
			}
		
	}
	//ת������ͬ��//ͨ��Name�û�������Ŀ��
	public SocketThread getScoketByName(String name) {
		for(int i = 0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	//ת��ȫ����//���͸������ˣ�����Ҫ�ų�����,��ʽ��MSG|SenderName|MSGInfo
	public void sendMSGtoALL(String MSG,SocketThread st) {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(!s.equals(st)) {
			s.sendMSG("MSG|" + st.getName() + "|" +MSG);
			}
		}
	}
	//ɾ�������û�֪ͨ
	public void deluserlist(String offname,SocketThread st) {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(!s.equals(st)) {
			s.sendMSG("DEL|" + st.getName() );
			}
		}
	}
	//�رշ�����������û��б�
	public void closeserver() {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			s.socketQuit();		
		}
		clearuser();
	}
	
}
