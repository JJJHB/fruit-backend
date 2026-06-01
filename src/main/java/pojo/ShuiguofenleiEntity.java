package pojo;

import java.util.Date;

public class ShuiguofenleiEntity {
    // 对应数据库的 id
    private Long id;
    // 对应数据库的 addtime
    private Date addtime;
    // 对应数据库的 shuiguofenlei
    private String shuiguofenlei;

    // 无参构造（建议加上）
    public ShuiguofenleiEntity() {
    }

    // 全参构造（可选，方便创建对象）
    public ShuiguofenleiEntity(Long id, Date addtime, String shuiguofenlei) {
        this.id = id;
        this.addtime = addtime;
        this.shuiguofenlei = shuiguofenlei;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getShuiguofenlei() {
        return shuiguofenlei;
    }

    public void setShuiguofenlei(String shuiguofenlei) {
        this.shuiguofenlei = shuiguofenlei;
    }

    // 可选：toString 方法，方便打印日志
    @Override
    public String toString() {
        return "ShuiguofenleiEntity{" +
                "id=" + id +
                ", addtime=" + addtime +
                ", shuiguofenlei='" + shuiguofenlei + '\'' +
                '}';
    }
}