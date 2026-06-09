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

        String sql = "SELECT s.*, c.name AS categoryName " +
                "FROM shuiguoxinxi s " +
                "LEFT JOIN shuiguofenlei c ON s.category_id = c.id";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ShuiguoxinxiEntity obj = new ShuiguoxinxiEntity();
                obj.setId(rs.getLong("id"));
                obj.setName(rs.getString("name"));
                obj.setPrice(rs.getDouble("price"));
                obj.setStock(rs.getInt("stock"));
                obj.setCategoryId(rs.getInt("category_id"));
                obj.setPicture(rs.getString("picture"));
                obj.setDetail(rs.getString("detail"));
                obj.setClicknum(rs.getInt("clicknum"));
                obj.setCategoryName(rs.getString("categoryName"));
                
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, stmt, conn);
        }
        return list;
    }

    // 条件查询
    public List<ShuiguoxinxiEntity> queryByCondition(String name, String minPrice, String maxPrice) {
        List<ShuiguoxinxiEntity> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //联表查询，带出categoryName
            StringBuilder sql = new StringBuilder(
                "SELECT s.*, c.name AS categoryName FROM shuiguoxinxi s " +
                "LEFT JOIN shuiguofenlei c ON s.category_id = c.id WHERE 1=1 "
            );

            if (name != null && !name.trim().isEmpty()) {
                sql.append("AND s.name LIKE ? ");
            }
            if (minPrice != null && !minPrice.trim().isEmpty()) {
                sql.append("AND price >= ? ");
            }
            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                sql.append("AND price <= ? ");
            }

            ps = conn.prepareStatement(sql.toString());
            int index = 1;

            if (name != null && !name.trim().isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (minPrice != null && !minPrice.trim().isEmpty()) {
                ps.setDouble(index++, Double.parseDouble(minPrice));
            }
            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                ps.setDouble(index++, Double.parseDouble(maxPrice));
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                ShuiguoxinxiEntity obj = new ShuiguoxinxiEntity();
                obj.setId(rs.getLong("id"));
                obj.setName(rs.getString("name"));
                obj.setPrice(rs.getDouble("price"));
                obj.setStock(rs.getInt("stock"));
                obj.setCategoryId(rs.getInt("category_id"));
                obj.setPicture(rs.getString("picture"));
                obj.setDetail(rs.getString("detail"));
                obj.setClicknum(rs.getInt("clicknum"));
                obj.setCategoryName(rs.getString("categoryName"));
                
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, ps, conn);
        }
        return list;
    }

	 // 新增水果
	    public void addFruit(ShuiguoxinxiEntity fruit) {
	        String sql = "INSERT INTO shuiguoxinxi (name, price, stock, category_id, picture, detail, clicknum) VALUES (?,?,?,?,?,?,?)";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	
	            ps.setString(1, fruit.getName());
	            ps.setDouble(2, fruit.getPrice());
	            ps.setInt(3, fruit.getStock());
	            ps.setInt(4, fruit.getCategoryId());
	            ps.setString(5, fruit.getPicture());
	            ps.setString(6, fruit.getDetail());
	            ps.setInt(7, fruit.getClicknum());
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

    // 修改水果
    public void updateFruit(ShuiguoxinxiEntity fruit) {
        String sql = "UPDATE shuiguoxinxi SET name=?, price=?, stock=?, category_id=?, picture=?, detail=?, clicknum=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fruit.getName());
            pstmt.setDouble(2, fruit.getPrice());
            pstmt.setInt(3, fruit.getStock());
            pstmt.setInt(4, fruit.getCategoryId());
            pstmt.setString(5, fruit.getPicture());
            pstmt.setString(6, fruit.getDetail());
            pstmt.setInt(7, fruit.getClicknum());
            pstmt.setLong(8, fruit.getId());
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
    
   
      // 水果点击量 +1
    public int addClickNum(int fruitId) {
        String sql = "UPDATE shuiguoxinxi SET clicknum = clicknum + 1 WHERE id = ?";
        try (java.sql.Connection conn = util.DBUtil.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fruitId);
            return pstmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}