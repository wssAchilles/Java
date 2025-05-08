package 泛型和容器类;

public class 通用性 {
    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5};
        Double[] b = {1.1, 2.2, 3.3, 4.4, 5.5};
        String[] c = {"xzq", "wss", "lqj", "zj", "xj"};
        print(a);
        print(b);
        print(c);
    }

    public static <E> void print(E[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
