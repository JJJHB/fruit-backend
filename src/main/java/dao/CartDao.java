package dao;

import pojo.CartEntity;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    // 查询单个购物项
    public CartEntity getCartItem(Long userId, Integer fruitId) {

        String sql = "select * from cart where user_id=? and fruit_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setInt(2, fruitId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CartEntity c = new CartEntity();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getLong("user_id"));
                c.setFruitId(rs.getInt("fruit_id"));
                c.setQuantity(rs.getInt("quantity"));
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 添加
    public boolean addCart(CartEntity cart) {

        String sql = "insert into cart(user_id,fruit_id,quantity) values(?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cart.getUserId());
            ps.setInt(2, cart.getFruitId());
            ps.setInt(3, cart.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 修改数量
    public boolean updateCount(int id, int count) {

        String sql = "update cart set quantity=? where id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, count);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 删除
    public boolean deleteCart(int id) {

        String sql = "delete from cart where id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 查询购物车列表
    public List<CartEntity> getCartByUser(Long userId) {

        String sql = "select * from cart where user_id=?";
        List<CartEntity> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartEntity c = new CartEntity();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getLong("user_id"));
                c.setFruitId(rs.getInt("fruit_id"));
                c.setQuantity(rs.getInt("quantity"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}