package GUISocket.Chat.Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import GUISocket.Chat.Client.ClientMG;
import GUISocket.Chat.Client.SocketThread;
public class ClientForm extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel lblIp;
	public JTextField textIP;
	public JLabel label;
	public JTextField textPort;
	public JButton btnLogin;
	public JButton btnClose;
	public JPanel panel_1;
	public JTextArea textSend;
	public JButton btnSend;
	
	//PrintWriter pw=null;
//	ClientChat sThd;
	public JLabel label_1;
	public JTextField textUser;
	public JScrollPane scrollPane;
	public JTextArea textLog;
	public JScrollPane scrollPane_1;
	public JList listUser;
	public JButton button;
	public JScrollPane scrollPane_2;
	/**
	 * Launch the application.
	 */
	
	DefaultListModel<String> itemUsers;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm frame = new ClientForm();
					frame.setVisible(true);
					ClientMG.getClientMG().setWinForm(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientForm() {
		setTitle("\u5BA2\u6237\u7AEF");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 501, 70);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblIp = new JLabel("IP");
		lblIp.setBounds(10, 29, 54, 15);
		panel.add(lblIp);
		
		textIP = new JTextField();
		textIP.setText("192.168.88.2");
		textIP.setBounds(34, 26, 109, 21);
		panel.add(textIP);
		textIP.setColumns(10);
		
		label = new JLabel("\u7AEF\u53E3");
		label.setBounds(147, 29, 54, 15);
		panel.add(label);
		
		textPort = new JTextField();
		textPort.setText("9000");
		textPort.setBounds(180, 26, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);
		
		btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new BtnLoginActionListener());
		btnLogin.setBounds(369, 25, 61, 23);
		panel.add(btnLogin);
		
		btnClose = new JButton("\u5173\u95ED");
		btnClose.addActionListener(new BtnCloseActionListener());
		btnClose.setBounds(430, 25, 61, 23);
		panel.add(btnClose);
		
		label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setBounds(250, 29, 54, 15);
		panel.add(label_1);
		
		textUser = new JTextField();
		textUser.setBounds(293, 26, 66, 21);
		panel.add(textUser);
		textUser.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 481, 501, 98);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnSend = new JButton("\u53D1\u9001");
		btnSend.addActionListener(new BtnSendActionListener());
		btnSend.setBounds(427, 65, 64, 23);
		panel_1.add(btnSend);
		
		button = new JButton("\u7FA4\u53D1");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(359, 65, 64, 23);
		panel_1.add(button);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(10, 22, 481, 36);
		panel_1.add(scrollPane_2);
		
		textSend = new JTextArea();
		textSend.setLineWrap(true);
		scrollPane_2.setViewportView(textSend);
		textSend.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(null, "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 90, 309, 388);
		contentPane.add(scrollPane);
		
		textLog = new JTextArea();
		textLog.setLineWrap(true);
		scrollPane.setViewportView(textLog);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "\u5728\u7EBF\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setBounds(318, 90, 193, 388);
		contentPane.add(scrollPane_1);
		
		itemUsers=new DefaultListModel<String>();
		listUser = new JList(itemUsers);
		scrollPane_1.setViewportView(listUser);
	}

	private class BtnLoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//连接服务器
			String IP=textIP.getText().trim(); 
			int port=Integer.parseInt(textPort.getText().trim());	
			String user=textUser.getText().trim();  //用户名默认具有唯一性
			if (ClientMG.getClientMG().connect(IP, port, user))
				ClientMG.getClientMG().setLogTxt("连接服务器成功。");
			else {
				ClientMG.getClientMG().setLogTxt("连接服务器失败！");
			}

		}
	}
	private class BtnSendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(listUser.getSelectedIndex()>=0) {
				//发送一对一消息的代码，格式：MSG|SenderName|RecName|MSGInfo
				String sSender = ClientMG.getClientMG().getCurrThdName();
				String sRecName = "";
				sRecName = listUser.getSelectedValue().toString().trim();
				String sMSG = textSend.getText().trim();
				String strend = "MSG|" + sSender + "|" + sRecName + "|" + sMSG;
				ClientMG.getClientMG().sendMSG(strend);
				ClientMG.getClientMG().setLogTxt("[我说：]");
				ClientMG.getClientMG().setLogTxt(sMSG);
//				发送完消息后，清空发送框
				textSend.setText("");
			}
		}
	}
	private class BtnCloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(ClientMG.getClientMG().close()) {
				ClientMG.getClientMG().setLogTxt("该客户端关闭成功");
					
			}else {
				ClientMG.getClientMG().setLogTxt("关闭失败");
			}
		} 
	}
	//群发
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//发送一对一消息的代码，格式：ADD|SenderName|RecName|MSGInfo
			String sSender = ClientMG.getClientMG().getCurrThdName();
			String sRecName = "ALL";
			String sMSG = textSend.getText().trim();
			String strend = "MSG|" + sSender + "|" + sRecName + "|" + sMSG;
			ClientMG.getClientMG().sendMSG(strend);
			ClientMG.getClientMG().setLogTxt("[我说：]");
			ClientMG.getClientMG().setLogTxt(sMSG);
			textSend.setText("");
		}
	}

}
