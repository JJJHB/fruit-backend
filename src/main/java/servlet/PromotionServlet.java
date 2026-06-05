package servlet;

import dao.PromotionDao;
import pojo.PromotionEntity;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/promotion")
public class PromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PromotionDao dao = new PromotionDao();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            String title = request.getParameter("title");
            String fruitIdStr = request.getParameter("fruitId");
            Integer fruitId = null;
            if (fruitIdStr != null && !fruitIdStr.isEmpty()) {
                fruitId = Integer.parseInt(fruitIdStr);
            }

            List<PromotionEntity> list = dao.getPromotionList(title, fruitId);
            result.put("code", 200);
            result.put("promotions", list);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "查询失败");
        }
        out.print(result.toString());
        out.close();
    }

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
                    result.put("msg", "修改失败");
                }

            } else {
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
                    result.put("msg", "新增失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "错误：" + e.getMessage());
        }
        out.print(result.toString());
        out.close();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int count = dao.deletePromotion(id);
            if (count > 0) {
                result.put("code", 200);
                result.put("msg", "删除成功");
            } else {
                result.put("code", 500);
                result.put("msg", "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "删除异常");
        }
        out.print(result.toString());
        out.close();
    }
}