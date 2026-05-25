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
        // ========== 跨域 ==========
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // ==========================
        // ✅ 接收前端查询条件（关键！）
        // ==========================
        String name = request.getParameter("name");
        String chandi = request.getParameter("chandi");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        ShuiguoxinxiDao dao = new ShuiguoxinxiDao();
        // ==========================
        // ✅ 调用条件查询（关键！！）
        // ==========================
        List<ShuiguoxinxiEntity> fruitList = dao.queryByCondition(name, chandi, minPrice, maxPrice);

        // ========== 拼接 JSON ==========
        StringBuilder json = new StringBuilder();
        json.append("{\"fruits\":[");

        for (int i = 0; i < fruitList.size(); i++) {
            ShuiguoxinxiEntity fruit = fruitList.get(i);
            json.append("{");

            json.append("\"id\":").append(fruit.getId()).append(",");
            json.append("\"addtime\":\"").append(fruit.getAddtime() == null ? "" : fruit.getAddtime()).append("\",");
            json.append("\"shuiguomingcheng\":\"").append(fruit.getShuiguomingcheng() == null ? "" : fruit.getShuiguomingcheng()).append("\",");
            json.append("\"shuiguofenlei\":\"").append(fruit.getShuiguofenlei() == null ? "" : fruit.getShuiguofenlei()).append("\",");
            json.append("\"chandi\":\"").append(fruit.getChandi() == null ? "" : fruit.getChandi()).append("\",");
            json.append("\"guige\":\"").append(fruit.getGuige() == null ? "" : fruit.getGuige()).append("\",");
            json.append("\"caizhairiqi\":\"").append(fruit.getCaizhairiqi() == null ? "" : fruit.getCaizhairiqi()).append("\",");
            json.append("\"baozhiqi\":\"").append(fruit.getBaozhiqi() == null ? "" : fruit.getBaozhiqi()).append("\",");
            json.append("\"shuiguojieshao\":\"").append(fruit.getShuiguojieshao() == null ? "" : fruit.getShuiguojieshao()).append("\",");
            json.append("\"shuiguotupian\":\"").append(fruit.getShuiguotupian() == null ? "" : fruit.getShuiguotupian()).append("\",");
            json.append("\"onelimittimes\":").append(fruit.getOnelimittimes() == null ? 0 : fruit.getOnelimittimes()).append(",");
            json.append("\"alllimittimes\":").append(fruit.getAlllimittimes() == null ? 0 : fruit.getAlllimittimes()).append(",");
            json.append("\"thumbsupnum\":").append(fruit.getThumbsupnum() == null ? 0 : fruit.getThumbsupnum()).append(",");
            json.append("\"crazilynum\":").append(fruit.getCrazilynum() == null ? 0 : fruit.getCrazilynum()).append(",");
            json.append("\"clicktime\":\"").append(fruit.getClicktime() == null ? "" : fruit.getClicktime()).append("\",");
            json.append("\"clicknum\":").append(fruit.getClicknum() == null ? 0 : fruit.getClicknum()).append(",");
            json.append("\"discussnum\":").append(fruit.getDiscussnum() == null ? 0 : fruit.getDiscussnum()).append(",");
            json.append("\"price\":").append(fruit.getPrice() == null ? 0 : fruit.getPrice()).append(",");
            json.append("\"onshelves\":").append(fruit.getOnshelves() == null ? 0 : fruit.getOnshelves()).append(",");
            json.append("\"storeupnum\":").append(fruit.getStoreupnum() == null ? 0 : fruit.getStoreupnum());

            json.append("}");
            if (i != fruitList.size() - 1) json.append(",");
        }

        json.append("]}");
        out.write(json.toString());
        out.flush();
    }

    // 处理 OPTIONS 跨域预检
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