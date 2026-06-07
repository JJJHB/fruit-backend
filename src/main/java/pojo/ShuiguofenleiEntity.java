package pojo;

public class ShuiguofenleiEntity {
    // 对应数据库 id (int)
    private Integer id;
    // 对应数据库 name (varchar(50)) —— 分类名称
    private String name;
    // 对应数据库 description (varchar(255)) —— 分类描述
    private String description;

    // 无参构造（必须保留）
    public ShuiguofenleiEntity() {
    }

    // 全参构造（可选，方便创建对象）
    public ShuiguofenleiEntity(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getter & Setter（与数据库字段一一对应）
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // 可选：toString 方法，方便打印日志
    @Override
    public String toString() {
        return "ShuiguofenleiEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}