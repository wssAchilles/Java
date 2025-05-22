package database;

import java.sql.*;
import java.util.Scanner;

public class 模拟登录 {
    public static void main(String[] args) throws SQLException {
        //1.提示用户输入用户名并接收
        System.out.print("请输入用户名：");
        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        //2.提示用户输入密码并接收
        System.out.print("请输入密码：");
        String password = sc.nextLine();

        //3.根据用户名和密码查询数据库
        login(username, password);
    }

    private static void login(String username, String password) throws SQLException {
        Connection conn=null;
        //Statement stat=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //1.注册数据库驱动 //2.获取数据库连接
            conn= Config.getConn();

            //3.获取传输器,使用PreparedStatement防止SQL注入
            String sql = "select * from administer where username=? and password=?";
            ps= conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs=ps.executeQuery();
            //String sql = "select * from administer where username='" + username + "' and password='" + password + "'";

            //处理结果集(打印到控制台)
            if (rs.next()) {
                //获取数据
                String name = rs.getString("username");
                String pass = rs.getString("password");
                System.out.println("用户名：" + name + "\t密码：" + pass);
                System.out.println("登录成功！");
            } else {
                System.out.println("用户名或密码错误，请重新输入！");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            Config.close(conn,ps,rs);
        }
    }
}
