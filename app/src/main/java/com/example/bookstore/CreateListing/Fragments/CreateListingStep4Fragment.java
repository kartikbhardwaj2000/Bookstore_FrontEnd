package com.example.bookstore.CreateListing.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookstore.R;


public class CreateListingStep4Fragment extends Fragment {


    private ImageCapture imageCapture;
    private Button nextButton;
    private Listener listener;
    private TextView skipTv;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(Listener)context;
        imageCapture=(ImageCapture)context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_listing_step4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        skipTv=getView().findViewById(R.id.skip_tv);
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNextClick("step5");
            }
        });
        nextButton=getView().findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageCapture.captureImage();
//                listener.onNextClick("step5");
            }
        });

    }

    public static interface ImageCapture{
        void captureImage();


    }

}