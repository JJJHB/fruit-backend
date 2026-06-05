package servlet;

import com.alibaba.fastjson.JSON;
import dao.ShuiguoxinxiDao;
import dao.ShuiguofenleiDao;
import pojo.ShuiguoxinxiEntity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

@WebServlet("/fruitUpdate")
public class FruitUpdateServlet extends HttpServlet {

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

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        ShuiguoxinxiEntity fruit = JSON.parseObject(sb.toString(), ShuiguoxinxiEntity.class);

        // 主键ID非空校验
        if (fruit.getId() == null) {
            response.getWriter().write("{\"code\":500,\"msg\":\"数据ID不能为空\"}");
            return;
        }

        // 分类ID校验
        Integer cid = fruit.getCategoryId();
        if (cid == null || cid <= 0) {
            response.getWriter().write("{\"code\":500,\"msg\":\"分类不能为空\"}");
            return;
        }

        // 动态校验分类ID是否存在于数据库
        ShuiguofenleiDao fenleiDao = new ShuiguofenleiDao();
        Set<Integer> validCategoryIds = fenleiDao.getAllCategoryIds();
        if (!validCategoryIds.contains(cid)) {
            response.getWriter().write("{\"code\":500,\"msg\":\"分类ID不存在，请使用数据表中的有效分类\"}");
            return;
        }

        // 执行修改
        ShuiguoxinxiDao dao = new ShuiguoxinxiDao();
        dao.updateFruit(fruit);
        response.getWriter().write("{\"code\":200,\"msg\":\"修改成功\"}");
    }
}