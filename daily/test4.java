package daily;

import java.util.Scanner;

public class   test4 {
    public static void main(String[] args) {
        int a=new Scanner(System.in).nextInt();
        int b=new Scanner(System.in).nextInt();
        System.out.println("交换前，a="+a+"，b="+b);
        int c=a;
        a=b;
        b=c;
        String i="zdf";
        System.out.println(i);
        System.out.println("交换后，a="+a+"，b="+b);
        long x= (long) 10000000000L;
        float y=9.9F;
        //类型转换，小转大
        long m=10;
        double n=m;
        //大转小
        float t=5;
        int s = (int)t;
        System.out.println(0/0.0);
    }
}
