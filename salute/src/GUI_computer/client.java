package GUI_computer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class client extends JFrame {

	private JPanel contentPane;
	private JTextField txtip;
	private JTextField txtport;
	private JTextField txtopera1;
	private JTextField txtopera2;
	private JButton add;
	private JButton sub;
	private JButton mul;
	private JButton del;
	
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
		setBounds(100, 100, 491, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ip");
		lblNewLabel.setBounds(39, 26, 61, 16);
		contentPane.add(lblNewLabel);
		
		txtip = new JTextField();
		txtip.setText("127.0.0.1");
		txtip.setBounds(131, 21, 130, 26);
		contentPane.add(txtip);
		txtip.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("端口号");
		lblNewLabel_1.setBounds(39, 54, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		txtport = new JTextField();
		txtport.setText("9001");
		txtport.setBounds(131, 49, 130, 26);
		contentPane.add(txtport);
		txtport.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("操作数1");
		lblNewLabel_2.setBounds(6, 110, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("操作数2");
		lblNewLabel_3.setBounds(6, 179, 61, 16);
		contentPane.add(lblNewLabel_3);
		
		txtopera1 = new JTextField();
		txtopera1.setBounds(60, 105, 130, 26);
		contentPane.add(txtopera1);
		txtopera1.setColumns(10);
		
		
		txtopera2 = new JTextField();
		txtopera2.setBounds(60, 174, 130, 26);
		contentPane.add(txtopera2);
		txtopera2.setColumns(10);
		
		JButton btnLog = new JButton("登录");
		btnLog.addActionListener(new BtnLogActionListener());
		btnLog.setBounds(287, 26, 117, 29);
		contentPane.add(btnLog);
		
		JButton BtnQuit = new JButton("退出");
		BtnQuit.addActionListener(new BtnQuitActionListener());
		BtnQuit.setBounds(287, 84, 117, 29);
		contentPane.add(BtnQuit);
		
		add = new JButton("加法(+)");
		add.addActionListener(new BtnAddActionListener());
		add.setBounds(200, 105, 75, 29);
		contentPane.add(add);
		
		sub = new JButton("减法(-)");
		sub.addActionListener(new BtnSubActionListener());
		sub.setBounds(200, 140, 75, 29);
		contentPane.add(sub);
		
		mul = new JButton("乘法(*)");
		mul.addActionListener(new BtnMulActionListener());
		mul.setBounds(200, 179, 75, 29);
		contentPane.add(mul);
		
		del = new JButton("除法(/)");
		del.addActionListener(new BtnDelActionListener());
		del.setBounds(200, 216, 75, 29);
		contentPane.add(del);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "\u53CD\u9988\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(287, 179, 178, 66);
		contentPane.add(scrollPane);
		
		txtlogin = new JTextField();
		scrollPane.setViewportView(txtlogin);
		txtlogin.setColumns(10);
	}
	private class BtnLogActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//登录
			Socket socket = null;
			String IP = txtip.getText().trim();
			int PORT = Integer.parseInt(txtport.getText().trim());
			try {
				socket = new Socket(IP,PORT);
				st = new socketThread(socket);
				st.start();
				txtlogin.setText("连接成功"+socket);
				
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	private JTextField txtlogin;

	private class BtnQuitActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//退出
			st.quit();
			txtlogin.setText("客户端已退出");
		}
	}
	private class BtnAddActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//加法
			int s1 = Integer.parseInt(txtopera1.getText().trim());
			int s2 = Integer.parseInt(txtopera2.getText().trim());
			int s3 = s1+s2;
			txtlogin.setText(s1+"+"+s2+"="+s3);
			st.sendMSG("ADD|"+s1+"+"+s2+"="+s3);
			
			
		}
	}private class BtnSubActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//减法
			int s1 = Integer.parseInt(txtopera1.getText().trim());
			int s2 = Integer.parseInt(txtopera2.getText().trim());
			int s3 = s1-s2;
			txtlogin.setText(s1+"-"+s2+"="+s3);
			st.sendMSG("SUB"+s1+"-"+s2+"="+s3);
		}
	}private class BtnMulActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//乘法
			int s1 = Integer.parseInt(txtopera1.getText().trim());
			int s2 = Integer.parseInt(txtopera2.getText().trim());
			int s3 = s1*s2;
			txtlogin.setText(s1+"*"+s2+"="+s3);
			st.sendMSG("MUL"+s1+"*"+s2+"="+s3);
		}
	}private class BtnDelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//除法
			int s1 = Integer.parseInt(txtopera1.getText().trim());
			int s2 = Integer.parseInt(txtopera2.getText().trim());
			int s3 = s1/s2;
			txtlogin.setText(s1+"/"+s2+"="+s3);
			st.sendMSG("DEL"+s1+"/"+s2+"="+s3);
		}
	}
class socketThread extends Thread{
	BufferedReader br  = null;
	PrintWriter pw = null;
	Socket socket = null;
	public socketThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
	
	try {
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
	
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")));
		String str = "";
		while((str=br.readLine())!=null) {
			if(str.equals("END"))
				break;
			txtlogin.setText(str);
				
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
	public void sendMSG(String send) {
		pw.println(send);
		pw.flush();
		
	}
	public void quit() {
		try {
			if(pw!=null)
				pw.close();
			if(br!=null)
				br.close();
			if(socket!=null)
				socket.close();
		}catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
}
