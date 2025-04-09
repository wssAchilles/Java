package homework;
class student {
    private String sclass;
    private String sname;
    private int sbirthday;
    public student() {}
    public student(String sclass, String sname, int sbirthday) {
        this.sclass = sclass;
        this.sname = sname;
        this.sbirthday = sbirthday;
    }
    public String getsclass() {
        return sclass;
    }
    public String getsname() {
        return sname;
    }
    public int getsbirthday() {
        return sbirthday;
    }
    public void print() {
        System.out.print("姓名:" + sname + " 出生日:" + sbirthday + " 班级：" + sclass);
    }
}
class college extends student{
    private int age;
    public college(String sclass, String sname, int sbirthday) {
        super(sclass, sname, sbirthday);
    }
    public void setAge(int age) {
        if (age >= 17 && age <= 30) {
            this.age = age;
        } else {
            System.out.println("年龄必须在17到30岁之间！");
            System.exit(0);
        }
    }
    public void print() {
        super.print();
        System.out.println(" 年龄: " + age);
    }
}
public class _4003 {
    public static void main(String[] args) {
        college student = new college("23软一", "李四", 2004);
        student.setAge(25);
        student.print();
    }
}
