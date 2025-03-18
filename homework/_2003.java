package homework;

public class _2003 {
    public static void main(String[] args) {
        String a="abc";
        String b=new String("ABC");
        String d="abc";
        String e=new String("abc");
        int c=a.compareTo(b);
        System.out.println("compareTo:"+c);
        c=a.compareToIgnoreCase(b);
        System.out.println("compareToTgnoreCase:"+c);
        boolean boo=a.equals(b);
        System.out.println("equals:"+boo);
        boo=a.equalsIgnoreCase(b);
        System.out.println("equalsIgnoreCase:"+boo);
        boo=(a==b);
        System.out.println("a==b:"+boo);
        boo=(a==d);
        System.out.println("a==d:"+boo);
        boo=(a==e);
        System.out.println("a==e:"+boo);
//      compareTo() 按 Unicode 码值 逐字符比较，"abc" 比 "ABC" 大 32。
//      compareToIgnoreCase() 忽略大小写，所以 "abc" 和 "ABC" 视为相等，返回 0。
//      equals() 区分大小写，所以 "abc" 和 "ABC" 不相等。
//      equalsIgnoreCase() 忽略大小写，所以 "abc" 和 "ABC" 相等。
//      ==比较引用地址,直接赋值的字符串字面量 存放在字符串常量池，相同值指向相同地址。new String("abc") 会创建新的对象，== 比较时会返回 false。
    }
}
