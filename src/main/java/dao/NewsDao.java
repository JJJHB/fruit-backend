package dao;

import pojo.NewsEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    // 1. 条件查询公告列表
    public List<NewsEntity> queryNews(String title) {
        List<NewsEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM news WHERE 1=1";
            if (title != null && !title.trim().isEmpty()) {
                sql += " AND title LIKE ?";
            }
            sql += " ORDER BY addtime DESC";

            ps = conn.prepareStatement(sql);
            if (title != null && !title.trim().isEmpty()) {
                ps.setString(1, "%" + title + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                NewsEntity news = new NewsEntity();
                news.setId(rs.getLong("id"));
                news.setAddtime(rs.getTimestamp("addtime"));
                news.setTitle(rs.getString("title"));
                news.setIntroduction(rs.getString("introduction"));
                news.setTypename(rs.getString("typename"));
                news.setName(rs.getString("name"));
                news.setHeadportrait(rs.getString("headportrait"));
                news.setClicknum(rs.getInt("clicknum"));
                news.setClicktime(rs.getTimestamp("clicktime"));
                news.setThumbsupnum(rs.getInt("thumbsupnum"));
                news.setCrazilynum(rs.getInt("crazilynum"));
                news.setStoreupnum(rs.getInt("storeupnum"));
                news.setPicture(rs.getString("picture"));
                news.setContent(rs.getString("content"));
                list.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, ps, conn);
        }
        return list;
    }

    // 2. 根据 ID 删除单条记录
    public boolean deleteNewsById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM news WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, ps, conn);
        }
        return false;
    }

    // 3. 批量删除
    public boolean deleteBatch(String ids) {
        if (ids == null || ids.trim().isEmpty()) return false;
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "DELETE FROM news WHERE id IN (" + ids + ")";
            return stmt.executeUpdate(sql) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return false;
    }
}