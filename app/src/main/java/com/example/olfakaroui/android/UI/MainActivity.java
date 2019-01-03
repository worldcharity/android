package com.example.olfakaroui.android.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UI.events.EventsByCauseFragment;
import com.example.olfakaroui.android.UI.events.HomePageFragment;
import com.example.olfakaroui.android.UI.events.MyEventsFragment;
import com.example.olfakaroui.android.UI.events.PendingCollabsFragment;
import com.example.olfakaroui.android.UI.users.CharitiesListFragment;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.utils.BottomNavigationBehavior;
import com.example.olfakaroui.android.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements HomePageFragment.OnFragmentInteractionListener,
        EventsByCauseFragment.OnFragmentInteractionListener, CharitiesListFragment.OnFragmentInteractionListener {

    private ActionBar toolbar;
    private Fragment fragment;
    BottomNavigationView navigation;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SessionManager sessionManager = new SessionManager(this);
        //sessionManager.getLogin(user);
        user.setRole("user");
        toolbar = getSupportActionBar();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if(user.getRole().equals("user"))
        {
            navigation.inflateMenu(R.menu.navigationbar);
        }
        else
        {
            navigation.inflateMenu(R.menu.navigationbar_charity);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        toolbar.hide();
        fragment = new HomePageFragment();
        loadFragment(fragment);
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
                    fragment = new HomePageFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_myevents:
                    toolbar.hide();
                    fragment = new MyEventsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_donations:
                    toolbar.hide();
                    fragment = new PendingCollabsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_charities:
                    toolbar.show();
                    fragment = new CharitiesListFragment();
                    loadFragment(fragment);
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                fragment = new HomePageFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                Log.d("reloaaaaaaaad", "hello");
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
        if (requestCode == 2) {

            if(resultCode == RESULT_OK){
                fragment = new CharitiesListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                Log.d("reloaaaaaaaad", "charities");
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }



}

