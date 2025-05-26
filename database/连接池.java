package database;
//04PM1.53.19
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class 连接池 {
    // 创建连接池对象（单例模式）
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    // 静态代码块，配置连接池参数
    static {
        try {
            // 设置数据库驱动
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            // 设置数据库连接URL
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/student?characterEncoding=utf-8");
            // 设置数据库用户名
            cpds.setUser("root");
            // 设置数据库密码
            cpds.setPassword("758205Blns");

            // 设置连接池参数
            cpds.setInitialPoolSize(5);  // 初始连接数
            cpds.setMaxPoolSize(10);     // 最大连接数
            cpds.setMinPoolSize(2);      // 最小连接数
            cpds.setAcquireIncrement(2); // 当连接池中连接用完时,一次性创建的连接数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接的方法
    public static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 测试插入方法
    public static void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 从连接池获取连接
            conn = getConnection();

            // 使用时间戳生成唯一用户名，避免主键冲突
            String uniqueUsername = "admin" + System.currentTimeMillis();

            // 获取传输器
            String sql = "insert into administer(username,password) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, uniqueUsername);
            ps.setString(2, "88888888");

            // 执行SQL
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("插入成功！用户名: " + uniqueUsername);
            } else {
                System.out.println("插入失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源，但不是真正关闭，而是归还连接到连接池
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 测试连接池的main方法
    public static void main(String[] args) {
        testInsert();
    }
}
