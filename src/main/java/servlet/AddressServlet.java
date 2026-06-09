package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AddressDao;
import pojo.AddressEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/address/*")
public class AddressServlet extends HttpServlet {

    private final AddressDao dao = new AddressDao();

    // ✅ 防乱码关键：固定 UTF-8 + 禁止 HTML 转义
    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ 强制 UTF-8（关键）
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getPathInfo();
        if (action == null) {
            write(resp, fail("invalid action"));
            return;
        }

        switch (action) {

            // ================= 地址列表 =================
            case "/list": {
                String uidStr = req.getParameter("userId");
                if (uidStr == null) {
                    write(resp, gson.toJson(List.of()));
                    return;
                }

                long userId = Long.parseLong(uidStr);
                List<AddressEntity> list = dao.getAddressByUser(userId);

                write(resp, gson.toJson(list));
                break;
            }

            // ================= 删除 =================
            case "/delete": {
                String idStr = req.getParameter("id");
                if (idStr == null) {
                    write(resp, fail("missing id"));
                    return;
                }

                boolean ok = dao.delete(Integer.parseInt(idStr));
                write(resp, success(ok));
                break;
            }

            // ================= 设置默认 =================
            case "/default": {
                String idStr = req.getParameter("id");
                String userIdStr = req.getParameter("userId");

                if (idStr == null || userIdStr == null) {
                    write(resp, fail("missing params"));
                    return;
                }

                boolean ok = dao.setDefault(
                        Integer.parseInt(idStr),
                        Long.parseLong(userIdStr)
                );

                write(resp, success(ok));
                break;
            }

            default:
                write(resp, fail("unknown action"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ POST 防乱码核心
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String action = req.getPathInfo();
        if (action == null) {
            write(resp, fail("invalid action"));
            return;
        }

        // ✅ 直接 JSON 解析（前端必须 axios.post JSON）
        AddressEntity a = gson.fromJson(
                req.getReader(),
                AddressEntity.class
        );

        switch (action) {

            // ================= 新增 =================
            case "/add": {
                boolean ok = dao.add(a);
                write(resp, success(ok));
                break;
            }

            // ================= 更新 =================
            case "/update": {
                boolean ok = dao.update(a);
                write(resp, success(ok));
                break;
            }

            default:
                write(resp, fail("unknown action"));
        }
    }

    // ================= 工具方法 =================

    private void write(HttpServletResponse resp, String json) throws IOException {
        resp.getWriter().write(json);
    }

    private String success(boolean ok) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", ok);
        return gson.toJson(map);
    }

    private String fail(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", msg);
        return gson.toJson(map);
    }
}