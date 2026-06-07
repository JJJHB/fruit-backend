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

@WebServlet("/fruitAdd")
public class FruitAddServlet extends HttpServlet {

    // 先把 DAO 实例化好，避免每次请求都 new
    private final ShuiguofenleiDao fenleiDao = new ShuiguofenleiDao();
    private final ShuiguoxinxiDao fruitDao = new ShuiguoxinxiDao();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        // 读取请求体
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // 解析 JSON
        ShuiguoxinxiEntity fruit;
        try {
            fruit = JSON.parseObject(sb.toString(), ShuiguoxinxiEntity.class);
        } catch (Exception e) {
            response.getWriter().write("{\"code\":500,\"msg\":\"请求数据格式错误\"}");
            return;
        }

        // ========== 新增必填字段校验 ==========
        // 1. 分类ID校验
        Integer cid = fruit.getCategoryId();
        if (cid == null || cid <= 0) {
            response.getWriter().write("{\"code\":500,\"msg\":\"分类ID不能为空或小于等于0\"}");
            return;
        }

        // 2. 水果名称校验
        String name = fruit.getName();
        if (name == null || name.trim().isEmpty()) {
            response.getWriter().write("{\"code\":500,\"msg\":\"水果名称不能为空\"}");
            return;
        }

        // 3. 价格校验
        Double price = fruit.getPrice();
        if (price == null || price < 0) {
            response.getWriter().write("{\"code\":500,\"msg\":\"价格不能为空且不能为负数\"}");
            return;
        }

        // 4. 库存校验
        Integer stock = fruit.getStock();
        if (stock == null || stock < 0) {
            response.getWriter().write("{\"code\":500,\"msg\":\"库存不能为空且不能为负数\"}");
            return;
        }

        // 校验分类ID是否存在
        Set<Integer> validCategoryIds = fenleiDao.getAllCategoryIds();
        if (!validCategoryIds.contains(cid)) {
            response.getWriter().write("{\"code\":500,\"msg\":\"分类ID不存在，请使用数据表中的有效分类\"}");
            return;
        }

        // 空字段赋默认值
        if (fruit.getClicknum() == null) {
            fruit.setClicknum(0);
        }
        if (fruit.getPicture() == null) {
            fruit.setPicture("");
        }
        if (fruit.getDetail() == null) {
            fruit.setDetail("");
        }

        // 执行新增
        fruitDao.addFruit(fruit);
        response.getWriter().write("{\"code\":200,\"msg\":\"新增成功\"}");
    }
}