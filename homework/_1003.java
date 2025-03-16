package homework;
//编写九九乘法表程序，要求每一列对齐；
public class _1003 {
    public static void main(String[] args) {
        for(int i=1;i<=9;i++)
        {
            for(int j=1;j<=i;j++)
            {
                System.out.printf("%d×%d=%2d ", j, i, i * j);
            }
            System.out.println();
        }
    }
}
