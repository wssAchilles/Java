package daily;
//char类型可以存放一个中文汉字，数字，字母，需加单引号
//直接存数字时，参照asc码表
public class test1 {
    public static void main(String[] args) {
//        byte max = Byte.MAX_VALUE;
//        byte min = Byte.MIN_VALUE;
//        System.out.println(max);
//        System.out.println(min);
//        short max2= Short.MAX_VALUE;
//        System.out.println(max2);
//        short min2= Short.MIN_VALUE;
//        System.out.println(min2);
//        float max3=Float.MAX_VALUE;
//        System.out.println(max3);
//        float min3=Float.MIN_VALUE;
//        System.out.println(min3);
        char c=Character.MAX_VALUE;//65535
        char b=Character.MIN_VALUE;//0
        System.out.println(c);
        System.out.println(b);
        char c2='G';
        System.out.println(c2);
        String name="xzq";
        int age=18;
        System.out.println(name+age);
    }
}

