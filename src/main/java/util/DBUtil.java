package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    static String user = "root";
    static String password = "root";
    // 适配MySQL8+加时区，5.7也能兼容
    static String url = "jdbc:mysql://localhost:3306/fruitshop?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // mysql8驱动用com.mysql.cj.jdbc.Driver，5.x可用旧驱动，高版本jar自动兼容
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动缺失！");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
        return conn;
    }

    // 重载方法：兼容PreparedStatement(DAO全部用这个)
    public static void closeJDBC(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (pstmt != null) {
            try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // 保留原来Statement版本备用
    public static void closeJDBC(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}