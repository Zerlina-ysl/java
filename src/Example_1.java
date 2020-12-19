//检测某文件夹是否有某后缀的文件
import java.io.*;
public class Example_1 {
    public static void main(String args[]){
        File f1 = new File("/Users/luna/Downloads/javacode");
        File filelist[] = f1.listFiles();

            for(File f2:filelist){
            String filename = f2.getName();
            if(filename.endsWith(".java")){
                System.out.println(filename); } }

    }

}
