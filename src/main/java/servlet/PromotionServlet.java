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
    // 改成兼容前端的格式
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		

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
                    obj.put("fruitId", p.getFruitId());
                    obj.put("title", p.getTitle());
                    obj.put("discountPrice", p.getDiscountPrice());
                    obj.put("startTime", sdf.format(p.getStartTime()));
                    obj.put("endTime", sdf.format(p.getEndTime()));
                    result.put("data", obj);
                }
            } else {
                String title = request.getParameter("title");
                String fruitIdStr = request.getParameter("fruitId");
                Integer fruitId = (fruitIdStr != null && !fruitIdStr.isEmpty()) ? Integer.parseInt(fruitIdStr) : null;
                List<PromotionEntity> list = dao.getPromotionByCondition(title, fruitId);

                JSONArray array = new JSONArray();
                for (PromotionEntity p : list) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", p.getId());
                    obj.put("fruitId", p.getFruitId());
                    obj.put("title", p.getTitle());
                    obj.put("discountPrice", p.getDiscountPrice());
                    obj.put("startTime", sdf.format(p.getStartTime()));
                    obj.put("endTime", sdf.format(p.getEndTime()));
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
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                String title = request.getParameter("title");
                double discountPrice = Double.parseDouble(request.getParameter("discountPrice"));
                String startTimeStr = request.getParameter("startTime");
                String endTimeStr = request.getParameter("endTime");

                PromotionEntity p = new PromotionEntity();
                p.setId(id);
                p.setFruitId(fruitId);
                p.setTitle(title);
                p.setDiscountPrice(discountPrice);
                p.setStartTime(sdf.parse(startTimeStr));
                p.setEndTime(sdf.parse(endTimeStr));

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
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                String title = request.getParameter("title");
                double discountPrice = Double.parseDouble(request.getParameter("discountPrice"));
                String startTimeStr = request.getParameter("startTime");
                String endTimeStr = request.getParameter("endTime");

                PromotionEntity p = new PromotionEntity();
                p.setFruitId(fruitId);
                p.setTitle(title);
                p.setDiscountPrice(discountPrice);
                p.setStartTime(sdf.parse(startTimeStr));
                p.setEndTime(sdf.parse(endTimeStr));

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