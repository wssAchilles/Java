package daily;

public class 接口练习 {
    public static void main(String[] args) {
        interImpl a = new interImpl();
        a.save();
        a.delete();
        a.update(); //接口中的方法不能被重写
        //a.number=20; //接口中的变量是常量，不能修改
    }
}
//接口中都是抽象方法
interface inter{
    //接口里没有构造方法，没有静态方法
    //public inter();
    //接口里面有成员变量，默认是public static final
    int number = 10;
    void save();
    void delete();
}
class interImpl implements inter{
    //重写也得是public
    public void save(){
        System.out.println("保存");
    }
    public void delete(){
        System.out.println("删除");
    }
    public void update(){
        System.out.println("更新");
    }
}