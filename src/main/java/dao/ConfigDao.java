package dao;

import pojo.ConfigEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigDao {

    public List<ConfigEntity> findAll() {
        List<ConfigEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM config ORDER BY sort_order ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
            	ConfigEntity c = new ConfigEntity();
                c.setId(rs.getInt("id"));
                c.setImgUrl(rs.getString("img_url"));
                c.setLinkUrl(rs.getString("link_url"));
                c.setSortOrder(rs.getInt("sort_order"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(ConfigEntity c) {
        String sql = "INSERT INTO config(img_url, link_url, sort_order) VALUES(?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getImgUrl());
            ps.setString(2, c.getLinkUrl());
            ps.setInt(3, c.getSortOrder() == null ? 0 : c.getSortOrder());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(ConfigEntity c) {
        String sql = "UPDATE config SET img_url=?, link_url=?, sort_order=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getImgUrl());
            ps.setString(2, c.getLinkUrl());
            ps.setInt(3, c.getSortOrder() == null ? 0 : c.getSortOrder());
            ps.setInt(4, c.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int id) {
        String sql = "DELETE FROM config WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}