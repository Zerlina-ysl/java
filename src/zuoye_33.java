import java.io.*;
public class zuoye_3 {
    public static void main(String args[]){
        File sourceFile = new File("/Users/luna/IdeaProjects/untitled1/src","zuoye_3.java");
        File targetFile = new File("/Users/luna/IdeaProjects/untitled1/src","zuoye_33.java");
int n=-1;
        char b[] =new char[100];
try{
    while(!targetFile.exists()) {

        targetFile.createNewFile();
        System.out.println("创建成功");
    }
}
catch(Exception e){
    System.out.println("error"+e);
    }
finally{
    System.out.println("hh");}
try{Writer on = new FileWriter(targetFile,true);
    Reader in = new FileReader(sourceFile);
    while((n=in.read(b))!=-1){
        on.write(b,0,100); }
on.flush();
on.close();
}
catch(Exception e){
        System.out.println("error"+e);
    }



        }
}
sh();