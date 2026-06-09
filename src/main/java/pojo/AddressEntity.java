package pojo;

public class AddressEntity {
    private int id;
    private long userId;
    private String recipient;
    private String phone;
    private String detail;
    private int isDefault; // 0=否 1=是

    // --- getter & setter ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public int getIsDefault() { return isDefault; }
    public void setIsDefault(int isDefault) { this.isDefault = isDefault; }
}