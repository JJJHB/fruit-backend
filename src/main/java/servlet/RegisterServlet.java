package servlet;

import dao.YonghuDao;
import pojo.YonghuEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private YonghuDao yonghuDao = new YonghuDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // 读取JSON
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String json = sb.toString();

        String username = extractJsonValue(json, "username");
        String password = extractJsonValue(json, "password");
        String sex = extractJsonValue(json, "sex");
        String phone = extractJsonValue(json, "phone");
        String picture = extractJsonValue(json, "picture");

        System.out.println("收到注册请求:");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("sex = " + sex);
        System.out.println("phone = " + phone);

        // 非空校验
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()) {

            out.print("{\"code\":500,\"msg\":\"用户名、密码和手机号不能为空\"}");
            return;
        }

        // 手机号格式校验
        if (!phone.matches("^1[3-9]\\d{9}$")) {
            out.print("{\"code\":500,\"msg\":\"手机号格式错误\"}");
            return;
        }

        // 检查账号是否存在
        if (yonghuDao.exists(username)) {
            out.print("{\"code\":500,\"msg\":\"该账号已存在\"}");
            return;
        }

        // 创建用户对象
        YonghuEntity user = new YonghuEntity();
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        user.setSex(sex == null || sex.isEmpty() ? "男" : sex);
        user.setPhone(phone.trim());
        user.setPicture(picture == null ? "" : picture.trim());
        user.setMoney(BigDecimal.ZERO);

        // 注册
        boolean success = yonghuDao.register(user);

        if (success) {
            out.print("{\"code\":200,\"msg\":\"注册成功\"}");
        } else {
            out.print("{\"code\":500,\"msg\":\"注册失败\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.html");
    }

    // 简单 JSON 字符串解析器，提取 "key":"value"
    private String extractJsonValue(String json, String key) {
        if (json == null || key == null) return null;
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}