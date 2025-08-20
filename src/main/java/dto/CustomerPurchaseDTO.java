package dto;

public class CustomerPurchaseDTO {
    private int customerId;
    private String customerName;
    private int billId;
    private String billDate;
    private String itemName;
    private String itemType;
    private int quantity;
    private double itemPrice;
    private double itemTotal;

    public CustomerPurchaseDTO(int customerId, String customerName, int billId, String billDate,
                               String itemName, String itemType, int quantity, double itemPrice, double itemTotal) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.billId = billId;
        this.billDate = billDate;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.itemTotal = itemTotal;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }
}
