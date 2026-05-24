package servlet;

import com.alibaba.fastjson.JSON;
import dao.ShuiguoxinxiDao;
import pojo.ShuiguoxinxiEntity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@WebServlet("/fruitAdd")
public class FruitAddServlet extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // 读取前端传的JSON
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        ShuiguoxinxiEntity fruit = JSON.parseObject(sb.toString(), ShuiguoxinxiEntity.class);

        // ===================== 后端分类验证（关键！）=====================
        String[] validCategories = {
            "水果分类1",
            "水果分类2",
            "水果分类3",
            "水果分类4",
            "水果分类5",
            "水果分类6",
            "水果分类7",
            "水果分类8"
        };

        boolean ok = false;
        for (String s : validCategories) {
            if (s.equals(fruit.getShuiguofenlei())) {
                ok = true;
                break;
            }
        }

        if (!ok) {
            // 非法分类 → 直接返回错误，不执行数据库操作
            response.getWriter().write("{\"code\":500,\"msg\":\"分类只能是 水果分类1-8\"}");
            return;
        }
        // ==============================================================

        // 合法才继续保存
        fruit.setAddtime(new Date());
        fruit.setOnshelves(1);

        ShuiguoxinxiDao dao = new ShuiguoxinxiDao();
        dao.addFruit(fruit);

        response.getWriter().write("{\"code\":200,\"msg\":\"新增成功\"}");
    }
}