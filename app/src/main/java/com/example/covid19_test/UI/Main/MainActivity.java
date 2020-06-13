package com.example.covid19_test.UI.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.covid19_test.R;
import com.example.covid19_test.Utilities.PassDataViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.google.gson.reflect.TypeToken.get;


public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener  {
    private static final String TAG_PLACEHOLDER = "TAG_PLACEHOLDER" ;
    private BottomNavigationView mBottomNavigation;
    private CustomViewPager mMainViewPager;
    private TabAdapter mMainViewPagerAdapter;

    PassDataViewModel passDataViewModel;
    String s ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        passDataViewModel = ViewModelProviders.of(MainActivity.this).get(PassDataViewModel.class);

        mBottomNavigation = findViewById(R.id.buttom_navigation);

        mBottomNavigation.setOnNavigationItemSelectedListener(this);
        mMainViewPager = findViewById(R.id.Main_viewPager);
        mMainViewPagerAdapter = new TabAdapter(getSupportFragmentManager());
        mMainViewPager.setAdapter(mMainViewPagerAdapter);
       // mMainViewPager = findViewById(R.id.pager);
        mMainViewPager.setOffscreenPageLimit(4);         /* limit is a fixed integer*/


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
             case R.id.home_menu:
                 mMainViewPager.setCurrentItem(0);

                 break;
            case R.id.countries_menu :
                mMainViewPager.setCurrentItem(1);
                // to back to first fragment when click in earth icon again
                if (getSupportFragmentManager().getBackStackEntryCount() >= 0){
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }

                break;
            case R.id.map_menu:

                mMainViewPager.setCurrentItem(2);
                break;

            case R.id.more_menu:
                mMainViewPager.setCurrentItem(3);
                break;

        }

        return true;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}



