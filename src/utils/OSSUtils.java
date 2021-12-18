package utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import org.apache.commons.fileupload.FileItem;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用阿里云的OSS服务进行文件的管理
 * @author HJK
 * @since 2021/12/18 0:02
 */
public class OSSUtils {

    public static String endpoint = "oss-cn-shenzhen.aliyuncs.com";

    public static String accessKeyId = "LTAI5tKNMPE6vV8qhXNyV8ym";

    public static String accessKeySecret = "MNlnQopuHj7Gk4D7YuaNf702ExoibM";

    public static String bucketName = "hjk-files";

    /**
     * 上传文件到OSS
     * @param filePath 想要存储的文件路径
     * @param fileItem http传来的文件项
     * @return 文件存储对应的OSS外网访问路径
     */
    public static String uploadFile(String filePath, FileItem fileItem){
        //创建客户端实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取文件流
        try {
            InputStream fileInputStream = fileItem.getInputStream();
            //设置上传文件的元数据
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(getFileType(fileItem.getName()));
            //设置文件下载时的编码
            meta.setContentEncoding("utf-8");
            //提交上传请求
            ossClient.putObject(bucketName,filePath,fileInputStream,meta);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭客户端连接
            ossClient.shutdown();
        }
        //文件最终存储位置
        return "https://"+bucketName+"."+endpoint+"/"+filePath;
    }

    /**
     * oss删除文件，这里默认是bucketName桶内的文件
     * @param fileUrl
     */
    public static void deleteFile(String fileUrl){
        //获取文件在OSS中的路径
        String filePath = fileUrl.replace("https://"+bucketName+"."+endpoint+"/","");
        //创建客户端实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //删除文件
        ossClient.deleteObject(bucketName, filePath);
        //关闭客户端连接
        ossClient.shutdown();
    }

    /**
     * 获取文件类型
     * @param filename
     * @return
     */
    public static String getFileType(String filename){
        if(filename.endsWith(".jpg")||filename.endsWith(".png")||(filename.endsWith("jpeg"))){
            return "image/jpg";
        }else if(filename.endsWith(".bmp")){
            return "image/bmp";
        }else if(filename.endsWith(".gif")){
            return "image/gif";
        }else if(filename.endsWith(".doc")||(filename.endsWith(".docx"))){
            return "application/msword";
        }else if(filename.endsWith(".pdf")){
            return "application/pdf";
        }else if(filename.endsWith(".txt")){
            return "text/plain";
        }else if(filename.endsWith(".pptx")){
            return "application/vnd.ms-powerpoint";
        }else if(filename.endsWith(".xml")){
            return "text/xml";
        }
        //其余一律视为为图片
        return "image/jpg";
    }

    /**
     * 获取OSS文件元信息：文件大小、最后修改日期等
     * @param fileUrl
     * @return
     */
    public static Map<String,String> getObjectMeta(String fileUrl){
        String filePath = fileUrl.replace("https://"+bucketName+"."+endpoint+"/","");
        //创建客户端实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取OSS文件元数据
        SimplifiedObjectMeta objectMeta = ossClient.getSimplifiedObjectMeta(bucketName,filePath);
        long size = objectMeta.getSize();
        Date date = objectMeta.getLastModified();
        //最近一次修改日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        //文件大小
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSize = "";
        if (size < 1024) {
            fileSize = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSize = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSize = df.format((double) size / 1048576) + "M";
        } else {
            fileSize = df.format((double) size / 1073741824) + "G";
        }
        //文件名
        List<String> stringList = List.of(filePath.split("/"));
        String filename = stringList.get(stringList.size()-1);

        Map<String, String> map = new HashMap<>();
        map.put("fileSize", fileSize);
        map.put("upLoadDate",dateStr);
        map.put("fileName",filename);

        ossClient.shutdown();
        return map;
    }

}


