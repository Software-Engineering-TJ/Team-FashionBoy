package dao.impl;

import dao.inter.ReferenceDao;
import pojo.Reference;

import java.util.List;


public class ReferenceDaoImpl extends BaseDao implements ReferenceDao {
    @Override
    public Reference QueryReferenceByfileUrl(String fileUrl) {
        String sql = "select * from reference where fileUrl = ?";
        return queryForOne(Reference.class,sql,fileUrl);
    }

    @Override
    public int InsertReference(String courseID, String classID, String instructorNumber, String fileUrl) {
        String sql = "insert into reference(`courseID`,`classID`,`instructorNumber`,`fileUrl`) values(?,?,?,?)";
        return update(sql,courseID,classID,instructorNumber,fileUrl);
    }

    @Override
    public int DeleteReferenceByFileUrl(String fileUrl) {
        String sql = "delete from `reference` where (`fileUrl` = ?);";
        return update(sql,fileUrl);
    }

    @Override
    public List<Reference> QueryReferencesByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select * from reference where courseID = ? and classID = ?";
        return queryForList(Reference.class,sql,courseID,classID);
    }
}
