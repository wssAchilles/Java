package daily;

import org.w3c.dom.ls.LSOutput;

public class final关键字 {
    public static void main(String[] args) {
    son s=new son();
    s.show();
    }
}
//父类被final修饰，不能被继承
class father {
    static final String name="许";//定义常量
    public final void work(){
        System.out.println("工作");
    }

}
class son extends father {
    //子类不能重写父类的final方法
    public void show() {
        System.out.println(name);
    }
}
