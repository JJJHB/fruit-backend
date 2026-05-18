package servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.UsersEntity;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/userQueryList")
public class UserQueryListServlet extends HttpServlet {

    private UserDao usersDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ✅ 设置跨域头
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8081"); // 前端地址
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true"); // 如果前端要发送 cookie

        // 设置响应类型为 JSON
        resp.setContentType("application/json;charset=utf-8");

        List<UsersEntity> users = usersDao.getAllUsers();

        // 转换为 JSONArray
        JSONArray ja = JSONArray.fromObject(users);

        // 包装成一个 JSON 对象
        JSONObject result = new JSONObject();
        result.put("users", ja);
        result.put("total", users.size()); // 总条数

        PrintWriter out = resp.getWriter();
        out.print(result.toString());
        out.flush();
        out.close();
    }

    // ✅ 处理 OPTIONS 预检请求（必要）
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}