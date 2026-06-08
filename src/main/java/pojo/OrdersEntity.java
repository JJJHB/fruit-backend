package pojo;

public class OrdersEntity {

    private int id;

    // 用户ID
    private long userId;

    // 收货地址ID
    private int addressId;

    // 水果ID
    private int fruitId;

    // 数量
    private int quantity;

    // 单价
    private double price;

    // 总价
    private double totalPrice;

    // 订单状态
    private String status;

    // 创建时间
    private String createTime;

    public OrdersEntity() {
    }

    public OrdersEntity(int id,
                       long userId,
                       int addressId,
                       int fruitId,
                       int quantity,
                       double price,
                       double totalPrice,
                       String status,
                       String createTime) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.fruitId = fruitId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", fruitId=" + fruitId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}