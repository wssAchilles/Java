package database;
//03PM
import java.sql.*;
//import java.sql.Statement;

//03pm
public class printdata {
    public static void testFindAll() throws Exception {
        //1.注册数据库驱动
        //Class.forName("com.mysql.cj.jdbc.Driver");

        //2.获取数据库连接
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student?characterEncoding=utf-8",
                "root",
                "758205Blns");

        //3.获取传输器
        Statement stat = conn.createStatement();

        //4.执行sql语句
        String sql = "select * from administer";
        ResultSet rs = stat.executeQuery(sql);

        //5.处理结果集(打印到控制台)
        while (rs.next()) {
            {
                //获取数据
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("用户名：" + username + "\t密码：" + password);
            }
        }

        //6.关闭资源(原则：越晚获取越先关闭)
        rs.close();
        stat.close();
        conn.close();
    }

    public static void main(String[] args) {
        try {
            testFindAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
