package pojo;

import java.util.Date;

public class PromotionEntity {
    private Integer id;
    private Date addtime;
    private String shuiguomingcheng;
    private String shuiguofenlei;
    private String chandi;
    private Double price; // 新增价格

    public PromotionEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Date getAddtime() { return addtime; }
    public void setAddtime(Date addtime) { this.addtime = addtime; }

    public String getShuiguomingcheng() { return shuiguomingcheng; }
    public void setShuiguomingcheng(String shuiguomingcheng) { this.shuiguomingcheng = shuiguomingcheng; }

    public String getShuiguofenlei() { return shuiguofenlei; }
    public void setShuiguofenlei(String shuiguofenlei) { this.shuiguofenlei = shuiguofenlei; }

    public String getChandi() { return chandi; }
    public void setChandi(String chandi) { this.chandi = chandi; }

    // 价格 get/set
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}