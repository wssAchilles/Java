package ex1_23030327;
import java.util.Scanner;
//编写程序求 n!
public class ex1_1 {
    public static int jc(int n) {
        int m=0;
        if(n==1){
            m=1;
        }else{
            m=jc(n-1)*n;
        }
        return  m;
    }
    public static void main(String[] args) {
        System.out.print("请输入一个数字：");
        int n=new Scanner(System.in).nextInt();
        System.out.println("该数字的阶乘为："+jc(n));
    }
}
