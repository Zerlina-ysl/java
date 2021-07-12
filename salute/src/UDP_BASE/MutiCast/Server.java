package Base.MutiCast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MulticastSocket server =null;
				try {
					server = new MulticastSocket(8899);  //��ʼ��DatagramSocket
					InetAddress ipGroup =InetAddress.getByName("224.1.1.1");  //�鲥��ַ					
					server.joinGroup(ipGroup);  //�����鲥��ַ
					
					System.out.println("UDP��������...");
					byte [] bsRec = new byte[1024];
					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);  //������һ��Packet
//					Datagrampacket����ǽ������ݣ�����һ��byte ���� ������Ԫ���ǳ�ʼֵ��
					server.receive(dpRec);   //����������ݵ�״̬������ʽ����
					
					String strRec = new String(bsRec, 0, dpRec.getLength(), "UTF-8");
											// ���ݱ����յ��ֽ����飬��ʼλ�ã����ݵĳ��ȣ�����ͨ�����ݱ���ȡ�����ַ����뼯
					System.out.println("���յ���Ϣ��"+strRec);
					
//					//������Ϣ�����Ͷ�
//					String strRTN ="������Ϣ��"+strRec;
//					byte [] bsSend = strRTN.getBytes("UTF-8");  
//					DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length,dpRec.getAddress(),dpRec.getPort());
//					                                         //�ֽ����飨Ҫ���͵�����Դ�������ȣ�Ŀ��IP��Ŀ��Ķ˿ں�
//					server.send(dpSend);
					
					
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
