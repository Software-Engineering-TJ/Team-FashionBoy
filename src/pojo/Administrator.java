package pojo;

/**
 * administrator类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Administrator extends User{
    private String adminNumber;

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public Administrator(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSex() {
        return 1; //应该用不到，默认男
    }

    @Override
    public void setSex(int sex) {

    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {

    }

    @Override
    public int getStatus() {
        return 1; //默认已经激活
    }

    @Override
    public void setStatus(int status) {

    }

    @Override
    public String getStudentNumber() {
        return null;
    }

    @Override
    public void setStudentNumber(String studentNumber) {

    }

    @Override
    public String getInstructorNumber() {
        return null;
    }

    @Override
    public void setInstructorNumber(String phoneNumber) {

    }

    @Override
    public String toString() {
        return "Administrator{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
