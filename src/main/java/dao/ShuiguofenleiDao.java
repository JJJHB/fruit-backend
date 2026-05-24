package dao;

import pojo.ShuiguofenleiEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShuiguofenleiDao {

    // 查询所有水果分类
    public List<ShuiguofenleiEntity> getAllFenlei() {
        List<ShuiguofenleiEntity> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM shuiguofenlei";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ShuiguofenleiEntity obj = new ShuiguofenleiEntity();
                obj.setId(rs.getLong("id"));
                obj.setAddtime(rs.getTimestamp("addtime"));
                obj.setShuiguofenlei(rs.getString("shuiguofenlei"));

                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, stmt, conn);
        }

        return list;
    }
}