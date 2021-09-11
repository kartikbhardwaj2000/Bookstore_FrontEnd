package com.example.bookstore.HomePage.RecyclerView;

public class BookItemModel {
    private String bookName;
    private String id;
    private String author;
    private double price;
    private String condition;
    private String photoUri[];
    private int quantity;
    private String listedDate;
    private String description;
    private String level;
    private int edition;
    private String sellerId;
    private String sellerName;

    public BookItemModel(String bookName, String id, String author, double price, String condition, String[] photoUri, int quantity, String listedDate, String description, String level, int edition, String sellerName,String sellerId) {
        this.bookName = bookName;
        this.id = id;
        this.author = author;
        this.price = price;
        this.condition = condition;
        this.photoUri = photoUri;
        this.quantity = quantity;
        this.listedDate = listedDate;
        this.description = description;
        this.level = level;
        this.edition = edition;
        this.sellerName = sellerName;
        this.sellerId=sellerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getListedDate() {
        return listedDate;
    }

    public void setListedDate(String listedDate) {
        this.listedDate = listedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

//    public BookItemModel(String bookName, String id, String author, double price, String condition, String[] photoUri) {
//        this.bookName = bookName;
//        this.id = id;
//        this.author = author;
//        this.price = price;
//        this.condition = condition;
//        this.photoUri = photoUri;
//    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String[] getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String[] photoUri) {
        this.photoUri = photoUri;
    }
}
