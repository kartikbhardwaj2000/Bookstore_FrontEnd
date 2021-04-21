package com.example.bookstore.Authentication;

import com.example.bookstore.Authentication.Fragments.FragmentListener;
import com.example.bookstore.Authentication.Fragments.Step1Fragment;
import com.example.bookstore.Authentication.Fragments.Step2Fragment;
import com.example.bookstore.Authentication.Fragments.Step3Fragment;
import com.example.bookstore.Authentication.Fragments.Step4Fragment;
import com.example.bookstore.HomePage.HomeActivity;
import com.example.bookstore.R;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;

public class SignupActivity extends AppCompatActivity  implements FragmentListener,Step4Fragment.Listener {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar supportToolBar= findViewById(R.id.tool_bar);
         progressBar =findViewById(R.id.progress_bar);

        setSupportActionBar(supportToolBar);

   ActionBar actionBar= getSupportActionBar();
   if(actionBar!=null)
   {
       actionBar.setDisplayHomeAsUpEnabled(true);
   }
        addFirstFragment();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(fragment instanceof Step1Fragment)
        {
            setTitle("step-1");
            progressBar.setProgress(25);
        }
        if(fragment instanceof Step2Fragment)
        {
            setTitle("step-2");
            progressBar.setProgress(50);
        }
        if(fragment instanceof Step3Fragment)
        {
            setTitle("step-3");
            progressBar.setProgress(75);
        }
        if(fragment instanceof Step4Fragment)
        {
            setTitle("step-4");
            progressBar.setProgress(100);
        }



    }

    @Override
    public void nextButtonCLicked(String nextFragName) {
        Fragment nextFragment =null;
        switch (nextFragName)
        {
            case "step1":
                nextFragment=new Step1Fragment();
                break;
            case "step2":
                progressBar.setProgress(50);
                nextFragment=new Step2Fragment();
                setTitle("step-2");
                break;
            case "step3":
                progressBar.setProgress(75);
                nextFragment=new Step3Fragment();
                setTitle("step-3");
                break;
            case "step4":
                progressBar.setProgress(100);
                nextFragment=new Step4Fragment();
                setTitle("step-4");
                break;



        }
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        Log.d("data1","size is "+String.valueOf(count));
        Log.d("data2",fragment.toString());


    }

    @Override
    public void onNextClick() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    private void addFirstFragment()
    {
        setTitle("step-1");

        Step1Fragment step1Fragment = new Step1Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer,step1Fragment);
        transaction.commit();

    }

}