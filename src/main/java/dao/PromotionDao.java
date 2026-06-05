package dao;
import util.DBUtil;
import pojo.PromotionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PromotionDao {

    // 查询全部/条件查询
	public List<PromotionEntity> getPromotionList(String title, Integer fruitId) {
	    List<PromotionEntity> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        // 写死sql，不带任何where条件
	        String sql = "SELECT id, fruit_id, title, discount_price, start_time, end_time FROM cuxiaohuodong";
	        pstmt = conn.prepareStatement(sql);
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
	        // 控制台打印查到几条
	        System.out.println("数据库查出条数："+list.size());

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.closeJDBC(rs, pstmt, conn);
	    }
	    return list;
	}
    // 新增
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
            pstmt.setTimestamp(4, new Timestamp(p.getStartTime().getTime()));
            pstmt.setTimestamp(5, new Timestamp(p.getEndTime().getTime()));
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 修改
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
            pstmt.setTimestamp(4, new Timestamp(p.getStartTime().getTime()));
            pstmt.setTimestamp(5, new Timestamp(p.getEndTime().getTime()));
            pstmt.setInt(6, p.getId());
            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, pstmt, conn);
        }
        return rows;
    }

    // 删除
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
}