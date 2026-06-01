package dao;

import pojo.PromotionEntity;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionDao {

    // 条件查询（按 title / fruit_id 模糊匹配）
    public List<PromotionEntity> getPromotionByCondition(String title, Integer fruitId) {
        List<PromotionEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                "SELECT id, fruit_id, title, discount_price, start_time, end_time FROM cuxiaohuodong WHERE 1=1");

            if (title != null && !title.isEmpty()) {
                sql.append(" AND title LIKE ?");
            }
            if (fruitId != null) {
                sql.append(" AND fruit_id = ?");
            }

            pstmt = conn.prepareStatement(sql.toString());
            int index = 1;
            if (title != null && !title.isEmpty()) {
                pstmt.setString(index++, "%" + title + "%");
            }
            if (fruitId != null) {
                pstmt.setInt(index++, fruitId);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                PromotionEntity p = new PromotionEntity();
                p.setId(rs.getInt("id"));
                p.setFruitId(rs.getInt("fruit_id"));
                p.setTitle(rs.getString("title"));
                p.setDiscountPrice(rs.getDouble("discount_price"));
                p.setStartTime(rs.getTimestamp("start_time"));
                p.setEndTime(rs.getTimestamp("end_time"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return list;
    }

    // 新增促销
    public int addPromotion(PromotionEntity p) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO cuxiaohuodong(fruit_id, title, discount_price, start_time, end_time) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, p.getFruitId());
            pstmt.setString(2, p.getTitle());
            pstmt.setDouble(3, p.getDiscountPrice());
            pstmt.setTimestamp(4, new java.sql.Timestamp(p.getStartTime().getTime()));
            pstmt.setTimestamp(5, new java.sql.Timestamp(p.getEndTime().getTime()));
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 修改促销
    public int updatePromotion(PromotionEntity p) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE cuxiaohuodong SET fruit_id=?, title=?, discount_price=?, start_time=?, end_time=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, p.getFruitId());
            pstmt.setString(2, p.getTitle());
            pstmt.setDouble(3, p.getDiscountPrice());
            pstmt.setTimestamp(4, new java.sql.Timestamp(p.getStartTime().getTime()));
            pstmt.setTimestamp(5, new java.sql.Timestamp(p.getEndTime().getTime()));
            pstmt.setInt(6, p.getId());
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 删除促销
    public int deletePromotion(int id) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM cuxiaohuodong WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 根据ID查询
    public PromotionEntity getPromotionById(int id) {
        PromotionEntity p = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, fruit_id, title, discount_price, start_time, end_time FROM cuxiaohuodong WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new PromotionEntity();
                p.setId(rs.getInt("id"));
                p.setFruitId(rs.getInt("fruit_id"));
                p.setTitle(rs.getString("title"));
                p.setDiscountPrice(rs.getDouble("discount_price"));
                p.setStartTime(rs.getTimestamp("start_time"));
                p.setEndTime(rs.getTimestamp("end_time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return p;
    }
}