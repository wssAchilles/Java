package ex2_23030327;
//定义一个抽象类 Shape，在其中声明计算面积 Area()和周长 Perimeter()方法
//23030327许子祺
import java.text.DecimalFormat;
import static java.lang.Math.PI;
abstract class Shape{
    String name;
    public Shape(String name){
        this.name=name;
        System.out.println("名称："+name);
    }
    abstract  public double Area();
    abstract  public double Perimeter();
}
class Rectangle extends Shape{
    double length;
    double width;
    public Rectangle(String name,double length,double width){
        super(name);
        this.length=length;
        this.width=width;
    }
    public double Area(){
        return length*width;
    }
    public double Perimeter(){
        return 2*(length+width);
    }
}
class circle extends Shape{
    double radius;
    public circle(String name,double radius){
        super(name);
        this.radius=radius;
    }
    public double Area(){
        return PI*radius*radius;
    }
    public double Perimeter(){
        return 2*PI*radius;
    }
}
public class ex2_2 {
    public static void main(String[]args){
        Shape r=new Rectangle("矩形",5.43,10.98);
        System.out.println(" 面积："+r.Area()+" 周长："+r.Perimeter());
        Shape c=new circle("圆",9.543);
        DecimalFormat df = new DecimalFormat("0.000");
        System.out.println(" 面积："+df.format(c.Area())+" 周长："+df.format(c.Perimeter()));
    }
}
