package servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.YonghuEntity; // 实体类对应 UsersEntity
import dao.YonghuDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/yonghuQueryList")
public class YonghuQueryListServlet extends HttpServlet {

    private YonghuDao yonghuDao = new YonghuDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        List<YonghuEntity> users = yonghuDao.getAllUsers();

        JSONArray ja = JSONArray.fromObject(users);

        JSONObject result = new JSONObject();
        result.put("users", ja);
        result.put("total", users.size());

        resp.getWriter().print(result);
    }
}