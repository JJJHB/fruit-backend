package dao;

import pojo.CartEntity;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDao {

    // 查询购物车中是否已有该商品
    public CartEntity getCartItem(Long userId, Integer fruitId) {

        String sql =
                "select * from cart where user_id=? and fruit_id=?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, userId);
            ps.setInt(2, fruitId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                CartEntity cart = new CartEntity();

                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getLong("user_id"));
                cart.setFruitId(rs.getInt("fruit_id"));
                cart.setQuantity(rs.getInt("quantity"));

                return cart;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 添加购物车
    public boolean addCart(CartEntity cart) {

        String sql =
                "insert into cart(user_id,fruit_id,quantity) values(?,?,?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

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
    public boolean updateCart(CartEntity cart) {

        String sql =
                "update cart set quantity=? where id=?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, cart.getQuantity());
            ps.setInt(2, cart.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 根据用户查询购物车
    public ResultSet getCartByUser(Long userId) {

        try {

            Connection conn = DBUtil.getConnection();

            String sql =
                    "select * from cart where user_id=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setLong(1, userId);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 删除购物车项
    public boolean deleteCart(Integer id) {

        String sql =
                "delete from cart where id=?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateCount(int id,int count){

        String sql =
                "update cart set quantity=? where id=?";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ){

            ps.setInt(1,count);
            ps.setInt(2,id);

            int rows = ps.executeUpdate();

            System.out.println("更新行数="+rows);
            System.out.println("id="+id+" count="+count);

            return rows > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean deleteCart(int id){

        String sql =
                "delete from cart where id=?";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}