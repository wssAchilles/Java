package 多线程与并发;

public class runnable {
    public static void main(String[] args) {
        //4.创建多线程对象
        MyRunnable target=new MyRunnable();

        //5.创建Thread对象，传入Runnable对象
        Thread thread1=new Thread(target);
        thread1.setName("许子祺");
        thread1.start();

        //6.创建第二个线程对象
        MyRunnable target2=new MyRunnable();
        Thread thread2=new Thread(target2);
        thread2.setName("魏铄苏");
        thread2.start();
    }
}

//1.创建多线程类 implement Runnable接口
class MyRunnable implements Runnable{
    //2.重写run方法
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            //3.获取当前线程的ID和名称--Thread.currentThread().getId()和Thread.currentThread().getName()
            System.out.println(Thread.currentThread().getId()+"号线程"+Thread.currentThread().getName()+"正在运行");
        }
    }
}
