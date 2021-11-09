package pojo.logicEntity;

/**
 * sectionInformation类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/28  23:49
 */

public class SectionInformation {
        private String title;
        private String courseID;
        private String classID;
        private String day;
        private String time;
        private String chargingTeacher;
        private String teacher;
        private String duty;

        public String getCourseID() {
                return courseID;
        }

        public void setCourseID(String courseID) {
                this.courseID = courseID;
        }

        public SectionInformation() {}

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getClassID() {
                return classID;
        }

        public void setClassID(String classID) {
                this.classID = classID;
        }

        public String getDay() {
                return day;
        }

        public void setDay(String day) {
                this.day = day;
        }

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public String getChargingTeacher() {
                return chargingTeacher;
        }

        public void setChargingTeacher(String chargingTeacher) {
                this.chargingTeacher = chargingTeacher;
        }

        public String getTeacher() {
                return teacher;
        }

        public void setTeacher(String teacher) {
                this.teacher = teacher;
        }

        public String getDuty() {
                return duty;
        }

        public void setDuty(String duty) {
                this.duty = duty;
        }

        @Override
        public String toString() {
                return "SectionInformation{" +
                        "title='" + title + '\'' +
                        ", classID='" + classID + '\'' +
                        ", day='" + day + '\'' +
                        ", time='" + time + '\'' +
                        ", chargingTeacher='" + chargingTeacher + '\'' +
                        ", teacher='" + teacher + '\'' +
                        ", duty='" + duty + '\'' +
                        '}';
        }
}

