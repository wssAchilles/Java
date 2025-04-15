package daily;

public class equal {
    public static void main(String[] args) {
        //==比较的是地址值
        //equals比较的是内容
        //String是引用类型，==比较的是地址值
        //String是不可变的
        String a = new String("abc");
        String b = new String("abc");
        String c = "abc";
        String d = "abc";
        System.out.println(a == b); // false
        System.out.println(d == c); // true
        System.out.println(a.equals(b)); // true
        Phone p1 = new Phone("黑色","6.0英寸","小米",2998);
        Phone p2 = new Phone("黑色","6.0英寸","小米",2998);
        System.out.println(p1.equals(p2)); // false
    }
}
//重写equals方法
//public boolean equals(Object obj) {
//    if (this == obj) {
//        return true;
//    }
//    if (obj == null || getClass() != obj.getClass()) {
//        return false;
//    }
//    Phone phone = (Phone) obj;
//    return color.equals(phone.color) && size.equals(phone.size) && brand.equals(phone.brand) && price == phone.price;
//}
