package 多线程与并发;
//测试多线程编程
public class thread {
    public static void main(String[] args) {
        MyThread target=new MyThread();
        //target.run(); 可以执行任务，但没有多线程效果，相当于普通方法调用
        target.start(); // 启动线程，调用run方法

        MyThread target2=new MyThread();
        //target2.run();
        target2.start(); // 启动线程，调用run方法
        //多线程执行结果随机性，由于菜谱执行过程不可控，会出现随机结果
    }
}
class MyThread extends Thread{
    @Override
    public void run(){
        for(int i=0;i<10;i++){
            System.out.println(getId()+"线程"+getName()+"正在运行");
        }
    }
    
}