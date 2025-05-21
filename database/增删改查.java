package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class 增删改查 {
    //1.增
    public static void Insert(){
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try {
            //1.注册数据库驱动 //2.获取数据库连接
            conn= Config.getConn();

            //3.获取传输器
            stat = conn.createStatement();

            //4.执行sql语句
            String sql = "insert into administer(username,password) values('admin','88888888')";
            int count = stat.executeUpdate(sql);
            System.out.println("插入了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Config.close(conn,stat,rs);
        }
    }

    //2.删
    public static void Delete(){
        Connection conn=null;
        Statement stat=null;
        //ResultSet rs=null;
        try {
            //1.注册数据库驱动 //2.获取数据库连接
            conn= Config.getConn();

            //3.获取传输器
            stat = conn.createStatement();

            //4.执行sql语句
            String sql = "delete from administer where username='admin'";
            int count = stat.executeUpdate(sql);
            System.out.println("删除了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Config.close(conn,stat,null);
        }
    }

    //3.改
    public static void Update(){
        Connection conn=null;
        Statement stat=null;
        //ResultSet rs=null;
        try {
            //1.注册数据库驱动 //2.获取数据库连接
            conn= Config.getConn();

            //3.获取传输器
            stat = conn.createStatement();

            //4.执行sql语句
            String sql = "update administer set password='123456' where username='admin'";
            int count = stat.executeUpdate(sql);
            System.out.println("修改了" + count + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Config.close(conn,stat,null);
        }
    }

    //4.查
    public static void Select(){
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try {
            //1.注册数据库驱动 //2.获取数据库连接
            conn= Config.getConn();

            //3.获取传输器
            stat = conn.createStatement();

            //4.执行sql语句
            String sql = "select * from administer";
            rs = stat.executeQuery(sql);

            //5.处理结果集(打印到控制台)
            while (rs.next()) {
                //获取数据
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("用户名：" + username + "\t密码：" + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Config.close(conn,stat,rs);
        }
    }
    public static void main(String[] args) {
        //Insert();
        Delete();
        //Update();
        Select();
    }
}
