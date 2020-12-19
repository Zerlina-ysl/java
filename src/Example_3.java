

import java.io.*;
public class Example_3{
    public static void main(String args[]){
        try{  BufferedInputStream in = new BufferedInputStream(new FileInputStream("a.txt"));
        BufferedOutputStream on = new BufferedOutputStream(new FileOutputStream("1.txt"));

                int n=0;
                while((n=in.read())!=-1){
            on.write(n);

        }
        in.close();
        on.close();}
        catch(Exception e){
        System.out.println(e.toString());}
    }
}