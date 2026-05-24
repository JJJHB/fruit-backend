package servlet;

import dao.ShuiguoxinxiDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fruitDelete")
public class FruitDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 跨域配置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");

        // 获取前端传过来的id
        String id = request.getParameter("id");
        ShuiguoxinxiDao dao = new ShuiguoxinxiDao();
        dao.deleteFruit(Long.parseLong(id));

        // 返回成功
        response.getWriter().write("{\"code\":200,\"msg\":\"删除成功\"}");
    }
}