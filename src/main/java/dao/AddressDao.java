package dao;

import pojo.AddressEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {

    // 查询用户地址列表
    public List<AddressEntity> getAddressByUser(long userId) {
        List<AddressEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM address WHERE user_id=? ORDER BY is_default DESC, id ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AddressEntity a = new AddressEntity();
                a.setId(rs.getInt("id"));
                a.setUserId(rs.getLong("user_id"));
                a.setRecipient(rs.getString("recipient"));
                a.setPhone(rs.getString("phone"));
                a.setDetail(rs.getString("detail"));
                a.setIsDefault(rs.getInt("is_default"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 添加地址
    public boolean add(AddressEntity a) {
        String sql = "INSERT INTO address(user_id, recipient, phone, detail, is_default) VALUES(?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, a.getUserId());
            ps.setString(2, a.getRecipient());
            ps.setString(3, a.getPhone());
            ps.setString(4, a.getDetail());
            ps.setInt(5, a.getIsDefault());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除地址
    public boolean delete(int id) {
        String sql = "DELETE FROM address WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 修改地址
    public boolean update(AddressEntity a) {
        String sql = "UPDATE address SET recipient=?, phone=?, detail=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getRecipient());
            ps.setString(2, a.getPhone());
            ps.setString(3, a.getDetail());
            ps.setInt(4, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 设为默认
    public boolean setDefault(int id, long userId) {
        String sql1 = "UPDATE address SET is_default=0 WHERE user_id=?";
        String sql2 = "UPDATE address SET is_default=1 WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1);
             PreparedStatement ps2 = conn.prepareStatement(sql2)) {

            conn.setAutoCommit(false);

            ps1.setLong(1, userId);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}