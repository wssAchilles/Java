package Examination;
abstract class shape{
    String name;
    public shape(String name) {
        this.name = name;
    }
    abstract public double Area();
    abstract public double Perimeter();
}

class Rectange extends shape{
    double length;
    double width;
    public Rectange(String name,double length,double width){
        super(name);
        this.length=length;
        this.width=width;
    }
    public double Area(){
        return length*width;
    }
    public double Perimeter(){
        return 2*(width+length);
    }
    public void Print(){
        System.out.println(name+"的面积为:"+Area()+" 周长为:"+Perimeter());
    }
}

class Circle extends shape{
    double r;
    public Circle(String name,double r){
        super(name);
        this.r=r;
    }
    public double Area(){
        return 3.14*r*r;
    }
    public double Perimeter(){
        return 2*3.14*r;
    }
    public void Print(){
        System.out.println(name+"的面积为:"+Area()+" 周长为:"+Perimeter());
    }
}
public class oop {
    public static void main(String[] args) {
        Rectange a=new Rectange("矩形",3,4);
        a.Print();
        Circle b=new Circle("圆形",4);
        b.Print();
    }
}
