package daily;
//静态代码块
//构造代码块
//局部代码块------>   静态>构造代码块>局部代码块
public class _static {
    static int a = 10;
    static int b = 20;
    public static void main(String[] args) {
        Person1 c=new Person1();
        Person1 d=new Person1();
        Person1.show();
        System.out.println(a);
        System.out.println(b);
    }
}
class Person1{
    static {
        System.out.println("静态代码块");//只调用一次
    }
    {
        System.out.println("构造代码块");
    }
    static public void show(){
        System.out.println("show");
        {
            System.out.println("局部代码块");
        }
    }
}
