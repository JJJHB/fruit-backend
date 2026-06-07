package servlet;

import dao.ShuiguoxinxiDao;
import pojo.ShuiguoxinxiEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/fruitQueryList")
public class FruitQueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 跨域配置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // 接收前端参数
        String name = request.getParameter("name");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        ShuiguoxinxiDao dao = new ShuiguoxinxiDao();
        // ✅ 修正参数顺序：和 DAO 方法形参完全一致
        List<ShuiguoxinxiEntity> fruitList = dao.queryByCondition(name, minPrice, maxPrice);

        // 拼接JSON
        StringBuilder json = new StringBuilder();
        json.append("{\"fruits\":[");

        for (int i = 0; i < fruitList.size(); i++) {
            ShuiguoxinxiEntity fruit = fruitList.get(i);
            json.append("{");

            json.append("\"id\":").append(fruit.getId()).append(",");
            json.append("\"name\":\"").append(fruit.getName() == null ? "" : fruit.getName()).append("\",");
            json.append("\"price\":").append(fruit.getPrice() == null ? 0 : fruit.getPrice()).append(",");
            json.append("\"stock\":").append(fruit.getStock() == null ? 0 : fruit.getStock()).append(",");
            json.append("\"categoryId\":").append(fruit.getCategoryId() == null ? 0 : fruit.getCategoryId()).append(",");
            json.append("\"picture\":\"").append(fruit.getPicture() == null ? "" : fruit.getPicture()).append("\",");
            json.append("\"detail\":\"").append(fruit.getDetail() == null ? "" : fruit.getDetail()).append("\",");
            json.append("\"clicknum\":").append(fruit.getClicknum() == null ? 0 : fruit.getClicknum()).append(",");
            json.append("\"categoryName\":\"").append(fruit.getCategoryName()==null?"":fruit.getCategoryName()).append("\"");
            
            json.append("}");
            if (i != fruitList.size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");
        out.write(json.toString());
        out.flush();
        out.close();
    }

    // 跨域预检
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}