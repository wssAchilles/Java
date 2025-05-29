package Examination;

import java.io.*;

//1.xzq
//2.许子祺
//3.123
public class 文件操作 {
    public static void main(String[] args) throws Exception{
        String path1="D:\\Java\\1.txt";
        String path2="D:\\Java\\2.txt";
        Writer out1=new FileWriter(path1,true);
        out1.write("xzq");
        out1.write("许子祺");
        out1.write("123");
        out1.close();
        InputStream in1=new BufferedInputStream(new FileInputStream(path1));
        OutputStream out2=new BufferedOutputStream(new FileOutputStream(path2,true));
        int b=0;
        while((b=in1.read())!=-1){
            out2.write(b);
        }
        in1.close();
        out2.close();
    }
}
