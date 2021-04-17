package com.example.bookstore.Authentication.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookstore.R;


public class Step3Fragment extends Fragment {

    private FragmentListener listener;
    private Button nextButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nextButton=getView().findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
           listener.nextButtonCLicked("step4");
        });

    }
}