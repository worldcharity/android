package com.example.olfakaroui.android.UI;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.events.EventsByCauseFragment;
import com.example.olfakaroui.android.UI.events.HomePageFragment;
import com.example.olfakaroui.android.utils.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity implements HomePageFragment.OnFragmentInteractionListener, EventsByCauseFragment.OnFragmentInteractionListener {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        toolbar.hide();
        loadFragment(new HomePageFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.nav_event:
                    toolbar.hide();
                    loadFragment(new HomePageFragment());
                    return true;
                case R.id.nav_charities:
                    toolbar.setTitle("Charities");
                    return true;
                case R.id.nav_causes:
                    toolbar.setTitle("Causes");
                    return true;
                case R.id.nav_settings:
                    toolbar.setTitle("Settings");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

