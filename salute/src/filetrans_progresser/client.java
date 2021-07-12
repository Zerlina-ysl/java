package filetrans_progresser;
//未运行成功
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
public class client extends JFrame {
	private JTextArea txtLog;
	private JPanel contentPane;
	private JTextField txtip;
	private JTextField txtport;
	private JTextField txtposition;
socketThread st = null;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnstart = new JButton("接收");
		btnstart.addActionListener(new BtnStartActionListener());
		btnstart.setBounds(129, 83, 117, 29);
		contentPane.add(btnstart);
		
		JLabel iplabel = new JLabel("接收IP地址");
		iplabel.setBounds(24, 25, 83, 16);
		contentPane.add(iplabel);
		
		txtip = new JTextField();
		txtip.setText("127.0.0.1");
		txtip.setBounds(119, 20, 130, 26);
		contentPane.add(txtip);
		txtip.setColumns(10);
		
		JLabel portlabel = new JLabel("端口");
		portlabel.setBounds(24, 55, 61, 16);
		contentPane.add(portlabel);
		
		txtport = new JTextField();
		txtport.setText("9001");
		txtport.setBounds(119, 50, 130, 26);
		contentPane.add(txtport);
		txtport.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 124, 254, 121);
		contentPane.add(scrollPane);
		
		 txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);
		
		JButton btnLogin = new JButton("登录");
		btnLogin.addActionListener(new BtnLoginActionListener());
		btnLogin.setBounds(6, 83, 117, 29);
		contentPane.add(btnLogin);
		
		JButton btnquit = new JButton("退出");
		btnquit.addActionListener(new BtnQuitActionListener());
		btnquit.setBounds(235, 83, 117, 29);
		contentPane.add(btnquit);
		
		JLabel lblNewLabel = new JLabel("接收地址");
		lblNewLabel.setBounds(6, 250, 61, 16);
		contentPane.add(lblNewLabel);
		
		txtposition = new JTextField();
		txtposition.setBounds(95, 245, 130, 26);
		contentPane.add(txtposition);
		txtposition.setColumns(10);
	}
	private class BtnLoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//登录
			int PORT = Integer.parseInt(txtport.getText().trim());
			String IP = txtip.getText().trim();
			Socket socket = null;
			try {
				socket = new Socket(IP,PORT);
				txtLog.append("客户端已连接"+socket+"\n");
				st = new socketThread(socket);
			
						
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	private class BtnStartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//接收文件
			st.start();
		}
	}
	private class BtnQuitActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//退出
			st.stopThread();
			
			
		}
	}
	class socketThread extends Thread{
		
		
		public socketThread(Socket socket) {
			this.socket = socket;
		}
		Socket socket = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
				
		@Override
		public void run() {
			
			try {
			// TODO Auto-generated method stub
			String str2 = txtposition.getText().trim();
			txtLog.append("文件接收地址:"+str2);
			
			File f = new File(str2);
			FileInputStream fos = new FileInputStream(f);
			
			DataInputStream dis = new DataInputStream(fos);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			byte[] buffer = new byte[1024];//文件读取的缓冲
			int icurrPos = 0;
			txtLog.append("文件开始接收...");
			
			while((icurrPos = dis.read(buffer,0,1024))!=-1) {
				dos.write(buffer,0,icurrPos);
				dos.flush();//写入字节流与读出的字节流要相同
				
				
			}
			txtLog.append("文件复制成功");}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		
		}
		public void stopThread() {
			try {
				if(socket!=null)
					socket.close();
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		
		
	}
}
