package daily;

import java.util.Scanner;

public class max {
    public static void main(String[] args) {
        int a=new Scanner(System.in).nextInt();
        int b=new Scanner(System.in).nextInt();
        int c=new Scanner(System.in).nextInt();
        int max=a>b?a:b;
        int maxi=max>c?max:c;
        System.out.print(maxi);

    }
}
