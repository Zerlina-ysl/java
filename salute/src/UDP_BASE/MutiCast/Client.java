package Base.MutiCast;

import java.net.*;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				DatagramSocket server=null;
				try {
					server = new DatagramSocket();  //��ʼ��DatagramSocket
					
					InetAddress ipaddr = InetAddress.getByName("224.1.1.1");  //�����ַ������Ϣ
					int port = 8899;
					//������Ϣ�����Ͷ�
					String strRTN ="Hello world!";
					byte [] bsSend = strRTN.getBytes("UTF-8");  
					DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length,ipaddr,port);
					                                         //�ֽ����飨Ҫ���͵�����Դ�������ȣ�Ŀ��IP��Ŀ��Ķ˿ں�
					server.send(dpSend);
					
					
					
//					byte [] bsRec = new byte[1024];
//					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);  //������һ��Packet
////					Datagrampacket����ǽ������ݣ�����һ��byte ���� ������Ԫ���ǳ�ʼֵ��
//					server.receive(dpRec);   //����������ݵ�״̬������ʽ����					
//					String strRec = new String(bsRec, 0, dpRec.getLength(), "UTF-8");
//											// ���ݱ����յ��ֽ����飬��ʼλ�ã����ݵĳ��ȣ�����ͨ�����ݱ���ȡ�����ַ����뼯
//					System.out.println("���յ���Ϣ��"+strRec);					
					
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if (server!=null)
						server.close();
				}
				
	}

}
