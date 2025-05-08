package homework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class _5002 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                display();
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);
    }
    public static void display() {
        Date now=new Date();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat format24Hour = new SimpleDateFormat("HH:mm:ss");
        System.out.println("24小时制时间: " + format24Hour.format(now));

        SimpleDateFormat formatFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("完整日期时间: " + formatFull.format(now));

        SimpleDateFormat formatChinese = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("中文格式: " + formatChinese.format(now));
    }
}
