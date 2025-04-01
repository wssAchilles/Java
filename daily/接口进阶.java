package daily;

public class 接口进阶 {
    public static void main(String[] args) {
        xzq a=new xzq();
        a.save();a.get();a.add();a.delete();a.update();
    }
}
interface Mapper1{
    void save();
    void get();
}
interface Mapper2{
    void add();
    void update();
}
interface Mapper3 extends Mapper1,Mapper2{
    void delete();
}
class xzq extends animal implements Mapper3{
    public void save(){
        System.out.println("保存");
    }
    public void get(){
        System.out.println("获取");
    }
    public void add(){
        System.out.println("添加");
    }
    public void update(){
        System.out.println("更新");
    }
    public void delete(){
        System.out.println("删除");
    }
}