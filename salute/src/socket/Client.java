	package socket;

import java.net.*;

import java.io.*;


public class Client {
	public static void main(String args[]){
//		ServerSocket server = null;
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		int PORT = 9001;
		String IP = "127.0.0.1";
		BufferedReader bkey = null;
		
		try {
			socket = new Socket(IP,PORT);
			System.out.println("连接信息"+socket);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			bkey = new BufferedReader(new InputStreamReader(System.in));
			String str = "";
			while(true) {
			String strKey = bkey.readLine();
			if(strKey.equals("QUIT"))
				break;
			String send = strKey;
			pw.println(send);
			pw.flush();
			String strRes = br.readLine();
			System.out.println("服务器的反馈信息"+strRes);
				
				
				
			}
			}	
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


