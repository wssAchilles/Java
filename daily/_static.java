package daily;
//静态代码块
//构造代码块
//局部代码块------>   静态>构造代码块>局部代码块
public class _static {
    static int a = 10;
    static int b = 20;
    public static void main(String[] args) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(_static.a);
        System.out.println(_static.b);
        new Person().show();
    }
}
class Person{
    static {
        System.out.println("静态代码块");
    }
    {
        System.out.println("构造代码块");
    }
    public void show(){
        {
            System.out.println("局部代码块");
        }
        System.out.println("show");
    }
}
