package model;

public class Book extends Item{
    private String author;
    private String isbn;

    public Book(int id, String name, double price, int quantity, String author, String isbn) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = "book";
        this.author = author;
        this.isbn = isbn;
    }
    @Override
    public void applyDiscount() {
        this.price *= 0.9; // 10% discount for books
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
}
