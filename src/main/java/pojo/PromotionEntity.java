package pojo;
import java.util.Date;

public class PromotionEntity {
    private Integer id;
    private Integer fruitId;
    private String title;
    private Double discountPrice;
    private Date startTime;
    private Date endTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getFruitId() { return fruitId; }
    public void setFruitId(Integer fruitId) { this.fruitId = fruitId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(Double discountPrice) { this.discountPrice = discountPrice; }

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
}