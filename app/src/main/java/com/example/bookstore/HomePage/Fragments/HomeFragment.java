package com.example.bookstore.HomePage.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.HomePage.BooksData;
import com.example.bookstore.HomePage.RecyclerView.BookItemModel;
import com.example.bookstore.HomePage.RecyclerView.BooksAdapter;
import com.example.bookstore.R;
import com.example.bookstore.SessionManager;
import com.example.bookstore.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private SearchView searchView;
    private SessionManager sessionManager;
    private TextView greetTv;
    private String userName ="";
    private String cityName;
    private ArrayAdapter<CharSequence> adapter;
    private View content;
    private View progressLayout;
    private TextView progressTV;
    private Listener listener;


    private  RequestQueue requestQueue;
    private String URL="https://bookstore-rc.herokuapp.com/";

    ArrayList<BookItemModel> bookData=new ArrayList<>();
    private RecyclerView booksRv;
    BooksAdapter booksAdapter;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(Listener)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("tag","favourite fragment");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout=getView().findViewById(R.id.progress_layout);
        content=getView().findViewById(R.id.content);
        progressTV=getView().findViewById(R.id.message_tv);
        booksRv=getView().findViewById(R.id.books_rv);

        sessionManager=new SessionManager(getActivity());
        userName=sessionManager.getNAME();
        cityName=sessionManager.getLOCATION();
        spinner=getView().findViewById(R.id.current_city_spinner);
        searchView=getView().findViewById(R.id.search_view);
        greetTv=getView().findViewById(R.id.greet_tv);
        greetTv.setText("Hello "+userName+", You are currently viewing in ");
        adapter = ArrayAdapter.createFromResource(getActivity(),R.array.city_names, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        if(cityName!=null)
        {
            int spinnerPosition = adapter.getPosition(cityName);
            spinner.setSelection(spinnerPosition);

        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("search submit",searchView.getQuery().toString());
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("search",searchView.getQuery().toString());

                return true;
            }
        });


        booksAdapter = new BooksAdapter(bookData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        booksRv.setLayoutManager(linearLayoutManager);
//        BookItemModel itemModel = new BookItemModel("HP Chromebook 14 Laptop, Intel Celeron N4000 Processor, 4 GB RAM, 32 GB eMMC, 14” HD Display, Chrome, " +
//                "Lightweight Computer with Webcam and Dual Mics, Home," +
//                " School, Music, Movies (14a-na0021nr, 2021)","alksdf","Samsung electronics ",300,"like-new",new String[]{"https://m.media-amazon.com/images/I/81NvTUBf9ZS._AC_UY327_FMwebp_QL65_.jpg"});
//        bookData.add(itemModel);
        booksAdapter=new BooksAdapter(bookData);
        BooksData.bookData = bookData;
        getSearchData("electronics");

        booksRv.setAdapter(booksAdapter);





    }

    public void initialize()
    {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(),adapter.getItem(position).toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void closeProgressBar()
    {

        progressLayout.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

    }
    public void showProgressBar(String message)
    {
        progressTV.setText(message);
        content.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
    }



    public void getSearchData(String query) {

 Log.d("getSearch","inside func");


        this.showProgressBar("fetching books  .....");
        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();


        }
        JSONObject jsonbody= new JSONObject();
        try {
            jsonbody.put("searchQuery",query);


        }catch (JSONException e){
            Log.d("error",e.toString());
        }
        String requestBody =jsonbody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL+"store/getSearch",null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response",response.toString());
                String status = null;
                JSONArray jsonArray=null;
                try {
                    status = response.getString("status");
                    jsonArray= response.getJSONArray("data");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!status.equals("success")||jsonArray==null)
                {
                    listener.onError("some error occurred please try again");
                }


                for (int i=0;i<jsonArray.length();i++)
                {
                    try {
                        JSONObject bookObj=jsonArray.getJSONObject(i);
                        JSONArray imageUriArr=bookObj.getJSONArray("photoUri");
                        String uriArr[]=new String[imageUriArr.length()];
                        for(int j=0;j<uriArr.length;j++)
                        {
                            uriArr[j]=(String)imageUriArr.get(j);
                            Log.d("uri",uriArr[j]);
                        }

                        BookItemModel bookItem=new BookItemModel(bookObj.getString("bookName"),bookObj.getString("_id"),bookObj.getString("author"),bookObj.getDouble("price"),bookObj.getString("condition"),uriArr,bookObj.getInt("quantity"),bookObj.getString("listedDate"),
                                bookObj.getString("description"),bookObj.getString("level"),bookObj.getInt("edition"),bookObj.getString("sellerName"),bookObj.getString("listedBy"));
                        bookData.add(bookItem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                booksAdapter.notifyDataSetChanged();
                booksRv.setAdapter(booksAdapter);
                closeProgressBar();

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                closeProgressBar();
                Log.d("error",error.toString());

               listener.onError(error.getMessage());

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
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(jsonObjectRequest);


    }


}