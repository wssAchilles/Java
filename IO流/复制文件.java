package IO流;

import java.io.*;

//用来测试文件复制--高效的字节流
public class 复制文件 {
    public static void main(String[] args) throws IOException {
        //1.读取源文件的数据
        InputStream in =new BufferedInputStream(new FileInputStream("D:\\Java\\1.txt"));

        //2.写出数据到目标文件中
        OutputStream out=new BufferedOutputStream(new FileOutputStream("D:\\Java\\2.txt",true));//String类型可追加

        //3.读取数据同时写出
        int b=0;
        while((b=in.read())!=-1){
            out.write(b);//写出一个字节
        }

        //4.释放资源
        in.close();
        out.close();
    }
}
