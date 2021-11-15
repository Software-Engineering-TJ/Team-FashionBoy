package pojo;

/**
 * instructor类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Instructor extends User{
    private String instructorNumber;
    private int sex;
    private String phoneNumber;
    private int status;


    public Instructor() {}

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

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    @Override
    public String getUserNumber() {
        return instructorNumber;
    }

    @Override
    public String getStudentNumber() {
        return null;
    }

    @Override
    public void setStudentNumber(String studentNumber) {

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
        return "Instructor{" +
                "instructorNumber='" + instructorNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
