package dao.inter;
//
//import com.aliyun.oss.model.LiveChannelListing;
import pojo.Attend;

import java.util.List;

public interface AttendDao {
    //查看某一次具体的考勤
    Attend QueryAttendByCourseIDAndClassIDAndTitle(String courseID, String classID,String title);
    //查找某一班级已经发布的考勤
    List<Attend> QueryAttendsByCourseIDAndClassID(String courseID, String classID);
    //添加考勤
    int InsertAttend(String courseID, String classID, String attendName, String startTime, String endTime);

}
