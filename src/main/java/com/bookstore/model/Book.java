package com.bookstore.model;

public class Book {
    private Long id;
    private String title;
    private Long authorId;
    private String category;
    private int publicationYear;
    private String isbn;
    private double price;
    private int stock;

    public Book(){}

    public Book(Long id, String title, Long authorId, String category, int publicationYear, String isbn, double price,int stock) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.category = category;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }
    
    public String getCategory() {
        return category;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
