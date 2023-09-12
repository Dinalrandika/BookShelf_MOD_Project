package com.example.mad_ex2;

public class Cart_Item {
    private String bookId;
    private int quantity;

    public Cart_Item() {
        // Default constructor required for Firebase
    }

    public Cart_Item(String bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

