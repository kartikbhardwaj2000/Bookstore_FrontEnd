package com.example.bookstore.Authentication.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookstore.R;


public class Step4Fragment extends Fragment {

    Listener listener;

    private String userName="";
    private TextView nameTv;
    private String greeting=",In which city do you live?";
    private String city;
    private Spinner spinner;
    private View content;
    private View progressLayout;
    private  TextView progressTV;


    public Step4Fragment(String userName){

        this.userName=userName;
    }
    @Override
    public void onAttach(Context context) {
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
        return inflater.inflate(R.layout.fragment_step4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTv=getView().findViewById(R.id.name_tv);
        nameTv.setText(userName.concat(greeting));
        spinner=getView().findViewById(R.id.city_spinner);
        content=getView().findViewById(R.id.content);
        progressLayout=getView().findViewById(R.id.progress_layout);
        progressTV=getView().findViewById(R.id.message_tv);

        getView().findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city=(String) spinner.getSelectedItem();
                listener.setLocation(city);
                listener.signUpUser();

            }
        });
        closeProgressBar();
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

    static public interface Listener{
        public void setLocation(String location);
        public void signUpUser();
    }

}