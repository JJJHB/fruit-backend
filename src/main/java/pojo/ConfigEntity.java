package pojo;

public class ConfigEntity {
    private Integer id;
    private String imgUrl;
    private String linkUrl;
    private Integer sortOrder;

    public ConfigEntity() {}

    public ConfigEntity(Integer id, String imgUrl, String linkUrl, Integer sortOrder) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.sortOrder = sortOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}