package IO流;

import java.io.*;
//InputStream抽象类是字节流读取的父类
public class 字节流 {
    public static void main(String[] args) throws IOException {
        method();//FileInputStream字节流读取
        method2();//BufferedInputStream字节流读取
    }

    private static void method2() throws IOException{
        //1.创建对象--参数是文件路径   BufferedInputStream(InputStream in)
        InputStream in=new BufferedInputStream(new FileInputStream("D:\\Java\\1.txt"));//String类型
        //2.开始读取
        //BufferedInputStream的read方法是重写的，效率更高
        int b=0;
        while((b=in.read())!=-1){
            System.out.print((char)b);//强转为字符
        }
        //3.释放资源
        in.close();
    }

    private static void method() throws IOException {
        //InputStream抽象类是字节流读取的父类
        //OutputStream抽象类是字节流写入的父类
        //FileInputStream是字节流读取的子类
        //FileOutputStream是字节流写入的子类

        //1.创建对象--参数是文件路径
        InputStream in=new FileInputStream("D:\\Java\\1.txt");//String类型
        InputStream in2=new FileInputStream(new File("D:\\Java\\1.txt"));//File类型
        //2.开始读取
        //System.out.println(in.read());//读取一个字节，返回int类型(a)97
        //没数据返回-1
        int b=in.read();
        while(b!=-1){
            System.out.print((char)b);//强转为字符
            b=in.read(); //读取下一个字节
        }
        //3.关闭流
        in.close();
    }
}
