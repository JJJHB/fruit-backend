package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    static String user = "root";
    static String password = "123456";
    // MySQL5 兼容 URL：关闭 SSL + UTF-8 编码
    static String url = "jdbc:mysql://localhost:3306/fruitshop?useUnicode=true&characterEncoding=utf8&useSSL=false";

    // 建立数据库连接并返回 Connection 对象
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // MySQL 5 驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动类未找到，请检查 jar 包是否存在！");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功: " + conn);
        } catch (SQLException e) {
            System.err.println("获取数据库连接失败，请检查 URL/用户名/密码/数据库状态！");
            e.printStackTrace();
        }
        return conn;
    }

    // 释放资源
    public static void closeJDBC(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
