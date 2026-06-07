package servlet;

import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/uploadImg")
public class UploadImgServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        JSONObject result = new JSONObject();

        // 获取upload真实磁盘路径
        String uploadPath = getServletContext().getRealPath("/upload");
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) uploadDir.mkdirs();

        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> itemList = upload.parseRequest(request);
            for(FileItem item : itemList){
                if(!item.isFormField()){
                    // 获取原文件名
                    String oriName = item.getName();
                    // 重命名：时间戳+后缀，防止重名覆盖
                    String suffix = oriName.substring(oriName.lastIndexOf("."));
                    String newFileName = System.currentTimeMillis() + suffix;
                    // 写入磁盘
                    item.write(new File(uploadDir + File.separator + newFileName));
                    // 返回文件名给前端
                    result.put("code",200);
                    result.put("fileName",newFileName);
                }
            }
        }catch(Exception e){
            result.put("code",500);
            result.put("msg","图片上传失败");
            e.printStackTrace();
        }
        response.getWriter().write(result.toString());
    }
}