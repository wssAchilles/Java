package daily;
//2.22
public class 抽象类练习 {
    public static void main(String[] args) {
        //抽象类不能被实例化
        //抽象类可以有final方法
        //抽象类可以有final属性
        //抽象类可以有private方法
        //抽象类可以有private属性
        //抽象类可以有protected方法
        //抽象类可以有protected属性
        Animal2 a=new Dog2();
        System.out.println(a.name);
        System.out.println(Animal2.age);
        a.play();
        System.out.println(a);
        //System.out.println(a.toString());//返回getClass().getName() + '@' + Integer.toHexString(hashCode())
    }
}
abstract class Animal2 {
    String name;
    static final int age=10;
    public Animal2() {
        this.name = "animal";
        System.out.println(name);
    }
    public void play() {
        System.out.println("玩耍");
    }
    abstract public void eat();
}
class Dog2 extends Animal2 {
    public Dog2() {
        this.name = "dog";
        System.out.println(super.name);
    }
    public void eat() {
        System.out.println("吃狗粮");
    }
    public void play() {
        System.out.println("玩球");
    }
//    public void lookHome() {
//        System.out.println("看家");
//    }
}