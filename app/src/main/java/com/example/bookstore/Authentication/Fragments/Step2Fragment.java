
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


public class Step2Fragment extends Fragment {

 private  FragmentListener listener;
 private EditText phoneNoEdittext;
 private DataListener dataListener;
 private String phoneNo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(FragmentListener)context;
        dataListener=(DataListener)context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.next_button).setOnClickListener(v -> {

            phoneNoEdittext=getView().findViewById(R.id.phNo_editText);
            phoneNo=phoneNoEdittext.getText().toString();
            if(phoneNo==null||phoneNo.length()==0)
            {
                listener.onError("please enter phone number correctly");
                return;

            }
            dataListener.setPhoneNo(phoneNo);
            listener.nextButtonCLicked("step3");


        });


    }
    public void closeProgressBar()
    {



    }
    public void showProgressBar(String message)
    {

    }

    public static interface DataListener{
        void setPhoneNo(String phoneNo);
    }

}