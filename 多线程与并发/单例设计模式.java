package 多线程与并发;
//单例设计模式17PM
public class 单例设计模式 {
    public static void main(String[] args) {
        Mysingle my1=Mysingle.getmy();
        Mysingle my2=Mysingle.getmy();
        System.out.println(my1==my2);
    }
}
//创建自己的类
class Mysingle{
    //1.私有化构造方法
    private Mysingle(){};

    //2.在类的内部，创建好对象
    private static Mysingle my=new Mysingle();

    //3.提供公共的全局访问点
    static public Mysingle getmy(){
        return my;
    }
}