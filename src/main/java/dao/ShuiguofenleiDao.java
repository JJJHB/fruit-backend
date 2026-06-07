package dao;

import pojo.ShuiguofenleiEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShuiguofenleiDao {

    // 查询所有水果分类
    public List<ShuiguofenleiEntity> getAllFenlei() {
        List<ShuiguofenleiEntity> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, description FROM shuiguofenlei")) {

            while (rs.next()) {
                ShuiguofenleiEntity obj = new ShuiguofenleiEntity();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setDescription(rs.getString("description"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 新增：获取数据库中所有有效的分类ID集合
    public Set<Integer> getAllCategoryIds() {
        Set<Integer> ids = new HashSet<>();
        String sql = "SELECT id FROM shuiguofenlei";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}