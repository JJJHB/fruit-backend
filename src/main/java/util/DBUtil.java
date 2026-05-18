package util;
//工具包一般用于放一些通用工具类
import java.sql.Connection;
//数据库连接对象
import java.sql.DriverManager;
//用来获取连接
import java.sql.ResultSet;
//查询结果集（select返回）
import java.sql.SQLException;
//数据库异常
import java.sql.Statement;
//执行SQL语句

public class DBUtil {

	static String user="root";
	static String password="123456";
	static String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
	
	
//	建立数据库连接并返回一个 Connection 对象。
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			加载 MySQL 驱动旧版本驱动（MySQL 5）新版（MySQL 8）
//			是 加载 MySQL JDBC 驱动 的经典写法，它告诉 JVM 去加载 MySQL 的驱动类，
//			以便之后能通过 DriverManager.getConnection(...) 建立数据库连接。
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			 conn=DriverManager.getConnection(url, user, password);
//			 获取数据库连接，如果这里失败：这一句不会执行成功
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeJDBC(ResultSet rs,Statement stmt,Connection conn){
//		释放数据库游标资源
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		Statement 是执行 SQL 的对象 不关会占用资源
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		真正断开数据库
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
