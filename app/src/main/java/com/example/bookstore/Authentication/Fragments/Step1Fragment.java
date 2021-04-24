
package com.example.bookstore.Authentication.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.bookstore.R;


public class Step1Fragment extends Fragment {

   private FragmentListener listener;
   private EditText nameEditText;
   private DataListener dataListener;
   private  String userName;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step1, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener=(FragmentListener) context;
        this.dataListener=(DataListener)context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameEditText = getView().findViewById(R.id.name_edittext);
        getView().findViewById(R.id.next_button).setOnClickListener(v -> {

            userName=nameEditText.getText().toString();
            if(userName==null||userName.length()==0)
            {
                listener.onError("please enter your name");
                return;
            }

            dataListener.setName(userName);
            listener.nextButtonCLicked("step2");


        });



    }


    public void closeProgressBar()
    {



    }
    public void showProgressBar(String message)
    {

    }
    public static interface DataListener{
        void setName(String name);
    }




}