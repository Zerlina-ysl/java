package Base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				DatagramSocket server=null;
				try {
					server = new DatagramSocket(8899);  //初始化DatagramSocket
					System.out.println("UDP服务启动...");
					byte [] bsRec = new byte[1024];
					DatagramPacket dpRec = new DatagramPacket(bsRec, bsRec.length);  //创建了一个Packet
//					Datagrampacket如果是接收数据，则是一个byte 数组 ，数组元素是初始值。
					server.receive(dpRec);   //处理接收数据的状态，阻塞式命令
					
					String strRec = new String(bsRec, 0, dpRec.getLength(), "UTF-8");
											// 数据报接收的字节数组，开始位置，数据的长度（必须通过数据报获取），字符编码集
					System.out.println("接收的信息："+strRec);
					
					//反馈信息给发送端
					String strRTN ="返回信息："+strRec;
					byte [] bsSend = strRTN.getBytes("UTF-8");  
					DatagramPacket dpSend = new DatagramPacket(bsSend, bsSend.length,dpRec.getAddress(),dpRec.getPort());
					                                         //字节数组（要发送的数据源），长度，目标IP，目标的端口号
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
