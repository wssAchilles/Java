package 多线程与并发;
//继承Thread，实现多线程
public class 卖票 {
    public static void main(String[] args) {
        Myticks target1=new Myticks();
        Myticks target2=new Myticks();
        Myticks target3=new Myticks();
        Myticks taiget4=new Myticks();

        target1.start();
        target2.start();
        target3.start();
        taiget4.start();
    }
}
class Myticks extends Thread{
    //票数
    static int ticks=100;
    //重写run方法
    @Override
    public void run(){
        while(true){
            if(ticks>0){
                try {//检查多线程是否存在安全隐患，让程序sleep 10ms
                    //问题1：同一张票卖给了多个人
                    //问题2：票数不够了，线程还在运行
                    //问题3：卖出了0号票
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(getName()+"正在卖"+ticks--+"号票");
            }else{
                break;
            }
        }
    }
}