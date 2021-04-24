package com.example.bookstore.Authentication.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bookstore.R;


public class Step3Fragment extends Fragment {

    private FragmentListener listener;
    private OtpListener otpListener;
    private Button nextButton;
    private String phoneNumber;
    private TextView phoneNoTextView;
    private TextView resendTextView;
    private TextView progressTV;
    private EditText otpEdtxt;
    private View content;
    private View progressLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Step3Fragment(String phoneNumber)
    {
     this.phoneNumber=phoneNumber;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step3, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(FragmentListener)context;
        otpListener=(OtpListener)context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
       phoneNoTextView= getView().findViewById(R.id.phone_no_txt);
       phoneNoTextView.setText(phoneNumber);
       resendTextView=getView().findViewById(R.id.resend_tv);
       progressLayout=getView().findViewById(R.id.progress_layout);
       content=getView().findViewById(R.id.content);
       otpEdtxt=getView().findViewById(R.id.otp_edt);
       progressTV=getView().findViewById(R.id.message_tv);



       resendTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Log.d("btn","btn clicked");

               otpListener.sendOtp(phoneNumber);
           }
       });
        nextButton=getView().findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {

            String otpCode=otpEdtxt.getText().toString();
            if(otpCode.length()<6)
            {
                listener.onError("otp length must be 6");
                return;
            }
            otpListener.verifyOtp(phoneNumber,otpCode);
//            listener.nextButtonCLicked("step4");
        });

        closeProgressBar();
        otpListener.sendOtp(phoneNumber);



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
    public static interface OtpListener{
        void sendOtp(String phoneNo);
        void verifyOtp(String phoneNumber ,String otpCode);
    }

}