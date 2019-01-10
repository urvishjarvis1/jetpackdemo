package com.example.bottomnavigationview;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController= Navigation.findNavController(this,R.id.mainNavFragment);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        NavigationUI.setupWithNavController(toolbar,navController,drawerLayout);
        NavigationView navigationView=findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView,navController);
    }
}
