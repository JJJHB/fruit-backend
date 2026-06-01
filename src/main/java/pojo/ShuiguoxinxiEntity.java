package pojo;

import java.util.Date;

public class ShuiguoxinxiEntity {
    private Long id;
    private Date addtime;           // 创建时间
    private String shuiguomingcheng;// 水果名称
    private String shuiguofenlei;   // 水果分类
    private String chandi;          // 产地
    private String guige;           // 规格
    private Date caizhairiqi;       // 采摘日期
    private String baozhiqi;        // 保质期
    private String shuiguojieshao;  // 水果介绍
    private String shuiguotupian;   // 水果图片
    private Integer onelimittimes;  // 单次限购
    private Integer alllimittimes;  // 库存
    private Integer thumbsupnum;    // 赞
    private Integer crazilynum;     // 踩
    private Date clicktime;         // 最近点击时间
    private Integer clicknum;        // 点击次数
    private Integer discussnum;     // 评论数
    private Double price;           // 价格
    private Integer onshelves;      // 是否上架(1:上架，0:下架)
    private Integer storeupnum;     // 收藏数

    // 生成所有 getter/setter 方法
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Date getAddtime() {return addtime;}
    public void setAddtime(Date addtime) {this.addtime = addtime;}
    public String getShuiguomingcheng() {return shuiguomingcheng;}
    public void setShuiguomingcheng(String shuiguomingcheng) {this.shuiguomingcheng = shuiguomingcheng;}
    public String getShuiguofenlei() {return shuiguofenlei;}
    public void setShuiguofenlei(String shuiguofenlei) {this.shuiguofenlei = shuiguofenlei;}
    public String getChandi() {return chandi;}
    public void setChandi(String chandi) {this.chandi = chandi;}
    public String getGuige() {return guige;}
    public void setGuige(String guige) {this.guige = guige;}
    public Date getCaizhairiqi() {return caizhairiqi;}
    public void setCaizhairiqi(Date caizhairiqi) {this.caizhairiqi = caizhairiqi;}
    public String getBaozhiqi() {return baozhiqi;}
    public void setBaozhiqi(String baozhiqi) {this.baozhiqi = baozhiqi;}
    public String getShuiguojieshao() {return shuiguojieshao;}
    public void setShuiguojieshao(String shuiguojieshao) {this.shuiguojieshao = shuiguojieshao;}
    public String getShuiguotupian() {return shuiguotupian;}
    public void setShuiguotupian(String shuiguotupian) {this.shuiguotupian = shuiguotupian;}
    public Integer getOnelimittimes() {return onelimittimes;}
    public void setOnelimittimes(Integer onelimittimes) {this.onelimittimes = onelimittimes;}
    public Integer getAlllimittimes() {return alllimittimes;}
    public void setAlllimittimes(Integer alllimittimes) {this.alllimittimes = alllimittimes;}
    public Integer getThumbsupnum() {return thumbsupnum;}
    public void setThumbsupnum(Integer thumbsupnum) {this.thumbsupnum = thumbsupnum;}
    public Integer getCrazilynum() {return crazilynum;}
    public void setCrazilynum(Integer crazilynum) {this.crazilynum = crazilynum;}
    public Date getClicktime() {return clicktime;}
    public void setClicktime(Date clicktime) {this.clicktime = clicktime;}
    public Integer getClicknum() {return clicknum;}
    public void setClicknum(Integer clicknum) {this.clicknum = clicknum;}
    public Integer getDiscussnum() {return discussnum;}
    public void setDiscussnum(Integer discussnum) {this.discussnum = discussnum;}
    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}
    public Integer getOnshelves() {return onshelves;}
    public void setOnshelves(Integer onshelves) {this.onshelves = onshelves;}
    public Integer getStoreupnum() {return storeupnum;}
    public void setStoreupnum(Integer storeupnum) {this.storeupnum = storeupnum;}
}