package com.example.bookstore.HomePage.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.SessionManager;

public class HomeFragment extends Fragment {


    private Spinner spinner;
    private SearchView searchView;
    private SessionManager sessionManager;
    private TextView greetTv;
    private String userName ="";
    private String cityName;



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

        sessionManager=new SessionManager(getActivity());
        userName=sessionManager.getNAME();
        cityName=sessionManager.getLOCATION();
        spinner=getView().findViewById(R.id.current_city_spinner);
        searchView=getView().findViewById(R.id.search_view);
        greetTv=getView().findViewById(R.id.greet_tv);
        greetTv.setText("Hello "+userName+", You are currently viewing in ");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.city_names, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
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
    }

    public void initialize()
    {

    }
}