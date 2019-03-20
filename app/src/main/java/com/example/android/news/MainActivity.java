package com.example.android.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.android.news.fragments.BusinessFragment;
import com.example.android.news.fragments.PoliticsFragment;
import com.example.android.news.fragments.ScienceFragment;
import com.example.android.news.fragments.SportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the toolbar, set title and color
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);

        //find the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //open the politics fragment initially
        Fragment selectedFragment = new PoliticsFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_container, selectedFragment
        ).commit();
    }

    /**
     *  To handle navigation through the bottom navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.politics:
                            selectedFragment = new PoliticsFragment();
                            break;
                        case R.id.business:
                            selectedFragment = new BusinessFragment();
                            break;
                        case R.id.science:
                            selectedFragment = new ScienceFragment();
                            break;
                        case R.id.sports:
                            selectedFragment = new SportsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fragment_container, selectedFragment
                    ).commit();

                    return true;
                }
            };
}
