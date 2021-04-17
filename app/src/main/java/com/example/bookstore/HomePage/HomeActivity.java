package com.example.bookstore.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bookstore.CreateListing.CreateListingActivity;
import com.example.bookstore.HomePage.Fragments.FavouriteFragment;
import com.example.bookstore.HomePage.Fragments.HomeFragment;
import com.example.bookstore.HomePage.Fragments.NotificationFragment;
import com.example.bookstore.HomePage.Fragments.ProfileFragment;
import com.example.bookstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar supportToolBar= findViewById(R.id.tool_bar);
        bottomNavigationView =findViewById(R.id.bottom_nav);
        setSupportActionBar(supportToolBar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayUseLogoEnabled(true);

        }

        setTitle("Bookstore");

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        addHomeFragment();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId())
        {
            case R.id.home_menu_item:
                fragment=new HomeFragment();
                break;
            case R.id.favourite_menu_item:
                fragment=new FavouriteFragment();
                break;

            case R.id.add_listing_menu_item:
                startCreateListingActivity();
                return true;

            case R.id.notification_menu_item:
                fragment=new NotificationFragment();
                break;

            case R.id.profile_menu_item:
                fragment=new ProfileFragment();
                break;

            default: return false;

        }
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         getMenuInflater().inflate(R.menu.options_menu,menu);
         return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addHomeFragment()
    {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer,homeFragment);
        fragmentTransaction.commit();
    }

    private void startCreateListingActivity()
    {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
    }
}