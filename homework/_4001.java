package homework;
public class _4001 {
    public static void main(String[] args) {
        Cubiod a = new Cubiod(2.3, 4.5, 5.5);
        a.show();
    }
}
abstract class Tridimensional{
    abstract public double shape();
    abstract public double volume();
}
class Cubiod extends Tridimensional{
    double length;
    double width;
    double height;
    public Cubiod(double length,double width,double height){
        this.length=length;
        this.width=width;
        this.height=height;
    }
    public double shape(){
        return length*width;
    }
    public double volume(){
        return length*width*height;
    }
    public void show(){
        System.out.println("长方体的表面积为："+shape());
        System.out.println("长方体的体积为："+volume());
    }
}