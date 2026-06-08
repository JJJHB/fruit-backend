package servlet;

import dao.CartDao;
import dao.ShuiguoxinxiDao;
import pojo.ShuiguoxinxiEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/cart")
public class QueryCartServlet extends HttpServlet {

    private CartDao cartDao = new CartDao();
    private ShuiguoxinxiDao fruitDao = new ShuiguoxinxiDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 从前端 GET 参数获取 userId
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"请先登录\"}");
            return;
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":400,\"msg\":\"用户 ID 格式错误\"}");
            return;
        }

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();
        json.append("[");

        try {
            ResultSet rs = cartDao.getCartByUser(userId);
            boolean first = true;

            while (rs.next()) {
                int fruitId = rs.getInt("fruit_id");
                int quantity = rs.getInt("quantity");

                ShuiguoxinxiEntity fruit = fruitDao.getFruitById(fruitId);
                if (fruit == null) continue;

                if (!first) {
                    json.append(",");
                }

                // 生成 JSON，注意防止 null
                String name = fruit.getName() == null ? "" : fruit.getName();
                double price = fruit.getPrice() == null ? 0.0 : fruit.getPrice();
                String picture = fruit.getPicture() == null ? "" : fruit.getPicture();

                json.append("{")
                        .append("\"id\":").append(rs.getInt("id")).append(",")
                        .append("\"fruitId\":").append(fruitId).append(",")
                        .append("\"name\":\"").append(name).append("\",")
                        .append("\"price\":").append(price).append(",")
                        .append("\"count\":").append(quantity).append(",")
                        .append("\"image\":\"http://localhost:8082/fruit-backend/upload/").append(picture).append("\"")
                        .append("}");

                first = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");
        out.write(json.toString());
        out.flush();
        out.close();
    }
}