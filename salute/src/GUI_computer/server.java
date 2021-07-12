package GUI_computer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
import javax.swing.border.TitledBorder;

public class server extends JFrame {
	ServerSocket server = null;
	private JPanel contentPane;
	private JTextField txtport;
	private JTextArea txtLog;  
	serverListener sl = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server frame = new server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnstart = new JButton("开始服务");
		btnstart.addActionListener(new BtnStartActionListener());
		btnstart.setBounds(10, 26, 117, 29);
		contentPane.add(btnstart);
		
		JButton btnquit = new JButton("结束服务");
		btnquit.addActionListener(new BtnQuitActionListener());
		btnquit.setBounds(205, 26, 117, 29);
		contentPane.add(btnquit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u804A\u5929\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(32, 65, 290, 201);
		contentPane.add(scrollPane);
		
		txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);
		
		
		JLabel lblNewLabel = new JLabel("端口号");
		lblNewLabel.setBounds(32, 6, 61, 16);
		contentPane.add(lblNewLabel);
		
		txtport = new JTextField();
		txtport.setText("9001");
		txtport.setBounds(94, 1, 130, 26);
		contentPane.add(txtport);
		txtport.setColumns(10);
		
		
		
	}
	
	private class BtnStartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//开始服务
		
			
			int PORT = Integer.parseInt(txtport.getText().trim());
			try {
				
				server = new ServerSocket(PORT);
				txtLog.append("服务器已连接...\n");
				
				sl = new serverListener();
				sl.start();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
}
		
	}
	private class BtnQuitActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
		//结束服务
			sl.quit();
			}
	}
		class serverListener extends Thread{
			volatile boolean bflag = true;
			Socket socket = null;
			
			public serverListener() {
				
			}

			@Override
			public void run() {
				while(bflag)
			{
					try {
					socket = server.accept();
					txtLog.append("客户端正在监听"+socket+"\n");
					socketThread st = new socketThread(socket);
					st.start();
			
					
					
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			}
			}
			public void quit() {
				try{
					bflag = false;
					if(server!=null)
						server.close();
					txtLog.append("服务器已退出");
				}catch (Exception e) {e.printStackTrace();}
			}
			
		
		}
			class socketThread extends Thread{
				BufferedReader br = null;
				PrintWriter pw = null;
				Socket socket = null;
				public socketThread(Socket socket) {
					this.socket = socket;
				}
				public void run() {
					try
					{
					br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8" )));
					String str = "";
					while((str=br.readLine())!=null) {
						if(str.equals("QUIT"))
								break;
					txtLog.append("客户端的操作："+str+"\n");
//						
					pw.println(str);
					pw.flush();}
						
				}catch (Exception e) {e.printStackTrace();}
					finally {
					try {
						
					if (pw != null) pw.close();
					if (br != null) br.close();
					if (socket != null) socket.close();					
				} catch (IOException e) {					
					e.printStackTrace();
				}	
				}
					
				}
				
			}
}
