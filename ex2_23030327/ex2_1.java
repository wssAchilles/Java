package ex2_23030327;
import java.util.Scanner;
//定义一个复数类，包含实部和虚部，实现复数的加减乘除运算
//2303027许子祺
class Complex{
    private  double real;
    private  double imag;
    public  Complex(double real,double imag){
        this.real=real;
        this.imag=imag;
    }
    public Complex(){}
    public void show(){
        if(real>0&&imag>0){System.out.println(real+"+"+imag+"i");}
        else if(real>0&&imag<0){System.out.println(real+""+imag+"i");}
        else if(real<0&&imag>0){System.out.println(real+"+"+imag+"i");}
        else if(real<0&&imag<0){System.out.println(real+""+imag+"i");}
        else if(real==0&&imag>0){System.out.println(imag+"i");}
        else if(real==0&&imag<0){System.out.println(imag+"i");}
        else if(real>0&&imag==0){System.out.println(real);}
        else if(real<0&&imag==0){System.out.println(real);}
        else if(real==0&&imag==0){System.out.println(0);}
    }
    public Complex add(Complex other){
        double newreal=this.real+other.real;
        double newimag=this.imag+ other.imag;
        return new Complex(newreal,newimag);
    }
    public Complex sub(Complex other){
        double newreal=this.real- other.real;
        double newimag=this.imag-other.imag;
        return new Complex(newreal,newimag);
    }
    public Complex mul(Complex other){
        double newreal=this.real*other.real-this.imag*other.imag;
        double newimag=this.imag*other.imag+this.imag*other.imag;
        return new Complex(newreal,newimag);
    }
    public Complex div(Complex other){
        double newreal=(this.real*other.real+this.imag*other.imag)/(other.real*other.real+other.imag*other.imag);
        double newimag=(this.imag*other.real-this.real*other.imag)/(other.real*other.real+other.imag*other.imag);
        return new Complex(newreal,newimag);
    }
}
public class ex2_1 {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        System.out.print("请输入第一个虚数：");
        Complex c1=new Complex(input.nextDouble(),input.nextDouble());
        System.out.print("请输入第二个虚数：");
        Complex c2=new Complex(input.nextDouble(), input.nextDouble());
        Complex add=c1.add(c2);
        Complex sub=c1.sub(c2);
        Complex mul=c1.mul(c2);
        Complex div=c1.div(c2);
        System.out.println("相加结果为：");
        add.show();
        System.out.println("相减结果为：");
        sub.show();
        System.out.println("相乘结果为：");
        mul.show();
        System.out.println("相除结果为：");
        div.show();
    }
}
