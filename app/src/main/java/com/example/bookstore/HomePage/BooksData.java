package com.example.bookstore.HomePage;

import com.example.bookstore.HomePage.RecyclerView.BookItemModel;

import java.util.ArrayList;

public class BooksData {
    public static ArrayList<BookItemModel> bookData;

    public ArrayList<BookItemModel> getBookData() {
        return bookData;
    }

    public void setBookData(ArrayList<BookItemModel> bookData) {
        this.bookData = bookData;
    }
}
