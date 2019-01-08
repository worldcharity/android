package com.example.olfakaroui.android.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.login.LoginActivity;
import com.example.olfakaroui.android.UI.events.EventsByCauseFragment;
import com.example.olfakaroui.android.UI.events.HomePageFragment;
import com.example.olfakaroui.android.UI.events.MyFavsFragment;
import com.example.olfakaroui.android.UI.interfaces_for_charity.MyEventsFragment;
import com.example.olfakaroui.android.UI.interfaces_for_charity.PendingCollabsFragment;
import com.example.olfakaroui.android.UI.posts.CausesDisplayFragment;
import com.example.olfakaroui.android.UI.users.CharitiesListFragment;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.utils.BottomNavigationBehavior;
import com.facebook.AccessToken;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements HomePageFragment.OnFragmentInteractionListener,
        EventsByCauseFragment.OnFragmentInteractionListener, CharitiesListFragment.OnFragmentInteractionListener,
        MyEventsFragment.OnFragmentInteractionListener,
        PendingCollabsFragment.OnFragmentInteractionListener, MyFavsFragment.OnFragmentInteractionListener,
        MyProfileFragment.OnFragmentInteractionListener{

    private ActionBar toolbar;
    private Fragment fragment;
    BottomNavigationView navigation;
    FloatingActionButton fab;
    User user = new User();
    public static GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
        /*if(user.getSocialPlatform().equals("facebook"))
        {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken==null || accessToken.isExpired()){
                sessionManager.logoff();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }*/


        toolbar = getSupportActionBar();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        fab = findViewById(R.id.fab);
        if(user.getRole().equals("user"))
        {
            navigation.getMenu().clear();
            navigation.inflateMenu(R.menu.navigationbar);
            toolbar.hide();
            fragment = new HomePageFragment();
            loadFragment(fragment);
        }
        else
        {
            navigation.getMenu().clear();
            navigation.inflateMenu(R.menu.navigationbar_charity);
            toolbar.hide();
            fragment = new PendingCollabsFragment();
            loadFragment(fragment);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatRoomActivity.class);
                startActivity(intent);
            }
        });


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
                case R.id.nav_fav:
                    toolbar.hide();
                    fragment = new MyFavsFragment();
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
                    //toolbar.hide();
                    fragment = new CausesDisplayFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_settings:
                    toolbar.hide();
                    fragment = new MyProfileFragment();
                    loadFragment(fragment);
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

