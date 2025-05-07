package 线程;

import API.Object;

public class Task {
    public static void main(String[] args) throws Exception {
        //在这里创建线程， 开启线程
        API.Object lock = new API.Object();
        Thread t1 = new MyThread("AA", lock, 0);
        Thread t2 = new MyThread("BB", lock, 1);
        Thread t3 = new MyThread("CC", lock, 2);
        t1.start();
        t2.start();
        t3.start();
    }
}
class MyThread extends Thread {
    private static int currentOrder = 0;
    private static final int TOTAL_THREADS = 3;
    private final String threadName;
    private final API.Object lock;
    private final int threadOrder;
    public MyThread(String threadName, Object lock, int threadOrder) {
        this.threadName = threadName;
        this.lock = lock;
        this.threadOrder = threadOrder;
    }
    public void run() {
        int count = 5;
        while(count > 0){
            synchronized (lock) {
                while (currentOrder != threadOrder) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Java Thread" + threadName);
                count--;
                currentOrder = (currentOrder + 1) % TOTAL_THREADS;
                lock.notifyAll();
            }
        }
    }
}