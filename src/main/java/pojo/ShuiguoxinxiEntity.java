package pojo;

public class ShuiguoxinxiEntity {
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer categoryId;
    private String picture;
    private String detail;
    private Integer clicknum;
    private String categoryName;

    // 无参构造
    public ShuiguoxinxiEntity() {}

    // Getter & Setter
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Integer getClicknum() {
        return clicknum;
    }
    public void setClicknum(Integer clicknum) {
        this.clicknum = clicknum;
    }
}