package daily;

import java.util.Scanner;

import static java.nio.file.Files.move;

public class Hanoi {
    public static void hanoi(int n, char A, char B, char C) {
        if (n == 0) {
            move(A, B);
        } else {
            hanoi(n - 1, A, C, B);
            move(A, B);
            hanoi(n - 1, C, A, B);
        }
    }

    private static void move(char x, char y) {
        System.out.println("▓ 移动盘子" +x+ "到" +y );
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please input the number:");
        int number=input.nextInt();
        System.out.println("The process of moving plates:5");
        hanoi(number,'A','B','C');
    }
}
