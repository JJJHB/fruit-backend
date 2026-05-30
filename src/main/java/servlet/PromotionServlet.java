package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.text.SimpleDateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import pojo.PromotionEntity;
import dao.PromotionDao;

@WebServlet("/promotion")
public class PromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PromotionDao dao = new PromotionDao();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                PromotionEntity p = dao.getPromotionById(id);
                if (p != null) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", p.getId());
                    obj.put("addtime", sdf.format(p.getAddtime()));
                    obj.put("shuiguomingcheng", p.getShuiguomingcheng());
                    obj.put("shuiguofenlei", p.getShuiguofenlei());
                    obj.put("chandi", p.getChandi());
                    obj.put("price", p.getPrice()); // 返回 price
                    result.put("data", obj);
                }
            } else {
                String name = request.getParameter("shuiguomingcheng");
                String chandi = request.getParameter("chandi");
                String fenlei = request.getParameter("shuiguofenlei");
                List<PromotionEntity> list = dao.getPromotionByCondition(name, chandi, fenlei);

                JSONArray array = new JSONArray();
                for (PromotionEntity p : list) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", p.getId());
                    obj.put("addtime", sdf.format(p.getAddtime()));
                    obj.put("shuiguomingcheng", p.getShuiguomingcheng());
                    obj.put("shuiguofenlei", p.getShuiguofenlei());
                    obj.put("chandi", p.getChandi());
                    obj.put("price", p.getPrice());
                    array.add(obj);
                }
                result.put("promotions", array);
            }
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        out.print(result.toString());
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            String action = request.getParameter("action");

            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("shuiguomingcheng");
                String fenlei = request.getParameter("shuiguofenlei");
                String chandi = request.getParameter("chandi");
                Double price = Double.parseDouble(request.getParameter("price")); // 接收 price

                PromotionEntity p = new PromotionEntity();
                p.setId(id);
                p.setShuiguomingcheng(name);
                p.setShuiguofenlei(fenlei);
                p.setChandi(chandi);
                p.setPrice(price);

                int count = dao.updatePromotion(p);
                if (count > 0) {
                    result.put("code", 200);
                    result.put("msg", "修改成功");
                } else {
                    result.put("code", 500);
                    result.put("msg", "修改失败：影响行数为0");
                }

            } else {
                // 新增
                String name = request.getParameter("shuiguomingcheng");
                String fenlei = request.getParameter("shuiguofenlei");
                String chandi = request.getParameter("chandi");
                Double price = Double.parseDouble(request.getParameter("price"));

                PromotionEntity p = new PromotionEntity();
                p.setShuiguomingcheng(name);
                p.setShuiguofenlei(fenlei);
                p.setChandi(chandi);
                p.setPrice(price);

                int count = dao.addPromotion(p);
                if (count > 0) {
                    result.put("code", 200);
                    result.put("msg", "新增成功");
                } else {
                    result.put("code", 500);
                    result.put("msg", "新增失败：影响行数为0");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "操作失败：" + (e.getMessage() == null ? "未知错误" : e.getMessage()));
        }
        out.print(result.toString());
        out.close();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            int count = dao.deletePromotion(id);
            if (count > 0) {
                result.put("code", 200);
                result.put("msg", "删除成功");
            } else {
                result.put("code", 500);
                result.put("msg", "删除失败：影响行数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
        }
        out.print(result.toString());
        out.close();
    }
}