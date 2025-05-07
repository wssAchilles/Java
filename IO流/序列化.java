package IO流;

import java.io.*;

public class 序列化 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        method();//序列化
        method2();//反序列化
    }
    public static void method() throws IOException {
        student s = new student("许子祺", 18);
        //创建序列化对象
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("D:\\Java\\3.txt"));

        //开始序列化
        out.writeObject(s);

        //释放资源
        out.close();
    }

    public static void method2() throws IOException, ClassNotFoundException {
        //创建反序列化对象
        ObjectInputStream in=new ObjectInputStream(new FileInputStream("D:\\Java\\3.txt"));

        //开始反序列化
        Object obj=in.readObject();//读取一个对象，返回Object类型
        System.out.println(obj);//输出对象的内存地址

        //释放资源
        in.close();
    }
}
//序列化的对象必须实现Serializable接口
class student implements java.io.Serializable{
    //序列化的对象必须有一个默认构造器
    private static final long serialVersionUID = 1L;
    String name;
    int age;
    public student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String toString() {
        return "student[name='" + name + "', age=" + age + "]";
    }
}