package daily;

import java.util.Random;
import java.util.Scanner;

import static java.util.Arrays.sort;

public class 数组练习 {
    public static void main(String[] args) {
        //静态舒适化数组
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] arr2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        //动态初始化数组
        int arr1[] = new int[10];
        //创建数组
        String []map={"你好！"};
        String []map1=new String[5];
        map1[0]="Hello";
        System.out.println(map1[0].length());
        int a[]=new int[5];
        for(int i=0;i<a.length;i++){
            a[i]=new Random().nextInt(100);
        }
        sort(a);
        for(int i:a){
            System.out.println(i);
        }
    }
}

