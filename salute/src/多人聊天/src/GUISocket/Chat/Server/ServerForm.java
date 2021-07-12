package GUISocket.Chat.Server;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import GUISocket.Chat.Server.serverListener;

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

public class ServerForm extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel label;
	public JTextField textPort;
	public JButton btnStart;
	public JButton btnEnd;
	public JScrollPane scrollPane;
	public JTextArea textLog;

	ServerSocket server = null;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);
					ServerMG.getClientMG().setWinForm(frame);   //将窗体对象传入管理 类
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerForm() {
		setTitle("\u591A\u4EBA\u804A\u5929\u670D\u52A1\u5668");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 444, 69);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(20, 34, 54, 15);
		panel.add(label);
		
		textPort = new JTextField();
		textPort.setText("9000");
		textPort.setBounds(60, 31, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);
		
		btnStart = new JButton("\u5F00\u542F\u670D\u52A1");
		btnStart.addActionListener(new BtnStartActionListener());
		btnStart.setBounds(175, 30, 93, 23);
		panel.add(btnStart);
		
		btnEnd = new JButton("\u5173\u95ED\u670D\u52A1");
		btnEnd.addActionListener(new BtnEndActionListener());
		btnEnd.setBounds(278, 30, 93, 23);
		panel.add(btnEnd);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(null, "\u6D88\u606F\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(0, 79, 444, 397);
		contentPane.add(scrollPane);
		
		textLog = new JTextArea();
		textLog.setEditable(false);
		textLog.setLineWrap(true);
		scrollPane.setViewportView(textLog);
	}
	serverListener slThd=null;
	private class BtnStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//开启服务
			
			int PORT = Integer.parseInt(textPort.getText().trim());   //监听端口号	
			try {
				server =new ServerSocket(PORT);
				ServerMG.getClientMG().setLogTxt("服务器开启...");			
				slThd=new serverListener(server);
				slThd.start();				
				
			} catch (IOException e) {				
				e.printStackTrace();
			} 
		}
	}
	private class BtnEndActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//关闭服务
			slThd.stopListener();
			ServerMG.getClientMG().closeserver();
			slThd.close();
		}
	}

}
