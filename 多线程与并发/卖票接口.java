package 多线程与并发;

public class 卖票接口 {
    public static void main(String[] args) {
        Mytick target=new Mytick();

        Thread thread1=new Thread(target,"窗口1");
        Thread thread2=new Thread(target,"窗口2");
        Thread thread3=new Thread(target,"窗口3");
        Thread thread4=new Thread(target,"窗口4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
class Mytick implements Runnable{
    //票数
    int ticks=100;
    Object obj=new Object(); //锁对象
    //重写run方法
    @Override
    public void run(){
        while(true){
            //锁的位置：合理，建议从共享资源开始位置，一直到使用刚结束都锁起来
            //锁的对象：锁代码块的锁对象可以是任意对象，只要是同一个对象就可以
            synchronized (this){
                if (ticks > 0) {
                    try {//检查多线程是否存在安全隐患，让程序sleep 10ms
                        //问题1：同一张票卖给了多个人
                        //问题2：票数不够了，线程还在运行
                        //问题3：卖出了0号票
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "正在卖" + ticks-- + "号票");
                } else {
                    break;
                }
            }
        }
    }
}