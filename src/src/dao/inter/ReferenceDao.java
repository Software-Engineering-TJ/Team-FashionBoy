package dao.inter;


import pojo.Reference;

import java.util.List;

public interface ReferenceDao {
    /**
     * 根据fileUrl查找参考资料，用于判断参考资料是否已存在
     * @param fileUrl
     * @return
     */
    Reference QueryReferenceByfileUrl(String fileUrl);

    List<Reference> QueryReferencesByCourseIDAndClassID(String courseID, String classID);

    int InsertReference(String courseID,String classID,String instructorNumber,String fileUrl);

    int DeleteReferenceByFileUrl(String fileUrl);
}
