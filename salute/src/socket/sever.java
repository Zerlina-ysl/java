package socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
public class sever {

	public static void main(String[] args) {
		int PORT = 9000;  
		ServerSocket server = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw  = null;		
		try {
			server =new ServerSocket(PORT);
			System.out.println("服务器已连接...");			
			socket = server.accept();   
			System.out.println("客户端已连接"+socket);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));			
			String str = br.readLine();
			System.out.println("客户端发送的信息："+ str);			
	
			String strRtn = "客户端的反馈信息"+ str;
			pw.println(strRtn);
			pw.flush();    
//			while(true) {
//				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
				try {
					if (pw != null) pw.close();
					if (br != null) br.close();
					if (socket != null) socket.close();
					if (server != null) server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
		
	}

}
