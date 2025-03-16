package daily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test6 {
    public static void main(String[] args) throws IOException {
        BufferedReader buf;
        String str1,str2;
        buf=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入字符串:");
        str1=buf.readLine();
        System.out.println("您输入的字符串是:"+str1);
        System.out.print("请输入一个实数");
        str2=buf.readLine();
        float num=Float.parseFloat(str2);
        System.out.println(num+"乘10后取整数为："+(int)(10*num));
    }
}
