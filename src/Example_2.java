import java.io.*;
import java.util.Scanner;
//读写文件
public class Example_2 {
    //创建文件并写入内容
    public static void main(String args[]){
        try{
            int n=-1;
            byte []a = new byte[100];
            FileInputStream f1 = new FileInputStream("1.txt");
            //构建指向文件的输入流
//控制台从输入流读取数据
            while((n=f1.read(a,0,100))!=-1){
                String s = new String(a,0,n);
                System.out.println(s);
//int read(byte b[],int off,int len) 到达文件末尾返回-1
            }
            f1.close();
        }catch(IOException e) {
            System.out.println("error" + e);

        }finally {
            System.out.println("🐂");
        }

try{
    FileOutputStream on = new FileOutputStream("1.txt");
   //输出流向控制台写入数据

 byte str[]="河南大学".getBytes();
 byte s[] = "haha".getBytes();
    on.write(str);
   on.close();
   on = new FileOutputStream("1.txt",true);
   on.write(s,0,s.length);
   on.close();

}catch(Exception e){
    System.out.println("error"+e);
}
    }
}
