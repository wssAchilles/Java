package IO流;

import java.io.*;

//Reader是字符流读取的父类
public class 字符流 {
    public static void main(String[] args) throws IOException {
        method();//FileReader字符流读取
        method2();//BufferedReader字符流读取
    }
    public static void method() throws IOException {
        //1.创建对象--参数是文件路径
        Reader in=new FileReader("D:\\Java\\1.txt");
        //Reader in2=new FileReader(new File("D:\\Java\\1.txt"));

        //2.开始读取
        //System.out.println(in.read());//读取一个字符，返回int类型  (a=97)
        int b=0;
        while((b=in.read())!=-1){
            System.out.print((char)b);//强转为字符
        }

        //3.关闭流
        in.close();
    }
    public static void method2() throws IOException {
        //1.创建对象--参数是文件路径
        Reader in=new BufferedReader(new FileReader("D:\\Java\\1.txt"));

        //2.开始读取
        //BufferedReader的read方法是重写的，效率更高
        int b=0;
        while((b=in.read())!=-1){
            System.out.print((char)b);//强转为字符
        }

        //3.释放资源
        in.close();
    }
}
