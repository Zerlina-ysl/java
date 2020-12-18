import java.io.*;
import java.util.Scanner;
//创建文件后在控制台编写文件
public class zuoye_1{
    public static void main(String args[]){
        File file = new File("/Users/luna/Downloads/javacode","1.txt");

        try{
            while(!file.exists()) {
                file.createNewFile();
                System.out.println("创建成功");
            }

        }catch(Exception e){
            System.out.println("error"+e);
        }
        try{
            FileOutputStream on = new FileOutputStream(file);
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            byte b[] = input.getBytes();
            byte a[] = "HAPPY NEW YEAR".getBytes();
            on.write(a);
            System.out.println(file.getName()+"的大小"+file.length());
            on.close();

            on = new FileOutputStream(file,true);
            on.write(b,0,b.length);
            System.out.println(file.getName()+"的大小"+file.length());
            on.close();

        }catch(Exception e){
            System.out.println("error"+e);
        }

        try{
            FileInputStream in = new FileInputStream(file);
            int n=-1;
            byte []a=new byte[100];
            while((n=in.read(a,0,100))!=-1) {
                String s = new String(a,0,n);
                System.out.println(s);
            }//从源中读取到字节数组 到达文件末尾返回-1
        in.close();}
        catch(Exception e){
                System.out.println("error"+e);
            }

        }
    }
