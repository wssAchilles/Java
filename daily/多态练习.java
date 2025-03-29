package daily;

public class 多态练习 {
    public static void main(String[] args) {
        //多态的好处：可以使用父类引用指向子类对象
        //多态的弊端：不能使用子类特有的方法
        //多态的前提：父类和子类之间有继承关系
        //多态的实现：方法重写
        //多态的表现形式：父类引用指向子类对象
        //多态的使用：父类引用调用子类重写的方法
        dog a = new dog();
        a.eat();
        animal b = new dog();
        b.eat();
        //编译时类型是animal，运行时类型是dog
        //父类提供了这个方法，才可以使用多态
    }
}
