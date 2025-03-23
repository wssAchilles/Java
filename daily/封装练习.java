package daily;
//45.48
public class 封装练习 {
    public static void main(String[] args) {
        Student s = new Student();
        s.gaming();
        s.set("hangman", 18, 100);
//        s.getName();
//        s.getAge();
//        s.getScore();
        s.show();
    }
}
class Student {
    private String name;
    private int age;
    private double score;
    public void set(String name,int age,double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }
//    public void getName() {
//        System.out.println(this.name);
//    }
//    public void getAge() {
//        System.out.println(this.age);
//    }
//    public void getScore() {
//        System.out.println(this.score);
//    }
    public void show() {
        System.out.println("姓名:" + name + " 年龄:" + age + " 成绩：" + score);
    }
    private void coding() {
        System.out.println("coding");
    }
    public void gaming() {
        coding();
        System.out.println("gaming");
    }

}