package pojo;

/**
 * instructor类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Instructor extends User{
    private String nickname;
    private int status;

    public Instructor(){}

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", status=" + status +
                '}';
    }
}
