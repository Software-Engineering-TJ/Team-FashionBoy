package pojo;

/**
 * administrator类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Administrator extends User{
    private String name;

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

    public String getNickname() {
        return name;
    }

    public void setNickname(String name) {
        this.name = name;
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
