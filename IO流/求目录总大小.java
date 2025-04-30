package IO流;

import java.io.File;

//"C:\Users\xuzq0\Desktop\Obsidian Vault"
public class 求目录总大小 {
    public static void main(String[] args) {
        String path = "C:\\Users\\xuzq0\\Desktop\\Obsidian Vault";
        File file = new File(path);
        System.out.println(getDirectorySize(file));
    }
    public static long getDirectorySize(File directory) {
        long size = 0;
        if (directory.isFile()) {
            return directory.length();
        } else {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    size += getDirectorySize(file);
                }
            }
        }
        return size;
    }
    
}
