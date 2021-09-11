package com.example.bookstore.HomePage.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.HomePage.BookDetailActivity;
import com.example.bookstore.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private ImageView bookImageView;
    private TextView bookTitleTv;
    private TextView bookPriceTv;
    private TextView bookCondition;
    private TextView bookEdition;
    private TextView bookLevel;
    private TextView bookAuthorTv;
    private Button notifyBtn;
    private ToggleButton favBtn;
    private Context context;
    private ConstraintLayout constraintLayout;
    private int position;
    private String POSITION_EXTRA="positionExtra";



    public BookViewHolder(@NonNull View itemView,Context context) {
        super(itemView);

        constraintLayout=itemView.findViewById(R.id.constraintLayout);
        bookImageView=itemView.findViewById(R.id.book_imageview);
        bookTitleTv=itemView.findViewById(R.id.booktitle_tv);
        bookPriceTv=itemView.findViewById(R.id.bookprice_tv);
        bookCondition=itemView.findViewById(R.id.bookcondition_tv);
        bookAuthorTv=itemView.findViewById(R.id.bookauthor_tv);
        bookEdition=itemView.findViewById(R.id.edition_tv);
        bookLevel=itemView.findViewById(R.id.level_tv);
        notifyBtn=itemView.findViewById(R.id.notify_owner_btn);
        favBtn=itemView.findViewById(R.id.fav__btn);
        this.context=context;

        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, BookDetailActivity.class);
//                context.startActivity(intent);
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BookDetailActivity.class);
                intent.putExtra(POSITION_EXTRA,position);
                context.startActivity(intent);
            }
        });
    }



    public void populate(BookItemModel item,int position)
    {
        this.position=position;
        bookTitleTv.setText(item.getBookName());
        bookAuthorTv.setText(item.getAuthor());
        bookCondition.setText(item.getCondition());
        bookPriceTv.setText(String.valueOf(item.getPrice()));
        bookEdition.setText(getEditionString(item.getEdition()));
        bookLevel.setText(item.getLevel()+" level");
        Glide.with(context).load(item.getPhotoUri()[0]).placeholder(R.drawable.book_placeholder2).into(bookImageView);


    }
    public String getEditionString(int edition)
    {
        if(edition==1)
        {
            return "1st edition";
        }
        if(edition==2)
        {
            return "2nd edition";
        }
        if(edition==3)
        {
            return "3rd edition";
        }
        return String.valueOf(edition)+"th edition";
    }

}
