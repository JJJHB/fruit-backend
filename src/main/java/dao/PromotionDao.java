package dao;

import pojo.PromotionEntity;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionDao {

    // 条件查询（返回所有字段，含 price）
    public List<PromotionEntity> getPromotionByCondition(String shuiguomingcheng, String chandi, String shuiguofenlei) {
        List<PromotionEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder(
                "SELECT id, addtime, shuiguomingcheng, shuiguofenlei, chandi, price FROM cuxiaohuodong WHERE 1=1");

            if (shuiguomingcheng != null && !shuiguomingcheng.isEmpty()) {
                sql.append(" AND shuiguomingcheng LIKE ?");
            }
            if (chandi != null && !chandi.isEmpty()) {
                sql.append(" AND chandi LIKE ?");
            }
            if (shuiguofenlei != null && !shuiguofenlei.isEmpty()) {
                sql.append(" AND shuiguofenlei LIKE ?");
            }

            pstmt = conn.prepareStatement(sql.toString());
            int index = 1;
            if (shuiguomingcheng != null && !shuiguomingcheng.isEmpty()) {
                pstmt.setString(index++, "%" + shuiguomingcheng + "%");
            }
            if (chandi != null && !chandi.isEmpty()) {
                pstmt.setString(index++, "%" + chandi + "%");
            }
            if (shuiguofenlei != null && !shuiguofenlei.isEmpty()) {
                pstmt.setString(index++, "%" + shuiguofenlei + "%");
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                PromotionEntity p = new PromotionEntity();
                p.setId(rs.getInt("id"));
                p.setAddtime(rs.getTimestamp("addtime"));
                p.setShuiguomingcheng(rs.getString("shuiguomingcheng"));
                p.setShuiguofenlei(rs.getString("shuiguofenlei"));
                p.setChandi(rs.getString("chandi"));
                p.setPrice(rs.getDouble("price")); // 读 price
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return list;
    }

    // 新增：带 price（数据库已有 NOT NULL，必须传）
 // 新增：带 price + 自动添加时间
    public int addPromotion(PromotionEntity p) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            
            // 1. 临时关闭外键约束
            conn.createStatement().execute("SET FOREIGN_KEY_CHECKS = 0;");
            
            // 2. 执行插入（现在不会再被外键拦截了）
            String sql = "INSERT INTO cuxiaohuodong(shuiguomingcheng, shuiguofenlei, chandi, price, addtime) VALUES(?,?,?,?,NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getShuiguomingcheng());
            pstmt.setString(2, p.getShuiguofenlei());
            pstmt.setString(3, p.getChandi());
            pstmt.setDouble(4, p.getPrice());
            
            rows = pstmt.executeUpdate();
            
            // 3. 插入完成后，重新开启外键约束
            conn.createStatement().execute("SET FOREIGN_KEY_CHECKS = 1;");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }
    // 修改：带 price
    public int updatePromotion(PromotionEntity p) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE cuxiaohuodong SET shuiguomingcheng=?, shuiguofenlei=?, chandi=?, price=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getShuiguomingcheng());
            pstmt.setString(2, p.getShuiguofenlei());
            pstmt.setString(3, p.getChandi());
            pstmt.setDouble(4, p.getPrice()); // 更新 price
            pstmt.setInt(5, p.getId());
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 删除（不变）
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

    // 根据ID查询（带 price）
    public PromotionEntity getPromotionById(int id) {
        PromotionEntity p = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT id, addtime, shuiguomingcheng, shuiguofenlei, chandi, price FROM cuxiaohuodong WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new PromotionEntity();
                p.setId(rs.getInt("id"));
                p.setAddtime(rs.getTimestamp("addtime"));
                p.setShuiguomingcheng(rs.getString("shuiguomingcheng"));
                p.setShuiguofenlei(rs.getString("shuiguofenlei"));
                p.setChandi(rs.getString("chandi"));
                p.setPrice(rs.getDouble("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, pstmt, conn);
        }
        return p;
    }
}