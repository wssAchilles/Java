package homework;

import java.util.Scanner;

//编写程序实现通过键盘输入若干个表示整数的字符串，并输出其中的最小值。
//如果输入的字符串不能转换成整数，用相应的异常处理方法解决。
public class _5001 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入字符串的个数:");
        int length=input.nextInt();
        String []arr=new String[length];
        for(int i=0;i<length;i++){
            arr[i]=input.next();
        }
        int min = Integer.MAX_VALUE;
        int cnt = 0;
        for (String s : arr) {
            try {
                int num = Integer.parseInt(s);
                if (num < min) {
                    min = num;
                }
            } catch (NumberFormatException e) {
                System.out.println("输入的字符串不能转换成整数: " + s);
                cnt++;
            }
        }
        if(cnt==length){
            System.out.println("所有输入的字符串都不能转换成整数");
        }else{
            System.out.println("最小值是: " + min);
        }
    }
}
