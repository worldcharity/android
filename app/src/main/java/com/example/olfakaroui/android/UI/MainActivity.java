package com.example.olfakaroui.android.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.example.olfakaroui.android.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //FOR DESIGN
  /*  private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment fragmentEvents;
    private Fragment fragmentPosts;
    public static User connectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            connectedUser = new User();
            connectedUser.setFirstName(prefs.getString("first_name", null));
            connectedUser.setLastName(prefs.getString("last_name", null));
            connectedUser.setSocialId(prefs.getString("social_id", null));
            connectedUser.setPhoto(prefs.getString("photo", null));
            connectedUser.setSocialPlatform(prefs.getString("social_platform", null));
            connectedUser.setrole(prefs.getString("role", null));
            connectedUser.setId(prefs.getInt("idName", 0));
        }
        this.fragmentEvents = new EventsFragment();
        //getSupportFragmentManager().beginTransaction()
         //       .add(R.id.activity_main_frame_layout, fragmentEvents).commit();
        this.configureToolBar();
        this.configureDrawerLayout();

        this.configureNavigationView();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
         /*  case R.id.nav_event :
                this.showEventsFragment();
                break;
           case R.id.nav_causes:
                this.showPostsFragment();
                break;
            case R.id.nav_charities:
                break;
            default:
                break;

        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showEventsFragment(){
        if (this.fragmentEvents== null) this.fragmentEvents = new EventsFragment();
        this.startTransactionFragment(this.fragmentEvents);
    }

    private void showPostsFragment(){
        if (this.fragmentPosts== null) this.fragmentPosts = new CausesDisplayFragment();
        this.startTransactionFragment(this.fragmentPosts);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
          //  getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
    private void configureToolBar(){
       // this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        //this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name, R.string.charities);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void configureNavigationView(){
        //this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }*/
}