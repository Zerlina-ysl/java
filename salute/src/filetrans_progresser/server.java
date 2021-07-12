package filetrans_progresser;
//未运行成功
//二改 进度条无法正常显示
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;
import java.net.*;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class server extends JFrame {

	private JPanel contentPane;
	
	private JTextField porttxt;
	public JProgressBar cppb;
	public JLabel cplabel;
	serverListener sl = null;
	private JTextArea txtLogin;
	/**
	 * Launch the application.
	 */
	ServerSocket server = null;
	
	private JTextField txtpath;
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
		
		JButton copybtn = new JButton("发送文件");
		copybtn.addActionListener(new BtnStartcpActionListener());
		copybtn.setBounds(168, 100, 117, 29);
		contentPane.add(copybtn);
		cppb = new JProgressBar();
		cppb.setBounds(125, 153, 146, 20);
		contentPane.add(cppb);
		cplabel = new JLabel("发送进度");
		cplabel.setBounds(282, 157, 61, 16);
		contentPane.add(cplabel);
		
		JButton startbtn = new JButton("开启服务");
		startbtn.addActionListener(new BtnStartActionListener());
		startbtn.setBounds(34, 55, 117, 29);
		contentPane.add(startbtn);
		
		JLabel iplabel = new JLabel("Port");
		iplabel.setBounds(24, 27, 61, 16);
		contentPane.add(iplabel);
		
		porttxt = new JTextField();
		porttxt.setText("9001");
		porttxt.setBounds(65, 22, 76, 26);
		contentPane.add(porttxt);
		porttxt.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 176, 237, 71);
		contentPane.add(scrollPane);
		
		txtLogin = new JTextArea();
		scrollPane.setViewportView(txtLogin);
		
		JButton quitbtn = new JButton("关闭服务");
		quitbtn.addActionListener(new BtnQuitActionListener());
		quitbtn.setBounds(34, 100, 117, 29);
		contentPane.add(quitbtn);
		
		JLabel lblNewLabel = new JLabel("文件源地址");
		lblNewLabel.setBounds(148, 60, 96, 16);
		contentPane.add(lblNewLabel);
		
		txtpath = new JTextField();
		txtpath.setBounds(224, 55, 130, 26);
		contentPane.add(txtpath);
		txtpath.setColumns(10);
	}
	private class BtnStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//开始服务
			
			
				int Port = Integer.parseInt(porttxt.getText().trim());
				
				try {
					server = new ServerSocket(Port);
					
					txtLogin.append("服务器已连接...\n");
					sl = new serverListener();
					sl.start();
					
		}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}}
		private class BtnQuitActionListener implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				//关闭服务
				try {
					txtLogin.append("服务器停止服务...\n");
					cppb.setValue(0);
					sl.stopThread();
					if(server!=null)
						server.close();
					
					
				}
				catch(Exception e){
					e.printStackTrace();
					
				}
			}
		}
		

	private class BtnStartcpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//发送文件
			sl.sendMSG();
		}
	}
	class serverListener extends Thread{
		public serverListener() {
			
		}
		volatile boolean bflag = true;
		copyThread ct =  null;
		public void run() {
			// TODO Auto-generated method stub
			
			while(bflag) {
				Socket socket = null;
				try {
					socket = server.accept();
					txtLogin.append("客户端连接"+socket+"\n");
					ct = new copyThread(socket);
				}catch (Exception e) {
					e.printStackTrace();
				}
					
				}
			}
		public void stopThread() {
			bflag = false;
		}
		public void sendMSG() {
			
			ct.start();
		}
		}
		
		
		
		
		
		
	
	class copyThread extends Thread{
		DataInputStream dis = null;
		DataOutputStream dos = null;
		FileOutputStream fos = null;
		Socket socket = null;
	
		public copyThread(Socket socket) {
			this.socket=socket;
		}
		public void run() {
	try {
			
			String str1 = txtpath.getText().trim();
			File f = new File(str1);
			txtLogin.append("文件源地址："+f+"\n");
			fos = new FileOutputStream(str1);
			dos = new DataOutputStream(fos);
			dis = new DataInputStream(socket.getInputStream());
			cppb.setValue(0);
			cppb.setMaximum((int)(f.length()));
			txtLogin.append("文件开始发送！" + "\n");
			byte [] buffer = new byte[1024];
			int incurrPos = 0;
			
			
			
			while((incurrPos=dis.read(buffer,0,1024))!=-1) {
				dos.write(buffer,0,incurrPos);//dos.write(buffer,0,icurrPos);
				cplabel.setText("复制进度："+incurrPos/1024+"%");
				
				cppb.setValue(incurrPos);
				dos.flush();
			}
			txtLogin.append("文件发送成功！" + "\n");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			try {
			if(socket!=null)
				socket.close();}
			catch(Exception e) {
				e.printStackTrace();}
			}
		}
		
	}
}	
	//开始服务->

