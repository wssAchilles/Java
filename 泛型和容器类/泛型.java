package 泛型和容器类;
//14pm--16am
import java.util.ArrayList;
import java.util.List;

public class 泛型 {
    public static void main(String[] args) {
        //1.创建集合对象
        List<String> list=new ArrayList();//必须指定泛型
        //泛型的作用：可以检查数据的类型，如果类型对才可以向结合中添加元素
        //可以把报错的时机提前，如果有错编译不通过

        //2.添加元素
        list.add("许子祺");
        //list.add(1);编译不通过
        list.add("wss");
        System.out.println(list);
    }
}
