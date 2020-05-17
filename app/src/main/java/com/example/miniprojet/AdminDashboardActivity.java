package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboardActivity extends AppCompatActivity {


    //firebase auth
    FirebaseAuth firebaseAuth;

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        //Actionbar and its title

        actionBar = getSupportActionBar();
        actionBar.setTitle("Admin Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //init views
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        //fragment transaction (default on star)
        actionBar.setTitle("Home"); //change action bar title
        AdminHomeFragment fragment1 = new AdminHomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, fragment1, "");
        ft1.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //handle item clicks
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            //fragment transaction
                            actionBar.setTitle("Home"); //change action bar title
                            AdminHomeFragment fragment1 = new AdminHomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, fragment1, "");
                            ft1.commit();
                            return true;
                        case R.id.nav_profile:
                            //fragment transaction
                            actionBar.setTitle("Profile"); //change action bar title
                            AdminProfileFragment fragment2 = new AdminProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            return true;
                        case R.id.nav_users:
                            //fragment transaction
                            actionBar.setTitle("Users"); //change action bar title
                            AdminUsersFragment fragment3 = new AdminUsersFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, fragment3, "");
                            ft3.commit();
                            return true;
                    }

                    return false;
                }
            };

    private void checkUserStatus(){
        //get current user
        FirebaseUser admin = firebaseAuth.getCurrentUser();
        if (admin != null){
            //user is signed in  stay here
            //set email of logged in user

        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(AdminDashboardActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }
}
