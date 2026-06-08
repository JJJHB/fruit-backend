package dao;

import pojo.UsersEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // 查询所有用户
    public List<UsersEntity> getAllUsers() {
        List<UsersEntity> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM users";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                UsersEntity user = new UsersEntity();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setImage(rs.getString("image"));
                user.setRole(rs.getString("role"));
                user.setAddtime(rs.getTimestamp("addtime"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, stmt, conn);
        }

        return list;
    }
}