package pojo;
// hello
import java.util.Date;

public class UsersEntity {
    private Long id;          // 用户ID
    private String username;  // 用户账号
    private String password;  // 密码
    private String image;     // 头像
    private String role;      // 用户类型
    private Date addtime;     // 注册时间

    // 无参构造
    public UsersEntity() {}

    // 全参构造
    public UsersEntity(Long id, String username, String password, String image, String role, Date addtime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.image = image;
        this.role = role;
        this.addtime = addtime;
    }

    // getter 和 setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getAddtime() { return addtime; }
    public void setAddtime(Date addtime) { this.addtime = addtime; }
}