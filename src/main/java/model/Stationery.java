package model;

public class Stationery extends Item{
    private String manufacturer;

    public Stationery(int id, String name, double price, int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = "stationery";
        this.manufacturer = manufacturer;
    }
    @Override
    public void applyDiscount() {
        if (quantity > 50) this.price *= 0.85; // 15% bulk discount
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
