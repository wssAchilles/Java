package IO流;

import java.io.File;
import java.io.IOException;

public class 文件常用方法 {
    public static void main(String[] args) throws IOException {
        //参数是文件路径或者文件夹路径
        File f=new File("D:\\Java\\1.txt");
        System.out.println(f.length());
        System.out.println(f.exists());
        System.out.println(f.isFile());
        System.out.println(f.isDirectory());
        System.out.println(f.getName());
        System.out.println(f.getParent());
        System.out.println(f.getAbsolutePath());

        System.out.println(f.createNewFile());
        System.out.println(f.mkdir());
        System.out.println(f.mkdirs());
        System.out.println(f.delete());

        String[] sts=f.list();

        File[] files=f.listFiles();
    }
}
