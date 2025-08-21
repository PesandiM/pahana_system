package dto;

public class ItemDTO {
    private int id;
    private String type; // "book" or "stationery"
    private String name;
    private double price;
    private int quantity;
    private String author;
    private String isbn;
    private String manufacturer;

    public ItemDTO(String type, String name, double price, int quantity, String author, String isbn, String manufacturer) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.author = author;
        this.isbn = isbn;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
