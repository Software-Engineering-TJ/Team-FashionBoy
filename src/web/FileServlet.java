package web;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import pojo.User;
//import service.Impl.FileServiceImpl;
//import service.inter.FileService;
import utils.RequestJsonUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * FileServlet类的描述：
 * 提供上传和下载文件的服务
 * @author 黄金坤（HJK）
 * @since 2021/11/11  13:44
 */

public class FileServlet extends BaseServlet {

    /**
     * 根据courseID、classID、userNumber、filename获取用户提交的文件
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取文件名
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        //文件结构：课程/班级/用户/文件
        String courseID = reqObject.get("courseID");
        String classID = reqObject.get("classID");
        String userNumber = reqObject.get("userNumber");
        String filename = reqObject.get("filename");

        //2.读取要下载的文件的内容（通过ServletContext对象）
        ServletContext servletContext = getServletContext();
//        InputStream resourceAsStream = servletContext.getResourceAsStream("F:\\javaProjects\\UserFiles\\cloud\\"+filename); //将数据按流数据获取
//        InputStream resourceAsStream = servletContext.getResourceAsStream("/static/pictures/"+filename); //将数据按流数据获取
        InputStream resourceAsStream = servletContext.getResourceAsStream("/WEB-INF/UserFiles/" + courseID + "/" + classID + "/" + userNumber + "/" + filename); //将数据按流数据获取
        //3.获取要下载的文件类型
//        String filetype = servletContext.getMimeType("F:\\javaProjects\\UserFiles\\cloud\\"+filename);
//        String filetype = servletContext.getMimeType("/static/pictures/"+filename);
        String filetype = servletContext.getMimeType("/WEB-INF/UserFiles/" + courseID + "/" + classID + "/" + userNumber + "/" + filename);
//        System.out.println(filetype);
        //4.通过响应头告诉客户端返回的文件类型
        resp.setContentType(filetype);
        //5.还要告诉客户端这是用来下载的.attachment表示一个附件，filename后面跟完整的文件名.URLEncoder.encode使得中文可以在IE和谷歌中存在
        //火狐需要用base64编码
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        //4.文件内容要给客户端
        OutputStream outputStream = resp.getOutputStream();  //引用输出流
        IOUtils.copy(resourceAsStream, outputStream);   //将数据流复制到输出流，输出给客户端
    }

    /**
     * 上传一个文件到路径：课程/班级/用户
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("开始上传文件");
        //文件传输时使用的是分段二进制编码，所以不能在使用req.getPara......的方式获取键值对。
        //获取数据流,直观体会传递的数据
//        ServletInputStream inputStream = req.getInputStream();
//        byte[] bytes = new byte[100000];
//        int count = inputStream.read(bytes);
//        System.out.printf(new String(bytes,0,count));
        //所以，我们需要解析数据流
        //1.先判断是否是多段数据,不是多段数据的话一定不是文件上传，后续操作无法展开
        if (ServletFileUpload.isMultipartContent(req)) {
            //2.创建FileItemFactory实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //3.用于解析上传数据的工具类ServletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            servletFileUpload.setHeaderEncoding("UTF-8"); //设置UTF-8编码
            //4.解析数据，得到各个表单项FileItem
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                String courseID = null, classID = null, expname = null, userNumber = null, filename = null;  //文件想要存储的位置
                FileItem fileItemToBeStore = null;
                //5.判断每个表单项是否是文件
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        //获取存储位置信息
                        String name = fileItem.getFieldName();   //普通表单项名字
                        if (name.equals("courseID")) {
                            courseID = fileItem.getString("UTF-8"); //参数UTF-8解决乱码问题
                        } else if (name.equals("classID")) {
                            classID = fileItem.getString("UTF-8");
                        } else if (name.equals("expname")) {
                            expname = fileItem.getString("UTF-8");
                        } else {
                            userNumber = fileItem.getString("UTF-8");
                        }
                        //普通表单项
//                        System.out.println("表单项的name属性值：" + fileItem.getFieldName());
//                        System.out.println("表单项的value属性值：" + fileItem.getString("UTF-8"));
                    } else {
                        //文件表单项
                        filename = fileItem.getName();//上传的文件名
                        System.out.println("文件：" + filename);
                        fileItemToBeStore = fileItem; //先放着，等待文件路径确定后再写到磁盘
                    }
                }
                //6.将文件写入磁盘
                try {
                    //使用Path类方便获取不同电脑下的当前文件所在的路径，随环境而变
                    Path path = Paths.get(req.getServletContext().getRealPath(""));
                    //定位到“整个项目”所在的路径
                    path = path.getParent().getParent().getParent();
                    //定位到文件应存放的路径
                    Path fileDirectory = Paths.get(path.toString(), "web/WEB-INF/files/" + courseID + "/" + classID + "/" + expname + "/" + userNumber);
//                            String filePath = "F:\\javaProjects\\WedProjectTest\\web\\WEB-INF\\UserFiles\\"+userNumber;
//                            System.out.println(req.getRequestURL());
                    File file = new File(fileDirectory.toString());
                    if (!file.isDirectory()) {
                        file.mkdirs(); //这个方法可以将路径中确实的父类目录均创建出来
                    }
                    Path filePath = Paths.get(fileDirectory.toString(), filename);
                    fileItemToBeStore.write(new File(filePath.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 老师用于上传文件
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void instructorUploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("老师开始上传文件");
        //1.先判断是否是多段数据,不是多段数据的话一定不是文件上传，后续操作无法展开
        if (ServletFileUpload.isMultipartContent(req)) {
            //2.创建FileItemFactory实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //3.用于解析上传数据的工具类ServletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            servletFileUpload.setHeaderEncoding("UTF-8"); //设置UTF-8编码
            //4.解析数据，得到各个表单项FileItem
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                String courseID = null, classID = null, userNumber = null, filename = null;  //文件想要存储的位置
                FileItem fileItemToBeStore = null;
                //5.判断每个表单项是否是文件
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        //获取存储位置信息
                        String name = fileItem.getFieldName();   //普通表单项名字
                        if (name.equals("courseID")) {
                            courseID = fileItem.getString("UTF-8"); //参数UTF-8解决乱码问题
                        } else if (name.equals("classID")) {
                            classID = fileItem.getString("UTF-8");
                        }else {
                            userNumber = fileItem.getString("UTF-8");
                        }
                    } else {
                        //文件表单项
                        filename = fileItem.getName();//上传的文件名
                        System.out.println("文件：" + filename);
                        fileItemToBeStore = fileItem; //先放着，等待文件路径确定后再写到磁盘
                    }
                }
                //6.将文件写入磁盘
                try {
                    //使用Path类方便获取不同电脑下的当前文件所在的路径，随环境而变
                    Path path = Paths.get(req.getServletContext().getRealPath(""));
                    //定位到“整个项目”所在的路径
                    path = path.getParent().getParent().getParent();
                    //定位到文件应存放的路径
                    Path fileDirectory = Paths.get(path.toString(), "web/WEB-INF/files/" + courseID + "/" + classID + "/" + userNumber);
                    File file = new File(fileDirectory.toString());
                    if (!file.isDirectory()) {
                        file.mkdirs(); //这个方法可以将路径中确实的父类目录均创建出来
                    }
                    Path filePath = Paths.get(fileDirectory.toString(), filename);
                    fileItemToBeStore.write(new File(filePath.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }
}
