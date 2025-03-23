package daily;
//3.19.45
public class 面向对象 {
    public static void main(String[] args) {
        Phone []p=new Phone[2];
        p[0]=new Phone("白色","5.5英寸","华为",19990);
        p[1]=new Phone("黑色","6.0英寸","小米",2999);
        for(Phone phone:p){
            phone.show();
        }
    }
}
class Phone{
    String color;
    String size;
    String brand;
    int price;
    public Phone(String color,String size,String brand,int price){
        this.color=color;
        this.size=size;
        this.brand=brand;
        this.price=price;
    }
    public  void show(){
        System.out.println("颜色："+color+" 尺寸："+size+" 品牌："+brand+" 价格："+price);
    }
    public void call(){
        System.out.println("打电话");
    }
    public void sendmessage(){
        System.out.println("发短信");
    }
    public void playgame(){
        System.out.println("玩游戏");
    }
}