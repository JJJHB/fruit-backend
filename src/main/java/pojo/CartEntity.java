package pojo;

public class CartEntity {

    private Integer id;
    private Long userId;
    private Integer fruitId;
    private Integer quantity;

    public CartEntity() {
    }

    public CartEntity(Integer id, Long userId, Integer fruitId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.fruitId = fruitId;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFruitId() {
        return fruitId;
    }

    public void setFruitId(Integer fruitId) {
        this.fruitId = fruitId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}