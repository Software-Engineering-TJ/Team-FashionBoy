package utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.fileupload.FileItem;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
            //提交上传请求
            ossClient.putObject(bucketName,filePath,fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭客户端连接
            ossClient.shutdown();
        }
        //文件最终存储位置
        return "https://"+bucketName+"."+endpoint+"/"+filePath;
    }

    public static InputStream downloadFile(String fileUrl){
        //获取文件在OSS中的路径
        String filePath = fileUrl.replace("https://"+bucketName+"."+endpoint+"/","");

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //获取OSS文件对象
        OSSObject ossObject = ossClient.getObject(bucketName,filePath);
        ossClient.shutdown();
        return ossObject.getObjectContent();
    }


    /**
     * oss删除文件，这里默认是bucketName桶内的文件
     * @param filePath
     */
    public static void deleteFile(String filePath){
        //创建客户端实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //删除文件
        ossClient.deleteObject(bucketName, filePath);
        //关闭客户端连接
        ossClient.shutdown();
    }

    public static String getFileType(String filename){
        if(filename.endsWith(".jpg")){
            return "image/jpg";
        }else if(filename.endsWith(".png")){
            return "image/png";
        }else if(filename.endsWith("jpeg")){
            return "image/jepg";
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
}
