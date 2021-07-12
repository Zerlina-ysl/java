package GDI;
//五子棋判断输赢有bug
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class GDIdemo extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	int chessdata [][] = new int[15][15];
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GDIdemo frame = new GDIdemo();
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
	public GDIdemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new mypanel(chessdata);
		panel.setBounds(16, 40, 434, 432);
		contentPane.add(panel);
		panel.addMouseListener(new PanelMouseListener());
		
		btnNewButton = new JButton("绘制图片");
		btnNewButton.addActionListener(new btntupianActionListener());
		btnNewButton.setBounds(32, 489, 117, 29);

		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("绘制图形");
		btnNewButton_1.setBounds(155, 489, 117, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("清除");
		btnNewButton_2.addActionListener(new btnclearActionListener());
		btnNewButton_2.setBounds(300, 489, 117, 29);
		contentPane.add(btnNewButton_2);
	}
	private class btntupianActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			
			panel.repaint();
		}
	}
	private class btnclearActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<chessdata.length;i++) {
				for(int j=0;j<chessdata[0].length;j++){		
					chessdata[i][j]=0;
				}
			}
			panel.repaint();
		}
	}
	
	
	private class PanelMouseListener extends MouseAdapter{
		int iH = 20;
		int iW = 20;
		int iQW = 10;
		int iQH = 10;
		int chessType = 1;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			int x = e.getX();
			int y = e.getY();
			setTitle(x+"|"+y);
			int i = Math.round((x-iW)/20f);
			int j = Math.round((y-iH)/20f);
			if(i>=0&&i<15&&j>=0&&j<15) {
				if(chessdata[i][j]==0) {
					
					chessType = chessType==1?2:1;
					chessdata[i][j]=chessType;
					panel.repaint();
				if(isSuccess(i,j,chessType)==true)
					JOptionPane.showMessageDialog(null,chessType==1?"黑子赢了":"白子赢了");
				
			}
			}
			
		
		
		}
		
	}
	private boolean isSuccess(int x,int y,int chessType) {
		int [][]gamedata = chessdata;
		int c1 = 0,c2 = 0;
		for(int i=x;i>=0;i--) {
			if(gamedata[i][y]==chessType)
				c1++;
			else
				break;
			
		}
		for(int i=x;i<15;i++) {
			if(gamedata[i][y]==chessType)
				c2++;
			else
				break;
			
		}
		if(c1+c2-1-5>=0)
			return true;
		c1=0;
		c2=0;
		for(int i=y;i>=0;i--) {
			if(gamedata[x][i]==chessType)
				c1++;
			else
				break;
			
		}
		for(int i=y;i<15;i++) {
			if(gamedata[x][i]==chessType)
				c2++;
			else
				break;
			
		}
		if(c1+c2-1-5>=0)
			return true;
		c1=0;
		c2=0;
		for(int i=x;i>=0;i--) {
			for(int j=y;j>=0;j--) {
				if(gamedata[i][j]==chessType)
					c1++;
				else
					break;}}
		for(int i=x;i<15;i++) {
			for(int j=y;j<15;j++) {
				if(gamedata[i][j]==chessType)
					c2++;
				else
					break;}}
		if(c1+c2-1-5>=0)
			return true;
		c1=0;
		c2=0;
		for(int i=x;i>=0;i--) {
			for(int j=y;j<15;j++) {
				if(gamedata[i][j]==chessType) 
					c1++;
				else
					break;}}
		for(int i=x;i<15;i++) {
			for(int j=y;j>=0;j--) {
				if(gamedata[i][j]==chessType)
					c2++;
				else
					break;}}
		if(c1+c2-5-1>=0)
			return true;
		
		return false;
		
	}
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//}
//package GDIDemo;
//
//import java.awt.*;
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Graphics;
//import java.awt.Image;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class GDITest2 extends JFrame {
//
//	private JPanel contentPane;
//	public JPanel panel;
//	public JButton btnNewButton;
//	public JButton btnNewButton_1;
//	public JButton btnNewButton_2;
//
//	int [][] chessData = new int [15][15];   //¿ØÖÆÆå×ÓµÄÊý¾ÝÔ´
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GDITest2 frame = new GDITest2();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public GDITest2() {
//		setTitle("\u7ED8\u5236\u56FE\u5F62");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 543, 467);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		panel = new MyPanel(chessData);
//		panel.addMouseListener(new PanelMouseListener());
//		panel.setBounds(26, 28, 431, 329);
//		contentPane.add(panel);
//		
//		btnNewButton = new JButton("\u7ED8\u5236\u56FE\u5F62");
//		btnNewButton.addActionListener(new BtnNewButtonActionListener());
//		btnNewButton.setBounds(26, 367, 107, 40);
//		contentPane.add(btnNewButton);
//		
//		btnNewButton_1 = new JButton("\u7ED8\u5236\u56FE\u7247");
//		btnNewButton_1.addActionListener(new BtnNewButton_1ActionListener());
//		btnNewButton_1.setBounds(143, 367, 114, 40);
//		contentPane.add(btnNewButton_1);
//		
//		btnNewButton_2 = new JButton("\u6E05\u9664");
//		btnNewButton_2.addActionListener(new BtnNewButton_2ActionListener());
//		btnNewButton_2.setBounds(267, 367, 124, 40);
//		contentPane.add(btnNewButton_2);
//	}
//	private class BtnNewButtonActionListener implements ActionListener {
//		public void actionPerformed(ActionEvent arg0) {	
//		}
//	}
//	private class BtnNewButton_2ActionListener implements ActionListener {
//		public void actionPerformed(ActionEvent arg0) {
////			Çå³ý»æÖÆÄÚÈÝ
//			for(int i=0;i<chessData.length;i++) {
//				for(int j=0;j<chessData[0].length;j++) {
//					chessData[i][j] = 0;
//					
//				}
//			}
//			panel.repaint();
//		}
//	}
//	private class BtnNewButton_1ActionListener implements ActionListener {
//		public void actionPerformed(ActionEvent arg0) {			
//			for(int i=0;i<chessData.length;i++) {
//				for(int j=0;j<chessData[0].length;j++) {
//					chessData[i][j] = 2;
//				}
//			}
//			panel.repaint();
//			
//		}
//	}
//	
//	int iH= 20;
//	int iW =20;
//	int iQW = 10;
//	int iQH = 10;
//	int chessType = 1; 
//	private class PanelMouseListener extends MouseAdapter {
//		@Override
//		public void mouseClicked(MouseEvent arg) {
//			int x = arg.getX(); //»ñÈ¡Êó±êµÄX×ø±ê
//			int y = arg.getY(); //»ñÈ¡Êó±êµÄY×ø±ê
//			setTitle(x + "|" +y);
//			//½«×ø±êÖµ×ª»»ÎªÊý¾ÝÏÂ±êÖµ
//			int i = Math.round((x - iW)/20f);
//			int j = Math.round((y - iH)/20f);
//			if (x>=0 && i<15 && j>=0 && j<15) {
//				if(chessData[i][j]==0) {  //ÅÐ¶Ïµ±Ç°Î»ÖÃÊÇ·ñ´æÔÚÆå×Ó
//					chessType= chessType==1? 2:1;
//					chessData[i][j] = chessType;
//					panel.repaint();
//					
//					if(isSuccess(i,j,chessType)) {
//						JOptionPane.showMessageDialog(null, chessType==1?"ºÚ×ÓÓ®ÁË":"°××ÓÓ®ÁË");
//					}
//				}				
//				
//			}
//			
//			
//		}
//	}
//	private boolean isSuccess(int x,int y,int chessType) {
//		
//		int [][]GameData=chessData;
//		// =======¶ÔË®Æ½·½ÏòµÄÁ¬½ÓÆå×ÓÊý½øÐÐÅÐ¶Ï
//		int c1 = 0, c2 = 0;
//		// Ë®Æ½Ïò×óÊý
//		for (int j = x; j >= 0; j--) {
//			if (GameData[j][y] == chessType)
//				c1++;
//			else
//				break;
//		}
//		// Ë®Æ½ÏòÓÒÊý
//		for (int j = x; j < 15; j++) {
//			if (GameData[j][y] == chessType)
//				c2++;
//			else
//				break;
//		}
//		if (c1 + c2 - 1 >= 5) {
//			return true;
//		}
//		// ==========¶Ô´¹Ö±·½ÏòµÄÁ¬½ÓÆå×ÓÊý½øÐÐÅÐ¶Ï
//		//´¹Ö±ÏòÉÏ
//		c1 = 0;
//		c2 = 0;
//		for (int j = y; j >= 0; j--) {
//			if (GameData[x][j] == chessType)
//				c1++;
//			else
//				break;
//		}
//		// ´¹Ö±ÏòÏÂ
//		for (int j = y; j < 15; j++) {
//			if (GameData[x][j] == chessType)
//				c2++;
//			else
//				break;
//		}
//		if (c1 + c2 - 1 >= 5) {
//			return true;
//		}
//
//		// ========¶Ô×óÉÏ·½µ½ÓÒÏÂ·½ÏòµÄÁ¬½ÓÆå×ÓÊý½øÐÐÅÐ¶Ï
//		/* Ïò×óÉÏ·½Êý */
//		c1 = 0;
//		c2 = 0;
//		for (int j = x, k = y; (j >= 0) && (k >= 0); j--, k--) {
//			if (GameData[j][k] == chessType)
//				c1++;
//			else
//				break;
//		}
//		/* ÏòÓÒÏÂ·½Êý */
//		for (int j = x, k = y; (j < 15) && (k < 15); j++, k++) {
//			if (GameData[j][k] == chessType)
//				c2++;
//			else
//				break;
//		}
//		if (c1 + c2 - 1 >= 5) {
//			return true;
//		}
//
//		// ========¶ÔÓÒÉÏ·½µ½×óÏÂ·½ÏòµÄÁ¬½ÓÆå×ÓÊý½øÐÐÅÐ¶Ï
//		/* ÏòÓÒÉÏ·½Êý */
//		c1 = 0;
//		c2 = 0;
//		for (int j = x, k = y; (j >= 0) && (k >= 0); j++, k--) {
//			if (GameData[j][k] == chessType)
//				c1++;
//			else
//				break;
//		}
//		/* Ïò×óÏÂ·½Êý */
//		for (int j = x, k = y; (j < 15) && (k < 15); j--, k++) {
//			if (GameData[j][k] == chessType)
//				c2++;
//			else
//				break;
//		}
//		if (c1 + c2 - 1 >= 5) {
//			return true;
//		}
//
//		return false;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
