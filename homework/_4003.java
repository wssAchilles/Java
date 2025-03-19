package homework;

import java.util.Scanner;

public class _4003 {
    class student{
        private String sclass;
        String sname;
        int sbirthday;
        public student(String sclass, String sname, int sbirthday) {
            this.sclass = sclass;
            this.sname = sname;
            this.sbirthday = sbirthday;
        }
        public  void show(){
            System.out.println("姓名:"+sname+" 出生日:"+sbirthday+" 班级："+sclass);
        }
    }
    public static void main(String[] args) {
        _4003 outer = new _4003();
        student[] s = new student[2];
        // 直接给student数组赋值
        s[0] = outer.new student("计算机1班", "张三", 20000101);
        s[1] = outer.new student("计算机2班", "李四", 20000202);
        // 显示学生信息
        for(int i=0;i<s.length;i++) {
            s[i].show();
        }
    }
}
