package IO流;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class 字符流写出 {
    public static void main(String[] args) throws IOException {
        method();//FileWriter字符流写出
        method2();//BufferedWriter字符流写出
    }
    public static void method() throws IOException {
        //1.创建对象--参数是文件路径,默认是数据覆盖模式，可以改成追加模式，需要传入第二个参数true
        //Writer是字符流写出的父类
        //FileWriter是字符流写出的子类
        Writer out = new FileWriter("D:\\Java\\2.txt", true);//String类型可追加
        //Writer out = new FileWriter(new File("D:\\Java\\2.txt"));//File类型

        //2.开始写出
        // void write(int)
        // void write(char[])
        // void write(char[], int, int)
        // void write(String)
        // void write(String, int, int)
        out.write("许子祺",0,3);

        //3.关闭流
        out.close();
    }
    public static void method2() throws IOException {
        //1.创建对象--参数是文件路径
        //BufferedWriter(Writer out)
        Writer out = new BufferedWriter(new FileWriter("D:\\Java\\2.txt", true));//String类型可追加

        //2.开始写出
        //BufferedWriter的write方法是重写的，效率更高
        out.write("牛逼");

        //3.释放资源
        out.close();
    }
}
