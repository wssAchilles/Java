package homework;

public class _3004 {
    public static void main(String[] args) {
        boolean[] light = new boolean[101];
        for (int i = 1; i <= 100; i++) {
            for (int j = i; j <= 100; j += i) {
                light[j] = !light[j];
            }
        }
        System.out.println("开着的灯泡编号：");
        for (int i = 1; i <= 100; i++) {
            if (light[i]) {
                System.out.print(i + " ");
            }
        }
    }
}
