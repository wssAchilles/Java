package homework;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;
//编写程序，输入一个日期，输出这一天是星期几。
public class _3003 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入一个日期：（年月日之间短横线连接）");
        String Date = input.next();
        LocalDate date = LocalDate.parse(Date);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("这一天是星期" + dayOfWeek.getValue());
    }
}
