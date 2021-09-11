package com.example.bookstore.HomePage.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;

import java.util.ArrayList;

public class BooksAdapter  extends RecyclerView.Adapter {

    private ArrayList<BookItemModel> data;


    public BooksAdapter (ArrayList<BookItemModel> data)
    {
        this.data=data;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_xml,parent,false);

        return new BookViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((BookViewHolder) holder).populate(data.get(position),position);


    }

    @Override
    public int getItemCount() {


        return data==null?0:data.size();
    }
}
