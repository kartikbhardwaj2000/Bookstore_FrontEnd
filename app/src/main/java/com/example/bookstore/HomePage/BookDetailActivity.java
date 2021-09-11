
package com.example.bookstore.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.bookstore.HomePage.RecyclerView.BookItemModel;
import  com.example.bookstore.R;
import com.example.bookstore.SessionManager;
import com.example.bookstore.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class BookDetailActivity extends AppCompatActivity {
    private String POSITION_EXTRA="positionExtra";
    private int position=-1;
    private BookItemModel item;
    private ImageView bookImageView;
    private TextView bookTitleTV;
    private TextView authorTV;
    private TextView priceTV;
    private TextView sellerTV;
    private TextView bookLevelTV;
    private TextView description;
    private TextView editionTV;
    private TextView conditionTV;
    private Button notifyBtn;

    private RequestQueue requestQueue;
    private final String  URL="https://bookstore-rc.herokuapp.com/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent= getIntent();
        if(intent!=null)
        {
            position=intent.getIntExtra(POSITION_EXTRA,-1);

        }
        bookImageView=findViewById(R.id.bookImage);
        bookTitleTV=findViewById(R.id.book_title_tv);
        authorTV=findViewById(R.id.book_author_tv);
        priceTV=findViewById(R.id.book_price_tv);
        description=findViewById(R.id.description_tv);
        editionTV=findViewById(R.id.book_edition_tv);
        sellerTV=findViewById(R.id.seller_tv);
        bookLevelTV=findViewById(R.id.book_level_tv);
        conditionTV=findViewById(R.id.condition_tv);
        notifyBtn=findViewById(R.id.notify_btn);

        if(position!=-1)
        {
            item=BooksData.bookData.get(position);
            populate();

        }

        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(null,item.getId(),new SessionManager(BookDetailActivity.this).getUSER_ID(),item.getSellerId());
            }
        });

    }



    public void populate()
    {
        Glide.with(this).load(item.getPhotoUri()[0]).placeholder(R.drawable.book_placeholder2).into(bookImageView);
        Log.d("imageUri",item.getPhotoUri()[0]);
        bookTitleTV.setText(item.getBookName());
        authorTV.setText("By "+item.getAuthor());
        priceTV.setText("Rs "+String.valueOf(item.getPrice()));
        description.setText(item.getDescription());
        editionTV.setText(getEditionString(item.getEdition()));
        conditionTV.setText(item.getCondition());
        bookLevelTV.setText(item.getLevel());
        sellerTV.setText(item.getSellerName());
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

    private void sendNotification(String message,String BookId,String UserId,String TargetUserId)
    {
        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        }

        JSONObject requestBody= new JSONObject();
        try {
            requestBody.put("bookId",BookId);
            requestBody.put("userId",UserId);
            requestBody.put("message",message);
            requestBody.put("targetUserId",TargetUserId);


        }catch (JSONException e){
            Log.d("error",e.toString());
        }


        String requestBodyStr =requestBody.toString();
        Log.d("BookDetailActivity",requestBodyStr);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL + "user/notifyUser", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("BookDetail", response.toString());
                String status = null;
                try {
                    status = response.getString("status");
                    if(status.equals("success"))
                    {
                        onError("User Notified Successfully");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!status.equals("success")) {
                    onError("some error occurred please try again");
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BookDetailActivity",error.toString());
                onError(error.toString());
            }
        }
        ){


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody()  {
                try {
                    return requestBodyStr == null ? null : requestBodyStr.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }



        };

        requestQueue.add(jsonObjectRequest);
    }

    private void onError(String Message)
    {
        Toast.makeText(this,Message,Toast.LENGTH_LONG).show();
    }
}