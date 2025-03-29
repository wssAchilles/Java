package daily;
class cylinder {
    private double radius;
    private double height;
    private double pi=3.14;
    public void set(double radius,double height) {
        this.radius = radius;
        this.height = height;
    }
    public cylinder(double radius,double height) {
        this.radius = radius;
        this.height = height;
    }
    public double getVolume() {
        return Math.PI * radius * radius * height;
    }
    public double getSurfaceArea() {
        return 2 * Math.PI * radius * radius + 2 * Math.PI * radius * height;
    }
}
public class class_practice {
    public static void main(String[] args) {
        cylinder v1,v2;
        v1=new cylinder(2,3);
        System.out.println("圆柱体1的体积为："+v1.getVolume());
        v2=v1;
        v2.set(3,4);
        System.out.println("圆柱体2的体积为："+v1.getVolume());
    }
}
