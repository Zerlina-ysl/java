/**
 * 
 */
package TransSocket;

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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

/**
 * 
 */

/**
* @Description
* @author Yunze Qi
* @version
* @date 2021年4月13日下午5:59:45
* 
*/
public class Client extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblNewLabel;
	public JTextField txtIP;
	public JLabel lblNewLabel_1;
	public JTextField txtPort;
	public JButton btnNewButton;
	public JButton btnNewButton_1;
	public JPanel panel_1;
	public JLabel lblNewLabel_2;
	public JTextField txtDest;
	public JButton btnNewButton_3;
	public JProgressBar progressBar;
	public JScrollPane scrollPane;
	public JTextArea txtLog;
	
	SocketThread sThd = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setTitle("客户端");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u767B\u5F55\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 522, 126);
		contentPane.add(panel);
		
		lblNewLabel = new JLabel("服务器IP：");
		panel.add(lblNewLabel);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		panel.add(txtIP);
		txtIP.setColumns(10);
		
		lblNewLabel_1 = new JLabel("端口：");
		panel.add(lblNewLabel_1);
		
		txtPort = new JTextField();
		txtPort.setText("9000");
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new BtnNewButtonActionListener());
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("退出");
		btnNewButton_1.addActionListener(new BtnNewButton_1ActionListener());
		panel.add(btnNewButton_1);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "\u4F20\u8F93\u9009\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 134, 522, 104);
		contentPane.add(panel_1);
		
		lblNewLabel_2 = new JLabel("接收地址：");
		lblNewLabel_2.setBounds(20, 32, 70, 15);
		panel_1.add(lblNewLabel_2);
		
		txtDest = new JTextField();
		txtDest.setColumns(10);
		txtDest.setBounds(100, 29, 290, 21);
		panel_1.add(txtDest);
		
		btnNewButton_3 = new JButton("接收");
		btnNewButton_3.addActionListener(new BtnNewButton_3ActionListener());
		btnNewButton_3.setBounds(400, 28, 93, 23);
		panel_1.add(btnNewButton_3);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(20, 73, 492, 15);
		panel_1.add(progressBar);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new TitledBorder(null, "\u6D88\u606F\u65E5\u5FD7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 248, 522, 319);
		contentPane.add(scrollPane);
		
		txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);
	}
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			登录
			int PORT = Integer.parseInt(txtPort.getText().trim());
			String IP = txtIP.getText().trim();
			
			try {
				Socket socket = new Socket(IP, PORT);
				txtLog.append("客户端连接：" + socket + "\n");
				sThd = new SocketThread(socket);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class BtnNewButton_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			退出
			 
	}
	private class BtnNewButton_3ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
//			文件接收
			sThd.start();
		}
	}
	
	private class SocketThread extends Thread {
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		public SocketThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				String strFile = txtDest.getText().trim();
				txtLog.append("文件接收地址：" + strFile + "\n");
				
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(new FileOutputStream(strFile));
				
				progressBar.setMaximum(socket.getInputStream().available());
				progressBar.setValue(0);
				byte [] buffer = new byte [1024];  //文件缓冲区
				int icurrPos = 0;
				txtLog.append("文件开始接收！" + "\n");
				while ((icurrPos = dis.read(buffer, 0, 1024)) != -1) {
					dos.write(buffer, 0, icurrPos);
					dos.flush();
					progressBar.setValue(progressBar.getValue() + icurrPos);
				}
				txtLog.append("文件接收完成！" + "\n");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public void socketQuit() {
			try {
				if (dis != null)
					dis.close();
				if (dos != null)
					dos.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
