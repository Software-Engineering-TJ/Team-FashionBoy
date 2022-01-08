package pojo.logicEntity;

/**
 * @author HJK
 */
public class UserInfo {
    private String name;

    private String sex;

    private String studentNumber;

    private String teacherNumber;

    private String phoneNumber;

    private String email;

    public UserInfo() {
    }

    public void setStudentInfo(String name,String sex,String studentNumber,String phoneNumber,String email){
        setName(name);
        setSex(sex);
        setStudentNumber(studentNumber);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public void setTeacherInfo(String name,String sex,String teacherNumber,String phoneNumber,String email){
        setName(name);
        setSex(sex);
        setStudentNumber(teacherNumber);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
