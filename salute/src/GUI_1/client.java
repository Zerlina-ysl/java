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
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class client extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblIp;
	public JTextField txtIP;
	public JLabel label;
	public JTextField txtPort;
	public JButton btnLogin;
	public JButton btnQuit;
	public JPanel panel_1;
	public JTextField txtSend;
	public JButton btnSend;
	public JLabel label_1;
	public JTextField txtLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client frame = new client();
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
	public client() {
		setTitle("\u5BA2\u6237\u7AEF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 434, 62);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblIp = new JLabel("IP");
		lblIp.setBounds(10, 28, 24, 15);
		panel.add(lblIp);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setBounds(32, 25, 103, 21);
		panel.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setText("9001");
		txtPort.setBounds(182, 25, 66, 21);
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new BtnLoginActionListener());
		
		
		label = new JLabel("端口");
		label.setBounds(145, 28, 35, 15);
		panel.add(label);
		btnLogin.setBounds(258, 24, 79, 23);
		panel.add(btnLogin);
		
		btnQuit = new JButton("\u9000\u51FA");
		btnQuit.addActionListener(new BtnExitActionListener());
		

		btnQuit.setBounds(345, 24, 79, 23);
		panel.add(btnQuit);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 69, 434, 130);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtSend = new JTextField();
		txtSend.setBounds(10, 19, 335, 31);
		panel_1.add(txtSend);
		txtSend.setColumns(10);
		
		btnSend = new JButton("\u53D1\u9001");
		btnSend.addActionListener(new BtnSendActionListener());
		
		btnSend.setBounds(355, 20, 69, 31);
		panel_1.add(btnSend);
		
		label_1 = new JLabel("\u53CD\u9988\u7ED3\u679C");
		label_1.setBounds(10, 61, 98, 24);
		panel_1.add(label_1);
		
		txtLog = new JTextField();
		txtLog.setBounds(10, 83, 414, 37);
		panel_1.add(txtLog);
		txtLog.setColumns(10);
		
		
		
	}
	socketThread st = null;
	private class BtnLoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//登录
			
			int PORT = Integer.parseInt(txtPort.getText());
			String IP = txtIP.getText();
			
			try {
				Socket socket = new Socket(IP,PORT);
				txtLog.setText("服务器已连接...");
				st = new socketThread(socket);
				st.start();
				
			}catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}
			
		}
	}
	private class BtnExitActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//退出
			st.socketQuit();
		}
	}
	private class BtnSendActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//发送
			st.sendMG(txtSend.getText().trim());
			//trim()函数用于去除字符串两端的空格（空白字符），并返回处理后的字符串。
		}
	}
	class socketThread extends Thread{
		BufferedReader br = null;
		PrintWriter pw = null;
		Socket socket = null;
	
		public socketThread(Socket socket){
			this.socket = socket;
		}
		public void run() {
			// TODO Auto-generated method stub
			try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
			String str = "";
			while((str=br.readLine())!=null) {
				if(str.equals("END"))
					break;//br是server的pw 即strRtn
				txtLog.setText(str);//str=server.strRtn=服务器的反馈信息+server.str
			}
	}catch (Exception e) {
		e.printStackTrace();// TODO: handle exception
	}finally {
		// TODO: handle finally clause
		try {
			if (pw != null) pw.close();
			if (br != null) br.close();
			if (socket != null) socket.close();					
		} catch (IOException e) {					
			e.printStackTrace();
		}	
	}
		}
		
		public void sendMG(String send) {
			pw.println(send);
			pw.flush();
			
		}
		public void socketQuit() {
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
