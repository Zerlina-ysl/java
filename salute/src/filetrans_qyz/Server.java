/**
 * 
 */
package TransSocket;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 */

/**
* @Description
* @author Yunze Qi
* @version
* @date 2021年4月13日下午4:40:32
* 
*/
public class Server extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel labelPort;
	public JTextField txtPort;
	public JButton btnNewButton;
	public JButton btnNewButton_1;
	public JScrollPane scrollPane;
	public JTextArea txtLog;
	public JPanel panel_1;
	public JLabel lblNewLabel_1;
	public JTextField txtSrc;
	public JButton btnNewButton_2;
	public JProgressBar progressBar;
	
	ServerSocket server = null;
	ServerListener slThd = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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
	public Server() {
		setTitle("服务端");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u7AEF\u53E3\u76D1\u542C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 10, 522, 90);
		contentPane.add(panel);
		
		labelPort = new JLabel("端口");
		panel.add(labelPort);
		
		txtPort = new JTextField();
		txtPort.setText("9000");
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		btnNewButton = new JButton("开启服务");
		btnNewButton.addActionListener(new BtnNewButtonActionListener());
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("关闭服务");
		btnNewButton_1.addActionListener(new BtnNewButton_1ActionListener());
		panel.add(btnNewButton_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new TitledBorder(null, "\u6D88\u606F\u65E5\u5FD7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(0, 243, 522, 311);
		contentPane.add(scrollPane);
		
		txtLog = new JTextArea();
		txtLog.setLineWrap(true);
		scrollPane.setViewportView(txtLog);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u4F20\u8F93\u9009\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 110, 522, 123);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_1 = new JLabel("源地址：");
		lblNewLabel_1.setBounds(10, 37, 70, 15);
		panel_1.add(lblNewLabel_1);
		
		txtSrc = new JTextField();
		txtSrc.setColumns(10);
		txtSrc.setBounds(80, 34, 306, 21);
		panel_1.add(txtSrc);
		
		btnNewButton_2 = new JButton("发送");
		btnNewButton_2.addActionListener(new BtnNewButton_2ActionListener());
		btnNewButton_2.setBounds(408, 33, 93, 23);
		panel_1.add(btnNewButton_2);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 80, 502, 15);
		panel_1.add(progressBar);
	}
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
//			开启服务
			int PORT = Integer.parseInt(txtPort.getText().trim());
			
			try {
				server = new ServerSocket(PORT);
				txtLog.append("服务器已开启..." + "\n");
				slThd = new ServerListener();
				slThd.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class BtnNewButton_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			关闭服务
			try {
				txtLog.append("服务器停止服务..." + "\n");
				progressBar.setValue(0);
				slThd.stopCircle();
				if (server != null)
					server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class BtnNewButton_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
//			发送文件
			slThd.sendFile();
		}
	}
	class ServerListener extends Thread {
		volatile boolean flag = true;
			SocketThread sThd = null;
		public ServerListener() {
		}
		@Override
		public void run() {
			while (flag) {
				try {
					Socket socket = server.accept();
					txtLog.append("客户端连接：" + socket + "\n");
					sThd = new SocketThread(socket);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		public void stopCircle() {
			flag = false;
		}
		public void sendFile () {
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
					String strFile = txtSrc.getText().trim();
					File file = new File(strFile);
					txtLog.append("文件源地址：" + strFile + "\n");
					
					dis = new DataInputStream(new BufferedInputStream(new FileInputStream(strFile)));
					dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					
					progressBar.setMaximum((int) (file.length()));
					progressBar.setValue(0);
					byte [] buffer = new byte[1024];  //文件缓冲区  
					int icurrPos = 0;
					txtLog.append("文件开始发送！" + "\n");
					while ((icurrPos = dis.read(buffer, 0, 1024)) != -1) {
						dos.write(buffer, 0, icurrPos);
						dos.flush();
						progressBar.setValue(progressBar.getValue() + icurrPos);
					}
					txtLog.append("文件发送完成！" + "\n");
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
	}
}

