package dao;

import pojo.OrdersEntity;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao {

    /**
     * 查询全部订单
     */
    public List<OrdersEntity> getAllOrders() {

        List<OrdersEntity> list = new ArrayList<>();

        String sql = "select * from orders order by create_time desc";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                OrdersEntity order = new OrdersEntity();

                order.setId(rs.getInt("id"));
                order.setUserId(rs.getLong("user_id"));
                order.setAddressId(rs.getInt("address_id"));
                order.setFruitId(rs.getInt("fruit_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setStatus(rs.getString("status"));
                order.setCreateTime(rs.getString("create_time"));

                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 按状态查询订单
     */
    public List<OrdersEntity> getOrdersByStatus(String status) {

        List<OrdersEntity> list = new ArrayList<>();

        String sql = "select * from orders where status=? order by create_time desc";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrdersEntity order = new OrdersEntity();

                order.setId(rs.getInt("id"));
                order.setUserId(rs.getLong("user_id"));
                order.setAddressId(rs.getInt("address_id"));
                order.setFruitId(rs.getInt("fruit_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setPrice(rs.getDouble("price"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setStatus(rs.getString("status"));
                order.setCreateTime(rs.getString("create_time"));

                list.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 修改订单状态
     */
    public boolean updateStatus(int id, String status) {

        String sql =
                "update orders set status=? where id=?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 删除订单
     */
    public boolean deleteOrder(int id) {

        String sql =
                "delete from orders where id=?";

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
}