
package com.example.studentapp;

import static android.view.Gravity.START;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.studentapp.ui.ApplyDocs.DocsActivity;
import com.example.studentapp.ui.ResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class submain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);


        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        navController= Navigation.findNavController(this,R.id.framelayout);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start ,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);


        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigationprofile:
                Intent intent1=new Intent(submain.this,profile.class);
                startActivity(intent1);
                break;

            case R.id.navigationlibrary:
                setContentView(R.layout.activity_library);
                break;
            case R.id.navigationabout:
                setContentView(R.layout.fragment_about);
                break;
            case R.id.navigationresult:
                Intent intent = new Intent(submain.this, ResultActivity.class);
                startActivity(intent);
                break;

            case R.id.navigationdocs:
                Intent intent2 = new Intent(submain.this, DocsActivity.class);
                startActivity(intent2);

                break;

            case R.id.logout:
                logout();
        }
        return true;
    }

    private void logout() {
        // Perform the logout action
        FirebaseAuth.getInstance().signOut();

        // Start LoginActivity
        Intent intent = new Intent(submain.this, login.class);
        startActivity(intent);
        finish();
    }


}