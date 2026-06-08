package pojo;

import java.math.BigDecimal;

public class YonghuEntity {
    private Long id;           // 用户ID
    private String username;   // 用户账号
    private String password;   // 密码
    private String sex;        // 性别
    private String phone;      // 手机号
    private String picture;    // 头像
    private BigDecimal money;  // 余额

    // 无参构造
    public YonghuEntity() {}

    // 全参构造
    public YonghuEntity(Long id, String username, String password, String sex, String phone, String picture, BigDecimal money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.picture = picture;
        this.money = money;
    }

    // getter 和 setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public BigDecimal getMoney() { return money; }
    public void setMoney(BigDecimal money) { this.money = money; }
}