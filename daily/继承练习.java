package daily;
//继承可以传递性
//子类可以继承父类的属性和方法，同时功能可以扩展
//不能继承父类的私有属性和方法
public class 继承练习 {
    public static void main(String[] args) {
        dog d=new dog();
        d.eat();
        d.lookHome();
        System.out.println(d.name);
        System.out.println(d.age);
    }
}
class animal {
    protected String name;
    protected int age;
    public animal() {
        this.name = "animal";
        this.age = 10;
        System.out.println(name);
    }
    public void eat() {
        System.out.println("吃东西");
    }
}
class dog extends animal{
    public dog(){
        this.name = "dog";
        System.out.println(name);
    }
    public void lookHome(){
        System.out.println("看家");
    }
    //重写父类的方法
    public void eat() {
        System.out.println("吃狗粮");
    }
}