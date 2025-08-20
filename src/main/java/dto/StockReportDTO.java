package dto;

public class StockReportDTO {
    private int itemId;
    private String itemName;
    private String itemType;
    private int quantity;
    private double price;
    //private int soldUnits;

    public StockReportDTO(int itemId, String itemName, String itemType, int quantity, double price, int soldUnits) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.price = price;
        //this.soldUnits = soldUnits;
    }

    public StockReportDTO() {

    }

    public StockReportDTO(int itemId, String itemName, String itemType, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public int getSoldUnits() {
//        return soldUnits;
//    }
//
//    public void setSoldUnits(int soldUnits) {
//        this.soldUnits = soldUnits;
//    }
}
