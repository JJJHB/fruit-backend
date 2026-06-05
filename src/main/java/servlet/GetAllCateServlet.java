package servlet;

import com.alibaba.fastjson.JSON;
import dao.ShuiguofenleiDao;
import pojo.ShuiguofenleiEntity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getAllCate")
public class GetAllCateServlet extends HttpServlet {
    private final ShuiguofenleiDao fenleiDao = new ShuiguofenleiDao();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Methods", "GET,OPTIONS");
        resp.setStatus(200);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ShuiguofenleiEntity> cateList = fenleiDao.getAllFenlei();
        response.getWriter().write(JSON.toJSONString(cateList));
    }
}