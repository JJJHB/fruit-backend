package pojo;

import java.io.Serializable;
import java.util.Date;

public class NewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date addtime;
    private String title;
    private String introduction;
    private String typename;
    private String name;
    private String headportrait;
    private Integer clicknum;
    private Date clicktime;
    private Integer thumbsupnum;
    private Integer crazilynum;
    private Integer storeupnum;
    private String picture;
    private String content;

    public NewsEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getAddtime() { return addtime; }
    public void setAddtime(Date addtime) { this.addtime = addtime; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }

    public String getTypename() { return typename; }
    public void setTypename(String typename) { this.typename = typename; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHeadportrait() { return headportrait; }
    public void setHeadportrait(String headportrait) { this.headportrait = headportrait; }

    public Integer getClicknum() { return clicknum; }
    public void setClicknum(Integer clicknum) { this.clicknum = clicknum; }

    public Date getClicktime() { return clicktime; }
    public void setClicktime(Date clicktime) { this.clicktime = clicktime; }

    public Integer getThumbsupnum() { return thumbsupnum; }
    public void setThumbsupnum(Integer thumbsupnum) { this.thumbsupnum = thumbsupnum; }

    public Integer getCrazilynum() { return crazilynum; }
    public void setCrazilynum(Integer crazilynum) { this.crazilynum = crazilynum; }

    public Integer getStoreupnum() { return storeupnum; }
    public void setStoreupnum(Integer storeupnum) { this.storeupnum = storeupnum; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}