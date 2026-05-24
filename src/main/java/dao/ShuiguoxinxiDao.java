package dao;

import pojo.ShuiguoxinxiEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShuiguoxinxiDao {

    // 查询所有水果信息
    public List<ShuiguoxinxiEntity> getAllShuiguo() {
        List<ShuiguoxinxiEntity> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM shuiguoxinxi";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ShuiguoxinxiEntity obj = new ShuiguoxinxiEntity();
                obj.setId(rs.getLong("id"));
                obj.setAddtime(rs.getTimestamp("addtime"));
                obj.setShuiguomingcheng(rs.getString("shuiguomingcheng"));
                obj.setShuiguofenlei(rs.getString("shuiguofenlei"));
                obj.setChandi(rs.getString("chandi"));
                obj.setGuige(rs.getString("guige"));
                obj.setCaizhairiqi(rs.getTimestamp("caizhairiqi"));
                obj.setBaozhiqi(rs.getString("baozhiqi"));
                obj.setShuiguojieshao(rs.getString("shuiguojieshao"));
                obj.setShuiguotupian(rs.getString("shuiguotupian"));
                obj.setOnelimittimes(rs.getInt("onelimittimes"));
                obj.setAlllimittimes(rs.getInt("alllimittimes"));
                obj.setThumbsupnum(rs.getInt("thumbsupnum"));
                obj.setCrazilynum(rs.getInt("crazilynum"));
                obj.setClicktime(rs.getTimestamp("clicktime"));
                obj.setClicknum(rs.getInt("clicknum"));
                obj.setDiscussnum(rs.getInt("discussnum"));
                obj.setPrice(rs.getDouble("price"));
                obj.setOnshelves(rs.getInt("onshelves"));
                obj.setStoreupnum(rs.getInt("storeupnum"));

                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, stmt, conn);
        }

        return list;
    }

    // ===================== 【新加：条件查询方法】 =====================
    public List<ShuiguoxinxiEntity> queryByCondition(String name, String chandi, String minPrice, String maxPrice) {
        List<ShuiguoxinxiEntity> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 动态拼接 SQL
            StringBuilder sql = new StringBuilder("SELECT * FROM shuiguoxinxi WHERE 1=1 ");

            if (name != null && !name.trim().isEmpty()) {
                sql.append("AND shuiguomingcheng LIKE ? ");
            }
            if (chandi != null && !chandi.trim().isEmpty()) {
                sql.append("AND chandi LIKE ? ");
            }
            if (minPrice != null && !minPrice.trim().isEmpty()) {
                sql.append("AND price >= ? ");
            }
            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                sql.append("AND price <= ? ");
            }

            ps = conn.prepareStatement(sql.toString());
            int index = 1;

            // 设置参数
            if (name != null && !name.trim().isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (chandi != null && !chandi.trim().isEmpty()) {
                ps.setString(index++, "%" + chandi + "%");
            }
            if (minPrice != null && !minPrice.trim().isEmpty()) {
                ps.setDouble(index++, Double.parseDouble(minPrice));
            }
            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                ps.setDouble(index++, Double.parseDouble(maxPrice));
            }

            rs = ps.executeQuery();

            // 封装结果（完全和你原来的字段一致）
            while (rs.next()) {
                ShuiguoxinxiEntity obj = new ShuiguoxinxiEntity();
                obj.setId(rs.getLong("id"));
                obj.setAddtime(rs.getTimestamp("addtime"));
                obj.setShuiguomingcheng(rs.getString("shuiguomingcheng"));
                obj.setShuiguofenlei(rs.getString("shuiguofenlei"));
                obj.setChandi(rs.getString("chandi"));
                obj.setGuige(rs.getString("guige"));
                obj.setCaizhairiqi(rs.getTimestamp("caizhairiqi"));
                obj.setBaozhiqi(rs.getString("baozhiqi"));
                obj.setShuiguojieshao(rs.getString("shuiguojieshao"));
                obj.setShuiguotupian(rs.getString("shuiguotupian"));
                obj.setOnelimittimes(rs.getInt("onelimittimes"));
                obj.setAlllimittimes(rs.getInt("alllimittimes"));
                obj.setThumbsupnum(rs.getInt("thumbsupnum"));
                obj.setCrazilynum(rs.getInt("crazilynum"));
                obj.setClicktime(rs.getTimestamp("clicktime"));
                obj.setClicknum(rs.getInt("clicknum"));
                obj.setDiscussnum(rs.getInt("discussnum"));
                obj.setPrice(rs.getDouble("price"));
                obj.setOnshelves(rs.getInt("onshelves"));
                obj.setStoreupnum(rs.getInt("storeupnum"));

                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, ps, conn);
        }

        return list;
    }
    
    public void addFruit(ShuiguoxinxiEntity fruit) {
        Connection conn = DBUtil.getConnection();
        String sql = "INSERT INTO shuiguoxinxi (addtime,shuiguomingcheng,shuiguofenlei,chandi,price,alllimittimes,shuiguotupian,onshelves) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(fruit.getAddtime().getTime()));
            ps.setString(2, fruit.getShuiguomingcheng());
            ps.setString(3, fruit.getShuiguofenlei());
            ps.setString(4, fruit.getChandi());
            ps.setDouble(5, fruit.getPrice());
            ps.setInt(6, fruit.getAlllimittimes());
            ps.setString(7, fruit.getShuiguotupian());
            ps.setInt(8, fruit.getOnshelves());

            ps.executeUpdate(); // 执行插入
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateFruit(ShuiguoxinxiEntity fruit) {
        String sql = "UPDATE shuiguoxinxi SET shuiguomingcheng=?, shuiguofenlei=?, chandi=?, price=?, alllimittimes=?, shuiguotupian=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fruit.getShuiguomingcheng());
            pstmt.setString(2, fruit.getShuiguofenlei());
            pstmt.setString(3, fruit.getChandi());

            // 价格字段兼容处理
            if (fruit.getPrice() != null) {
                pstmt.setDouble(4, fruit.getPrice().doubleValue());
            } else {
                pstmt.setNull(4, java.sql.Types.DECIMAL);
            }

            pstmt.setInt(5, fruit.getAlllimittimes());
            pstmt.setString(6, fruit.getShuiguotupian());

            // 修正：id是Long类型，使用setLong
            pstmt.setLong(7, fruit.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // 删除水果
    public void deleteFruit(Long id) {
        String sql = "DELETE FROM shuiguoxinxi WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}