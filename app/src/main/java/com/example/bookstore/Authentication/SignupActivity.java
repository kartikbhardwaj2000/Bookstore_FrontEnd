package com.example.bookstore.Authentication;

import com.example.bookstore.Authentication.Fragments.FragmentListener;
import com.example.bookstore.Authentication.Fragments.Step1Fragment;
import com.example.bookstore.Authentication.Fragments.Step2Fragment;
import com.example.bookstore.Authentication.Fragments.Step3Fragment;
import com.example.bookstore.Authentication.Fragments.Step4Fragment;
import com.example.bookstore.R;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class SignupActivity extends AppCompatActivity  implements FragmentListener {

    private ProgressBar progressBar;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar supportToolBar= findViewById(R.id.tool_bar);
         progressBar =findViewById(R.id.progress_bar);

        setSupportActionBar(supportToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void addFirstFragment()
    {
        setTitle("step-1");

        Step1Fragment step1Fragment = new Step1Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer,step1Fragment);
        transaction.commit();
        currentFragment=step1Fragment;
    }

    private  void initialize()
    {
//     switch (currentFragment)
//     {
//         case currentFragment instanceof Step2Fragment:
//
//             break;
//         case currentFragment instanceof Step3Fragment:
//             setTitle("step-2");
//             progressBar.setProgress(50);
//             break;
//         case currentFragment instanceof Step4Fragment:
//             setTitle("step-3");
//             progressBar.setProgress(75);
//
//     }

//     if(currentFragment instanceof Step2Fragment)
//     {
//         setTitle("step-1");
//         progressBar.setProgress(25);
//         currentFragment= getSupportFragmentManager().getFragments().;
//     }
       int count = getSupportFragmentManager().getFragments().size();
       List<Fragment> fragments = getSupportFragmentManager().getFragments();
       Fragment fragment=fragments.get(fragments.size()-1);
       Log.d("data1","size is "+String.valueOf(count));
        Log.d("data2",fragment.toString());



    }
}