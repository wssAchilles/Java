package homework;
import java.util.Scanner;
public class _2001 {
    public static void main(String[] args) {
        int []a=new int[10];
        Scanner input=new Scanner(System.in);
        for(int i=0;i<10;i++)
        {
            a[i]=input.nextInt();
        }
        for(int j=0;j<10;j++)
        {
            for(int i=0;i<9-j;i++)
            {
                if(a[i]>a[i+1])
                {
                    int t=a[i];
                    a[i]=a[i+1];
                    a[i+1]=t;
                }
            }
        }
        for(int i=0;i<10;i++)
        {
            System.out.print(a[i]+",");
        }
    }
}
