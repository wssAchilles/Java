package IO流;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class 字节写出 {
    public static void main(String[] args) throws Exception {
       method();//FileOutputStream字节流写出
       method2();//BufferedOutputStream字节流写出
    }
    private static void method() throws Exception {
        //1.创建对象--参数是文件路径,默认是数据覆盖模式，可以改成追加模式，需要传入第二个参数true
        //OutputStream抽象类是字节流写入的父类
        //FileOutputStream是字节流写入的子类
        OutputStream out=new FileOutputStream("D:\\Java\\2.txt",true);//String类型可追加
        //OutputStream out=new FileOutputStream(new File("D:\\Java\\2.txt"));//File类型

        //2.开始写出
        // void write(int)
        // void write(byte[])
        // void write(byte[], int, int)
        out.write('x');//写出一个字节，返回int类型(x)120
        out.write(98);//写出一个int数字，返回int类型(b)98
        byte[] arr={'x','z','q','1'};//写出一个字节数组
        out.write(arr,0,4);//写出一个字节数组，返回int类型(xzq)120

        //3.关闭流
        out.close();
    }
    public static void method2() throws Exception {
        //1.创建对象--参数是文件路径
        //BufferedOutputStream(OutputStream out)
        OutputStream out=new BufferedOutputStream(new FileOutputStream("D:\\Java\\2.txt",true));//String类型可追加

        //2.开始写出
        //BufferedOutputStream的write方法是重写的，效率更高
        out.write('y');
        out.write(98);//写出一个字节，返回int类型(b)98
        byte[] arr={'w','s','s','2'};//写出一个字节数组
        out.write(arr,0,4);//写出一个字节数组，返回int类型(wss)119

        //3.释放资源
        out.close();
    }
}
