package servlet;

import dao.CartDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cartupdate")
public class UpdateCartServlet extends HttpServlet {

    private CartDao cartDao = new CartDao();

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");

        try {

            int id = Integer.parseInt(req.getParameter("id"));
            int count = Integer.parseInt(req.getParameter("count"));

            boolean result = cartDao.updateCount(id, count);

            if(result){
                resp.getWriter().print(
                        "{\"code\":200,\"msg\":\"修改成功\"}"
                );
            }else{
                resp.getWriter().print(
                        "{\"code\":500,\"msg\":\"修改失败\"}"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            resp.getWriter().print(
                    "{\"code\":500,\"msg\":\"服务器异常\"}"
            );
        }
    }
}