package pojo;

/**
 * student类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Student extends User{
    private int sex;
    private String phoneNumber;
    private int status;
    private String studentNumber;

    public Student(){}

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String getUserNumber() {
        return studentNumber;
    }

    @Override
    public String getInstructorNumber() {
        return null;
    }

    @Override
    public void setInstructorNumber(String phoneNumber) {
    }

    @Override
    public String getAdminNumber() {
        return null;
    }

    @Override
    public void setAdminNumber(String adminNumber) {
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }
}



