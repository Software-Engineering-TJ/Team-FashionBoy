package pojo;

/**
 * User类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/20  13:54
 */

public abstract class User {
    protected String email;
    protected String password;
    protected String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public abstract int getSex();

    public abstract void setSex(int sex) ;

    public abstract String getPhoneNumber();

    public abstract void setPhoneNumber(String phoneNumber);

    public abstract int getStatus();

    public abstract void setStatus(int status);

    public abstract String getStudentNumber();

    public abstract void setStudentNumber(String studentNumber);

    public abstract String getInstructorNumber();

    public abstract void setInstructorNumber(String phoneNumber);

    public abstract String getAdminNumber();

    public abstract void setAdminNumber(String adminNumber) ;
    //服务于UserServlet中的getUserInfo()
    public abstract String getUserNumber();
}
