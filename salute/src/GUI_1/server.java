package GUI_1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class server extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblNewLabel;
	public JTextField txtPort;
	public JButton btnNewButton;
	public JButton btnNewButton_1;
	public JScrollPane scrollPane;
	public JTextArea txtLog;
ServerSocket server = null;
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
		setTitle("\u670D\u52A1\u5668\u7AEF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 382, 59);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("\u7AEF\u53E3");
		lblNewLabel.setBounds(10, 25, 51, 15);
		panel.add(lblNewLabel);
		
		txtPort = new JTextField();
		txtPort.setText("9001");
		txtPort.setBounds(55, 22, 66, 21);
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		btnNewButton = new JButton("\u5F00\u542F\u670D\u52A1");
		btnNewButton.addActionListener(new BtnOpenActionListener());

		btnNewButton.setBounds(131, 21, 90, 23);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("\u5173\u95ED\u670D\u52A1");
		btnNewButton_1.addActionListener(new BtnCloseActionListener());

		btnNewButton_1.setBounds(231, 21, 98, 23);
		panel.add(btnNewButton_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new TitledBorder(null, "\u6D88\u606F\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(0, 58, 382, 403);
		contentPane.add(scrollPane);
		
		txtLog = new JTextArea();
		txtLog.setLineWrap(true);
		scrollPane.setViewportView(txtLog);
	}
	private class BtnOpenActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//开启服务
			int PORT = Integer.parseInt(txtPort.getText());
			try {
				server = new ServerSocket(PORT);
				txtLog.append("服务器已连接...\n");
				sl = new serverListener();
				sl.start();
				
				
			}catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
			
		}
	}
	private class BtnCloseActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//关闭服务
			sl.stopListener();
		}
	}
	class serverListener extends Thread{
		volatile boolean bFlag = true;
		public serverListener() {
			
		}
		public void run() {
			while(bFlag) {
				try {
					Socket socket = server.accept();
					txtLog.append("客户端正在监听"+socket+"\n");
					socketThread st = new socketThread(socket);
					st.start();
					
				}catch (Exception e) {
					// TODO: handle exception
				e.printStackTrace();
				
				}
			}
		}
		public void stopListener() {
			try{
				bFlag = false;
				if(server!=null)
					server.close();
			}catch (Exception e) {
e.printStackTrace();				}
		}//可以无限监听client里面的输入 除非bflag为true 即关闭服务
		}
	
	
	
	
	
	
	
	class socketThread extends Thread{
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		public socketThread(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
			String str = "";
			while((str = br.readLine())!=null) {
				if(str.equals("END"))
					break;
				txtLog.append(str+"\n");
//br是client的pw 即sendMg里面输入的东西 
				String strRtn = "客户端的反馈信息"+str;
				pw.println(strRtn);
				pw.flush();
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
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
		
		
		
		
		
		
	}
	
	
}
