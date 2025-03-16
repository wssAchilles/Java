package ex1_23030327;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//打印菱形*
public class ex1_2 {
    public static void print(int n,int count,char star){
        for (int i = 0; i < (n - count) / 2; i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < count; i++) {
            System.out.print(star);
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        char star='\u002A';
        BufferedReader buf;
        buf=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入奇数：");
        String str1= buf.readLine();
        int n=Integer.parseInt(str1);
        for (int i = 1; i <= n; i += 2) {
            print(n, i, star);
        }
        for (int i = n- 2; i >= 1; i -= 2) {
            print(n, i, star);
        }
    }
}
