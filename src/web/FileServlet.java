package web;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.google.gson.reflect.TypeToken;
import dao.impl.ExpScoreDaoImpl;
import dao.impl.ReferenceDaoImpl;
import dao.inter.ExpScoreDao;
import dao.inter.ReferenceDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import pojo.User;
//import service.Impl.FileServiceImpl;
//import service.inter.FileService;
import service.Impl.InstructorServiceImpl;
import service.Impl.StudentServiceImpl;
import service.inter.InstructorService;
import service.inter.StudentService;
import utils.OSSUtils;
import utils.RequestJsonUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * FileServlet类的描述：
 * 提供上传和下载文件的服务
 * @author 黄金坤（HJK）
 * @since 2021/11/11  13:44
 */

public class FileServlet extends BaseServlet {

    private StudentService studentService = new StudentServiceImpl();
    private InstructorService instructorService = new InstructorServiceImpl();

    /**
     * 文件上传oss（老师和学生均用此接口）
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
                        /*
                        文件表单项
                         */
                        //上传的文件名
                        filename = fileItem.getName();
                        System.out.println("文件：" + filename);
                        //先放着，等待文件路径确定后再写到磁盘
                        fileItemToBeStore = fileItem;
                    }
                }
                //6.文件内容为空，提醒用户重新上传
                Map<String, Object> map = new HashMap<>();
                if (fileItemToBeStore.getSize() == 0) {
                    //文件内容为空，提醒用户重新上传！
                    map.put("result", 0);
                    resp.getWriter().write(gson.toJson(map));
                    return;
                }
                //7.将文件写入oss
                if(expname == null){
                    //本次是老师上传参考资料
                    String filePath = courseID + "/" + classID + "/" + userNumber + "/" + filename;
                    String fileUrl = OSSUtils.uploadFile(filePath, fileItemToBeStore);
                    //记录老师提交的资料信息：考虑重复提交同名文件（不用再次纪录）和提交新文件（插入一条记录）
                    if(!instructorService.checkReference(fileUrl)){
                        instructorService.recordCommit(courseID,classID,userNumber,fileUrl);
                    }
                }else{
                    //本次是学生上传实验报告
                    String filePath = courseID + "/" + classID + "/" + expname + "/" + userNumber + "/" + filename;
                    String fileUrl = OSSUtils.uploadFile(filePath, fileItemToBeStore);
                    //将实验提交记录更新到数据库
                    studentService.recordCommit(courseID,classID,expname,userNumber,fileUrl);
                }
                //文件提交成功
                map.put("result", 1);
                resp.getWriter().write(gson.toJson(map));
            }catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载oss文件
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("开始下载oss文件");
        //1.获取文件url
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String fileUrl = reqObject.get("fileUrl");

        //2.获取文件名
        List<String> stringList = List.of(fileUrl.split("/"));
        String filename = stringList.get(stringList.size()-1);
        //3.文件类型
        String fileType = OSSUtils.getFileType(filename);
        //4.通过响应头告诉客户端返回的文件类型
        resp.setContentType(fileType);
        /*
        5.还要告诉客户端这是用来下载的.attachment表示一个附件，filename后面跟完整的文件名.URLEncoder.encode使得中文可以在IE和谷歌中存在
        火狐需要用base64编码
         */
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        //7.文件内容
        String filePath = fileUrl.replace("https://"+OSSUtils.bucketName+"."+OSSUtils.endpoint+"/","");
        OSS ossClient = new OSSClientBuilder().build(OSSUtils.endpoint, OSSUtils.accessKeyId, OSSUtils.accessKeySecret);
        //获取OSS文件对象
        OSSObject ossObject = ossClient.getObject(OSSUtils.bucketName,filePath);
        InputStream inputStream = ossObject.getObjectContent();
        OutputStream outputStream = resp.getOutputStream();  //引用输出流
        //8.文件流传给客户端
        IOUtils.copy(inputStream, outputStream);   //将数据流复制到输出流，输出给客户端
        //关闭流
        inputStream.close();
        outputStream.close();
        ossClient.shutdown();
    }

    /**
     * 删除文件
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String reqJson = RequestJsonUtils.getJson(req);
        Map<String, String> reqObject = gson.fromJson(reqJson, new TypeToken<Map<String, String>>() {
        }.getType());

        String fileUrl = reqObject.get("fileUrl");
        //删除OSS中的文件
        OSSUtils.deleteFile(fileUrl);
        //删除文件在数据库中的记录,为了方便可以直接在expscore和reference两张表都执行删除操作
        instructorService.deleteReference(fileUrl);
        studentService.deleteCommit(fileUrl);
    }
}


