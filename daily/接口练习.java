package daily;

public class 接口练习 {
    public static void main(String[] args) {
        inter a = new interImpl();
        a.save();
        a.delete();
    }
}
//接口中都是抽象方法
interface inter{
    abstract public void save();
    abstract public void delete();
}
class interImpl implements inter{
    public void save(){
        System.out.println("保存");
    }
    public void delete(){
        System.out.println("删除");
    }
}