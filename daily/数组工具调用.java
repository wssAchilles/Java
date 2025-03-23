package daily;
import java.util.Arrays;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;

public class 数组工具调用 {
    public static void main(String[] args) {
        int []a={34,57,12,89,64};
        //排序
        sort(a);
        //以前的方法
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
        //现在的方法
        System.out.println(Arrays.toString(a));
        for(int i:a){
            System.out.println(i);
        }
        int []b=copyOf(a,10);
        System.out.println(Arrays.toString(b));
    }
}
