package homework;

//编写程序实现数字转换为字符串，比较结果并分析
public class _2002 {
    public static void main(String[] args) {
        String a = "1" + 2 + 3 + 4;
        String b = 1 + 2 + 3 + "4";
        String c = "1" + (2 + 3 + 4);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        //遇到+运算时，如果左侧是字符串，后续的值都会变成字符串并拼接。
        //如果+号两侧都是数值，则先进行数值运算，直到遇到字符串，之后的所有操作都会变成字符串拼接。
    }
}
