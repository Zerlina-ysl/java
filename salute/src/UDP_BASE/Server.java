package Base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				DatagramSocket server=null;
				try {
					server = new DatagramSocket(8899);  //��ʼ��DatagramSocket
					System.out.println("UDP��������...");
					byte [] bsRec = new byte[1024];
					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);  //������һ��Packet
//					Datagrampacket����ǽ������ݣ�����һ��byte ���� ������Ԫ���ǳ�ʼֵ��
					server.receive(dpRec);   //����������ݵ�״̬������ʽ����
					
					String strRec = new String(bsRec, 0, dpRec.getLength(), "UTF-8");
											// ���ݱ����յ��ֽ����飬��ʼλ�ã����ݵĳ��ȣ�����ͨ�����ݱ���ȡ�����ַ����뼯
					System.out.println("���յ���Ϣ��"+strRec);
					
					//������Ϣ�����Ͷ�
					String strRTN ="������Ϣ��"+strRec;
					byte [] bsSend = strRTN.getBytes("UTF-8");  
					DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length,dpRec.getAddress(),dpRec.getPort());
					                                         //�ֽ����飨Ҫ���͵�����Դ�������ȣ�Ŀ��IP��Ŀ��Ķ˿ں�
					server.send(dpSend);
					
					
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
