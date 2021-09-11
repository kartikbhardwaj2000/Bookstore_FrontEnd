package com.example.bookstore.HomePage.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookstore.R;


public class ProfileFragment extends Fragment {

    private View content;
    private View progressLayout;
    private TextView progressTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("tag","favourite fragment");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout=getView().findViewById(R.id.progress_layout);
        content=getView().findViewById(R.id.content);
        progressTV=getView().findViewById(R.id.message_tv);
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
}