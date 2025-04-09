package homework;
interface Mapper1{
    double Area();
}
interface Mapper2{
    double Perimeter();
}
class Square implements Mapper1,Mapper2{
    double length;
    public Square(double length){
        this.length=length;
    }
    public double Area(){
        return length*length;
    }
    public double Perimeter(){
        return length*4;
    }
    public void show(){
        System.out.println("正方形的面积为："+Area());
        System.out.println("正方形的周长为："+Perimeter());
    }
}
public class _4002 {
    public static void main(String[] args) {
        Square a = new Square(2.4);
        a.show();
    }
}
