package IO流;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class 文件常用方法 {
    public static void main(String[] args) throws IOException {
        //参数是文件路径或者文件夹路径
        File f=new File("D:\\Java\\2.txt");//封装文件路劲
        System.out.println(f.length());
        System.out.println(f.exists());
        System.out.println(f.isFile());
        System.out.println(f.isDirectory());
        System.out.println(f.getName());
        System.out.println(f.getParent());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.createNewFile());

        f=new File("D:\\Java\\death");//封装文件夹路劲
        System.out.println(f.mkdir());

        f=new File("D:\\Java\\wss\\xzq\\partner");//封装多级文件夹路劲
        System.out.println(f.mkdirs());
        //System.out.println(f.delete());//只能删除空文件夹(partner)

        f=new File("D:\\Java");//封装文件夹路劲
        String[] sts=f.list();//获取文件名并存入数组中
        System.out.println(Arrays.toString(sts));

        File[] files=f.listFiles();//获取文件并封装成file对象存入数组中
        System.out.println(Arrays.toString(files));

        Arrays.sort(files);
        for (File file : files) {
            System.out.print(file.getName()+" ");
        }
        System.out.println();
    }
}
