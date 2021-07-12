package chat;

import java.net.*;
import java.io.*;


public class server {
	public static void main(String args[]) {
		ServerSocket server = null;
		
		
		int PORT = 9001;
		try {
			server = new ServerSocket(PORT);
			System.out.println("服务器已开启....");
			
			while(true)
			{
				Socket socket = server.accept();
				System.out.println("客户端已连接"+socket);
				socketThread st = new socketThread(socket);
				st.start();
			}
		}
		catch (Exception e) {
				// TODO: handle exception
			
			e.printStackTrace();}
			finally {	
				try {
					if(server!= null)
						server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		
		}
	static class socketThread extends Thread{
		Socket socket = null;
		
		PrintWriter pw = null;
		BufferedReader br = null;
		mchat mc = new mchat();
		public socketThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			try {
	
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
	
		
		
		
		String str = "";
		while((str = br.readLine())!=null) {
			if(str.equals("END"))
				break;
			
			System.out.println("客户端发送的信息"+str);
			String strRtn = "";
			String []command = str.split("\\|");
			if(command[0].equals("CHAT")) {
				String strOpt = mc.getChatResult(command[1]);
				if(strOpt.equals(""))
				
					strRtn = "我不太懂您的意思";
				else
					strRtn = strOpt;
				
				
			}
			else if (command[0].equals("NUM")){
				strRtn = "偶数";
			}
		else{
			strRtn = "我不太懂您的意思";
			}
		}

		pw.println(strRtn);
		pw.flush();}
		
	
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();}
		finally {	
			try {
				
				if(br != null)
					br.close();
				if(pw != null)
					pw.close();
				if(socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		}
	}
	}

