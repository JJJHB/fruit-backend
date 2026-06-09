package servlet;

import com.alibaba.fastjson.JSON;
import dao.OrdersDao;
import pojo.OrdersEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ordersQuery")
public class OrderQueryServlet extends HttpServlet {

    private OrdersDao ordersDao = new OrdersDao();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        String status = request.getParameter("status");

        List<OrdersEntity> list;

        if (status == null || status.trim().isEmpty()) {
            // 查询全部订单
            list = ordersDao.getAllOrders();
        } else {
            // 按状态查询
            list = ordersDao.getOrdersByStatus(status);
        }

        String json = JSON.toJSONString(list);
        response.getWriter().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}