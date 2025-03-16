package homework;
import java.time.Year;
//判断闰年（两种）
public class _1004 {
//    public static boolean isLeapYear(int year) {
//        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
//    }
//    public static void main(String[] args) {
//       for(int i=1950;i<=2050;i++)
//       {
//           if(isLeapYear(i)){
//               System.out.print(i+"是闰年 ");
//           }
//       }
//    }
    public static void main(String[] args) {
        int count=0;
        for (int i = 1950; i <= 2050; i++) {
            if (Year.isLeap(i)) {
                System.out.print(i + "是闰年  ");  // 输出闰年
                count++;
                if (count == 5) {


                    System.out.println();
                    count = 0;
                }
            }

        }
    }
}