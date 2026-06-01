package dao;

import pojo.NewsEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    // 1. 条件查询公告列表（已修改为第一版字段：id, title, content, create_time）
    public List<NewsEntity> queryNews(String title) {
        List<NewsEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // 第一版只查询需要的核心字段，按 create_time 排序
            String sql = "SELECT id, title, content, create_time FROM news WHERE 1=1";
            if (title != null && !title.trim().isEmpty()) {
                sql += " AND title LIKE ?";
            }
            sql += " ORDER BY create_time DESC";

            ps = conn.prepareStatement(sql);
            if (title != null && !title.trim().isEmpty()) {
                ps.setString(1, "%" + title + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                NewsEntity news = new NewsEntity();
                // 仅组装第一版数据库拥有的字段
                news.setId(rs.getLong("id")); // 如果你第一版实体类里id是Integer，可以改为 rs.getInt("id")
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setCreateTime(rs.getTimestamp("create_time")); // 映射第一版的 create_time
                
                list.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(rs, ps, conn);
        }
        return list;
    }

    // 2. 根据 ID 删除单条记录（保持不变，通过 id 删除不受字段缩减影响）
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

    // 3. 批量删除（保持不变）
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
            // 这里顺便帮你统一用你的 DBUtil 优雅关闭，防止资源泄露
            DBUtil.closeJDBC(null, stmt, conn);
        }
        return false;
    }
 // 4. 新增公告（匹配第一版 DDL：仅写入 title 和 content，create_time 由数据库自动生成）
    public boolean addNews(pojo.NewsEntity news) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO news (title, content) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, ps, conn);
        }
        return false;
    }

    // 5. 修改公告（匹配第一版 DDL：仅更新 title 和 content）
    public boolean updateNews(pojo.NewsEntity news) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE news SET title = ?, content = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getContent());
            ps.setLong(3, news.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeJDBC(null, ps, conn);
        }
        return false;
    }
}



	