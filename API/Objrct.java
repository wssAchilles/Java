package API;

public class Objrct {
    public static void main(String[] args) {
        char []cs=new char[]{'a','b','c'};
        String s=new String(cs);//abc
        String s2="abc";
        System.out.println(s.charAt(1));//获取指定下标对应的字符
        System.out.println(s.concat("opq"));//在原有数据末尾处拼接指定字符串
        System.out.println(s.contains("bc"));//判断是否包含指定字符串
        System.out.println(s.endsWith("c"));//判断是否以指定字符串结尾
        System.out.println(s.equals("abc"));//判断是否相等
        System.out.println(s.hashCode());//获取哈希值
        s="absdfdasdf";//重新赋值
        System.out.println(s.lastIndexOf("s"));//获取指定字符在字符串中最后一次出现的下标
        System.out.println(s.indexOf("s"));//获取指定字符在字符串中第一次出现的下标
        System.out.println(s.length());//获取字符串长度
        System.out.println(s.replace('b','1'));//替换指定字符
        System.out.println(s.startsWith("ab"));//判断是否以指定字符串开头
        System.out.println(s.substring(1));//获取指定下标到末尾的字符串
        System.out.println(s.substring(1,4));//获取指定下标到指定前一个下标的字符串
        char []cs2=s.toCharArray();//将字符串转换为字符数组
        for(char c:cs2){
            System.out.print(c+" ");
        }
        System.out.println();
        System.out.println(s.toLowerCase());//将字符串转换为小写字母
        System.out.println(s.toUpperCase());//将字符串转换为大写字母
        System.out.println(s.trim());//去除字符串 首 尾 空格
        String num=String.valueOf(123);//将数字转换为字符串
        System.out.println(num);
        s="abc.def.ghi.jkl";
        String []strs=s.split("\\.");//分割字符串
        for(String str:strs){
            System.out.println(str+" ");
        }
        String xzq="abcdefhijklmnopqrstuvwxyz";
        String res="";
        long start=System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            res+=xzq;
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}
