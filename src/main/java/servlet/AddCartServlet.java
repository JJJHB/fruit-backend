package servlet;

import dao.CartDao;
import dao.YonghuDao;
import dao.ShuiguoxinxiDao;
import pojo.CartEntity;
import pojo.YonghuEntity;
import pojo.ShuiguoxinxiEntity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/addCart")
public class AddCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private YonghuDao yonghuDao = new YonghuDao();
    private ShuiguoxinxiDao fruitDao = new ShuiguoxinxiDao();
    private CartDao cartDao = new CartDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String userIdStr = req.getParameter("userId");
        String fruitIdStr = req.getParameter("fruitId");
        String quantityStr = req.getParameter("quantity");

        PrintWriter out = resp.getWriter();

        if (userIdStr == null || fruitIdStr == null) {
            out.write("{\"code\":500,\"msg\":\"缺少参数\"}");
            return;
        }

        long userId = Long.parseLong(userIdStr);
        int fruitId = Integer.parseInt(fruitIdStr);
        int quantity = (quantityStr == null) ? 1 : Integer.parseInt(quantityStr);

        // 验证用户是否存在
        YonghuEntity user = yonghuDao.getUserById(userId);
        if (user == null) {
            out.write("{\"code\":500,\"msg\":\"用户不存在\"}");
            return;
        }

        // 验证商品是否存在
        ShuiguoxinxiEntity fruit = fruitDao.getFruitById(fruitId);
        if (fruit == null) {
            out.write("{\"code\":500,\"msg\":\"商品不存在\"}");
            return;
        }

        // 检查库存
        if (fruit.getStock() < quantity) {
            out.write("{\"code\":500,\"msg\":\"库存不足\"}");
            return;
        }

        // 查询购物车是否已有该商品
        CartEntity cartItem = cartDao.getCartItem(userId, fruitId);
        if (cartItem != null) {
            // 已存在，增加数量
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            boolean ok = cartDao.updateCart(cartItem);
            if (ok) {
                out.write("{\"code\":200,\"msg\":\"加入购物车成功\"}");
            } else {
                out.write("{\"code\":500,\"msg\":\"加入购物车失败\"}");
            }
        } else {
            // 新增购物车条目
            CartEntity newItem = new CartEntity();
            newItem.setUserId(userId);
            newItem.setFruitId(fruitId);
            newItem.setQuantity(quantity);

            boolean ok = cartDao.addCart(newItem);
            if (ok) {
                out.write("{\"code\":200,\"msg\":\"加入购物车成功\"}");
            } else {
                out.write("{\"code\":500,\"msg\":\"加入购物车失败\"}");
            }
        }
    }
}