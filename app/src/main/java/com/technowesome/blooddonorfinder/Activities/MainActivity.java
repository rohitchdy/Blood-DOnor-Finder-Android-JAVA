package com.technowesome.blooddonorfinder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.technowesome.blooddonorfinder.Fragments.FeedFragment;
import com.technowesome.blooddonorfinder.Fragments.MakeARequestFragment;
import com.technowesome.blooddonorfinder.Fragments.MyDonationFragment;
import com.technowesome.blooddonorfinder.Fragments.MyPendingDonationFragment;
import com.technowesome.blooddonorfinder.Fragments.MyRequestFragment;
import com.technowesome.blooddonorfinder.Fragments.RequestFragment;
import com.technowesome.blooddonorfinder.NonActivities.PrefManager;
import com.technowesome.blooddonorfinder.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar=findViewById(R.id.toolBar);

        toolbar.setTitle("Requests");
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView  navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        // initialize by request fragment

        RequestFragment requestFragment = new RequestFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_for_fragments,requestFragment).commit();

        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(0);
        finish();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_requests) {
            RequestFragment requestsFragment = new RequestFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,requestsFragment).commit();
            toolbar.setTitle("Requests");

        }

        else if (id == R.id.nav_feeds) {
            FeedFragment feedsFragment = new FeedFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,feedsFragment).commit();
            toolbar.setTitle("Feeds");


        } else if (id == R.id.nav_make_a_request) {
            MakeARequestFragment makeARequestFragment = new MakeARequestFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,makeARequestFragment).commit();
            toolbar.setTitle("Make a Request");
        } else if (id == R.id.nav_my_requests) {
            MyRequestFragment myRequestsFragment = new MyRequestFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,myRequestsFragment).commit();
            toolbar.setTitle("My Requests");

        }else if (id == R.id.nav_my_pending_donation) {
            MyPendingDonationFragment myPendingDonationFragment = new MyPendingDonationFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,myPendingDonationFragment).commit();
            toolbar.setTitle("My Pending Donations");
        }
        else if (id == R.id.nav_my_donation) {
            MyDonationFragment myDonationFragment = new MyDonationFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_for_fragments,myDonationFragment).commit();
            toolbar.setTitle("My Donations");
        }

        else if (id == R.id.nav_setting) {
            Toast.makeText(getApplicationContext(), "Open Settings Activity", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,DemoActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout_user) {
            logout();
            Toast.makeText(getApplicationContext(),"User Logged out",Toast.LENGTH_LONG).show();
//            startLoginActivity();
            Log.d("LoginActivity","I am in logout");
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        new PrefManager(this).clearSharedPreferences();
    }



    
}