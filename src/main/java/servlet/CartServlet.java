package servlet;

import com.google.gson.Gson;
import dao.CartDao;
import dao.YonghuDao;
import dao.ShuiguoxinxiDao;
import pojo.CartEntity;
import pojo.YonghuEntity;
import pojo.ShuiguoxinxiEntity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDao cartDao = new CartDao();
    private YonghuDao yonghuDao = new YonghuDao();
    private ShuiguoxinxiDao fruitDao = new ShuiguoxinxiDao();
    private Gson gson = new Gson();

    // ================= 查询购物车 =================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        String userIdStr = req.getParameter("userId");
        if (userIdStr == null) {
            resp.getWriter().write("[]");
            return;
        }

        long userId = Long.parseLong(userIdStr);

        List<CartEntity> list = cartDao.getCartByUser(userId);
        resp.getWriter().write(gson.toJson(list));
    }

    // ================= 所有操作统一 POST =================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        PrintWriter out = resp.getWriter();
        String action = req.getParameter("action");

        if (action == null) {
            out.write("{\"code\":500,\"msg\":\"缺少action\"}");
            return;
        }

        try {

            // ================= 添加购物车 =================
            if ("add".equals(action)) {

                long userId = Long.parseLong(req.getParameter("userId"));
                int fruitId = Integer.parseInt(req.getParameter("fruitId"));
                int quantity = req.getParameter("quantity") == null ? 1 :
                        Integer.parseInt(req.getParameter("quantity"));

                YonghuEntity user = yonghuDao.getUserById(userId);
                if (user == null) {
                    out.write("{\"code\":500,\"msg\":\"用户不存在\"}");
                    return;
                }

                ShuiguoxinxiEntity fruit = fruitDao.getFruitById(fruitId);
                if (fruit == null) {
                    out.write("{\"code\":500,\"msg\":\"商品不存在\"}");
                    return;
                }

                if (fruit.getStock() < quantity) {
                    out.write("{\"code\":500,\"msg\":\"库存不足\"}");
                    return;
                }

                CartEntity cartItem = cartDao.getCartItem(userId, fruitId);

                boolean ok;
                if (cartItem != null) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    ok = cartDao.updateCount(cartItem.getId(), cartItem.getQuantity());
                } else {
                    CartEntity c = new CartEntity();
                    c.setUserId(userId);
                    c.setFruitId(fruitId);
                    c.setQuantity(quantity);
                    ok = cartDao.addCart(c);
                }

                out.write(ok ? "{\"code\":200,\"msg\":\"加入成功\"}"
                        : "{\"code\":500,\"msg\":\"加入失败\"}");
            }

            // ================= 修改数量 =================
            else if ("update".equals(action)) {

                int id = Integer.parseInt(req.getParameter("id"));
                int count = Integer.parseInt(req.getParameter("count"));

                boolean ok = cartDao.updateCount(id, count);

                out.write(ok ? "{\"code\":200,\"msg\":\"修改成功\"}"
                        : "{\"code\":500,\"msg\":\"修改失败\"}");
            }

            // ================= 删除 =================
            else if ("delete".equals(action)) {

                int id = Integer.parseInt(req.getParameter("id"));

                boolean ok = cartDao.deleteCart(id);

                out.write(ok ? "{\"code\":200,\"msg\":\"删除成功\"}"
                        : "{\"code\":500,\"msg\":\"删除失败\"}");
            }

            else {
                out.write("{\"code\":500,\"msg\":\"未知action\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"code\":500,\"msg\":\"服务器异常\"}");
        }
    }
}