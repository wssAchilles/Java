package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
练习1，往administer表中插入一条用户信息
练习2，查询administer表中所有的用户信息，并输出到控制台
*/
public class TestPreparedStatement {
    public static void insert(){
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=Config.getConn();
            Scanner sc=new Scanner(System.in);
            System.out.print("请输入插入用户名：");
            String username = sc.nextLine();
            System.out.print("请输入插入密码：");
            String password = sc.nextLine();
            String sql = "insert into administer(username,password) values(?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            // 执行SQL插入操作
            int result = ps.executeUpdate();
            if(result > 0) {
                System.out.println("插入成功！");
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Config.close(conn,ps,null);
        }
    }

    public static void findAll(){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=Config.getConn();
            String sql = "select * from administer";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("用户名："+username+"\t密码："+password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Config.close(conn,ps,rs);
        }
    }

    public static void main(String[] args) {
        //insert();
        findAll();
    }
}
