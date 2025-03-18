package daily;

import java.util.Scanner;

public class test3 {
    public static void main(String[] args) {

        System.out.print("请输入一小数：");
        double r = new Scanner(System.in).nextDouble();
        double area = 3.14 * r * r;
        System.out.println("圆面积为" + area);
        System.out.println(2 * 3.14 * r);
        String str1 = new Scanner(System.in).next();
        float num = Float.parseFloat(str1);
        System.out.println(str1);
    }
}
