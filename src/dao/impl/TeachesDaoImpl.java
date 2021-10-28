package dao.impl;

import dao.inter.TeachesDao;
import pojo.Teaches;

/**
 * TechesDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  23:04
 */

public class TeachesDaoImpl extends BaseDao implements TeachesDao{
    @Override
    public Teaches QueryOneTeachesByCourseIDAndClassID(String courseID, String classID) {
        return null;
    }
}
