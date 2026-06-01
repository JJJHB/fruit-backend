package servlet;

import com.alibaba.fastjson.JSON;
import dao.NewsDao;
import pojo.NewsEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private NewsDao newsDao = new NewsDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        
        // 跨域设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                executeList(request, response);
                break;
            case "add":
                executeAdd(request, response);
                break;
            case "update":
                executeUpdate(request, response);
                break;
            case "delete":
                executeDelete(request, response);
                break;
            case "batchDelete":
                executeBatchDelete(request, response);
                break;
        }
    }

    private void executeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        List<NewsEntity> list = newsDao.queryNews(title);
        response.getWriter().write(JSON.toJSONString(list));
    }

    // ✨ 新增：处理添加公告逻辑（仅限第一版字段）
    private void executeAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        Map<String, Object> resultMap = new HashMap<>();
        NewsEntity news = new NewsEntity();
        news.setTitle(title);
        news.setContent(content);

        boolean success = newsDao.addNews(news);
        if (success) {
            resultMap.put("code", 200);
            resultMap.put("msg", "添加成功");
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "添加失败");
        }
        response.getWriter().write(JSON.toJSONString(resultMap));
    }

    // ✨ 新增：处理修改公告逻辑（仅限第一版字段）
    private void executeUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Map<String, Object> resultMap = new HashMap<>();
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            Long id = Long.parseLong(idStr);
            NewsEntity news = new NewsEntity();
            news.setId(id);
            news.setTitle(title);
            news.setContent(content);

            boolean success = newsDao.updateNews(news);
            if (success) {
                resultMap.put("code", 200);
                resultMap.put("msg", "修改成功");
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "修改失败");
            }
        } else {
            resultMap.put("code", 400);
            resultMap.put("msg", "请求参数错误，缺少必要参数 id");
        }
        response.getWriter().write(JSON.toJSONString(resultMap));
    }

    private void executeDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        Map<String, Object> resultMap = new HashMap<>();
        
        if (idStr != null && !idStr.trim().isEmpty()) {
            Long id = Long.parseLong(idStr);
            boolean success = newsDao.deleteNewsById(id);
            if (success) {
                resultMap.put("code", 200);
                resultMap.put("msg", "删除成功");
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "删除失败");
            }
        } else {
            resultMap.put("code", 400);
            resultMap.put("msg", "请求参数错误，缺少必要参数 id");
        }
        response.getWriter().write(JSON.toJSONString(resultMap));
    }

    private void executeBatchDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ids = request.getParameter("ids");
        Map<String, Object> resultMap = new HashMap<>();
        boolean success = newsDao.deleteBatch(ids);
        if (success) {
            resultMap.put("code", 200);
            resultMap.put("msg", "批量删除成功");
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "批量删除失败");
        }
        response.getWriter().write(JSON.toJSONString(resultMap));
    }
}