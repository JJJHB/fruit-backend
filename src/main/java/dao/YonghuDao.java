package dao;

import pojo.YonghuEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YonghuDao {

    // 查询所有普通用户
    public List<YonghuEntity> getAllUsers() {
        List<YonghuEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM yonghu";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                YonghuEntity user = new YonghuEntity();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setSex(rs.getString("sex"));
                user.setPhone(rs.getString("phone"));
                user.setPicture(rs.getString("picture"));
                user.setMoney(rs.getBigDecimal("money"));

                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 根据ID查询用户
    public YonghuEntity getUserById(Long id) {
        String sql = "SELECT * FROM yonghu WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new YonghuEntity(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("sex"),
                            rs.getString("phone"),
                            rs.getString("picture"),
                            rs.getBigDecimal("money")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 检查账号是否存在
    public boolean exists(String username) {
        String sql = "SELECT COUNT(*) FROM yonghu WHERE username=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 注册新用户
    public boolean register(YonghuEntity user) {

        String sql =
                "insert into yonghu(username,password,sex,phone,picture,money) " +
                "values(?,?,?,?,?,?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPicture());
            ps.setBigDecimal(6, user.getMoney());

            int rows = ps.executeUpdate();

            System.out.println("影响行数=" + rows);

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 修改用户信息
    public boolean updateUser(YonghuEntity user) {
        String sql = "UPDATE yonghu SET username=?,password=?,sex=?,phone=?,picture=?,money=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPicture());
            ps.setBigDecimal(6, user.getMoney());
            ps.setLong(7, user.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 删除用户
    public boolean deleteUser(Long id) {
        String sql = "DELETE FROM yonghu WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}