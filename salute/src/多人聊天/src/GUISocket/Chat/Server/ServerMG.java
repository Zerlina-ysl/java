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
	//写入操作日志
	public void setLogTxt(String str) {
		win.textLog.append(str+"\n");
	}
	//添加上线用户
	public synchronized void adduser(SocketThread st) {
		userlist.add(st);
	}
	//删除下线用户
	public synchronized void deluser(SocketThread st) {
		userlist.remove(st);
	}
	//清空用户
	public void clearuser() {
		userlist.clear();
	}
	//发送给所用用户新上线用户
	public void getOnlineName(SocketThread st) {
		if(userlist.size()>0) {	
			String username = "";
			for(int i = 0;i<userlist.size();i++) {
				SocketThread s = userlist.get(i);
				if(s.equals(st)) //排除自身登录
					continue;
				username+=s.getName()+"_";
			} 
			if(!username.equals(""))
				st.sendMSG("USERLIST|"+username);
		}
	}
	//发送新登录用户
	public void addNewName(SocketThread st) {
		
			for(int i = 0; i<userlist.size();i++) {
				SocketThread s = userlist.get(i);
				if(s.equals(st)) 
					continue;
				s.sendMSG("ADD|" + st.getName());
			}
		
	}
	//转发给不同人//通过Name用户名查找目标
	public SocketThread getScoketByName(String name) {
		for(int i = 0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	//转发全部人//发送给所有人，但是要排除自身,格式：MSG|SenderName|MSGInfo
	public void sendMSGtoALL(String MSG,SocketThread st) {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(!s.equals(st)) {
			s.sendMSG("MSG|" + st.getName() + "|" +MSG);
			}
		}
	}
	//删除下线用户通知
	public void deluserlist(String offname,SocketThread st) {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			if(!s.equals(st)) {
			s.sendMSG("DEL|" + st.getName() );
			}
		}
	}
	//关闭服务器，清空用户列表
	public void closeserver() {
		for(int i =0;i<userlist.size();i++) {
			SocketThread s = userlist.get(i);
			s.socketQuit();		
		}
		clearuser();
	}
	
}
