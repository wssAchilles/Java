package database;

import java.sql.*;

public class Config {
    public static Connection getConn() throws SQLException {
        //1.注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2.返回连接
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student?characterEncoding=utf-8",
                "root",
                "758205Blns");
    }

    public static void close(Connection conn, Statement stat, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                rs=null;
            }
        }
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                stat=null;
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                conn=null;
            }
        }
    }
}
