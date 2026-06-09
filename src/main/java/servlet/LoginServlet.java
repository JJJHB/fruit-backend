package servlet;

import dao.UserDao;
import dao.YonghuDao;
import pojo.UserEntity;
import pojo.YonghuEntity;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao = new UserDao();
    private YonghuDao yonghuDao = new YonghuDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        System.out.println("username=" + username);
        System.out.println("password=" + password);
        System.out.println("role=" + role);
        String json;

        // ================= 管理员登录 =================
        if ("admin".equals(role)) {

            UserEntity admin = null;

            for (UserEntity u : userDao.getAllUsers()) {
                if (u.getUsername().equals(username)) {
                    admin = u;
                    break;
                }
            }

            if (admin != null && admin.getPassword().equals(password)) {
                json = "{\"code\":200,\"msg\":\"管理员登录成功\",\"role\":\"admin\"}";
            } else {
                json = "{\"code\":500,\"msg\":\"管理员账号或密码错误\"}";
            }
        }

        // ================= 用户登录 =================
        else {

            YonghuEntity user = null;

            for (YonghuEntity u : yonghuDao.getAllUsers()) {
                if (u.getUsername().equals(username)) {
                    user = u;
                    break;
                }
            }

            if (user != null && user.getPassword().equals(password)) {

                json = "{"
                        + "\"code\":200,"
                        + "\"msg\":\"用户登录成功\","
                        + "\"role\":\"user\","
                        + "\"id\":" + user.getId() + ","
                        + "\"username\":\"" + user.getUsername() + "\","
                        + "\"sex\":\"" + (user.getSex() == null ? "" : user.getSex()) + "\","
                        + "\"phone\":\"" + (user.getPhone() == null ? "" : user.getPhone()) + "\","
                        + "\"picture\":\"" + (user.getPicture() == null ? "" : user.getPicture()) + "\","
                        + "\"money\":" + user.getMoney()
                        + "}";

            } else {
                json = "{\"code\":500,\"msg\":\"用户账号或密码错误\"}";
            }
        }

        resp.getWriter().write(json);
    }
}