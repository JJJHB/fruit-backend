package servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dao.ConfigDao;
import pojo.ConfigEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/configQuery")
public class ConfigQueryServlet extends HttpServlet {

    private final ConfigDao dao = new ConfigDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp); // GET 和 POST 都走同一逻辑
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // CORS 跨域支持（如果需要前端跨域访问）
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setContentType("application/json;charset=utf-8");

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        List<ConfigEntity> configs = dao.findAll();

        // 把 List 转 JSONArray
        JSONArray ja = JSONArray.fromObject(configs);

        // 可以封装成一个 JSON 对象返回
        JSONObject result = new JSONObject();
        result.put("configs", ja);
        result.put("total", configs.size());

        PrintWriter out = resp.getWriter();
        out.print(result.toString());
        out.flush();
        out.close();
    }
}