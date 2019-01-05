package com.example.olfakaroui.android.UI.users;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.Login.UserRegistrationActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.CausesListAdapter;
import com.example.olfakaroui.android.adapter.UserListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FollowersFollowingActivity extends AppCompatActivity {

    private GridView gridView;
    User user = new User();
    UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_following);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridView = findViewById(R.id.followers_following_list);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.grid_anim);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        gridView.setLayoutAnimation(controller);
        user = (User) getIntent().getSerializableExtra("user");
        int what = getIntent().getIntExtra("liste", -1);
        if(what == 0)
        {
            adapter = new UserListAdapter(FollowersFollowingActivity.this, user.getFollowing());
            getSupportActionBar().setTitle(user.getFirstName()+" 's followings");
        }
        if(what == 1)
        {
            adapter = new UserListAdapter(FollowersFollowingActivity.this, user.getFollowers());
            getSupportActionBar().setTitle(user.getFirstName()+" 's followers");
        }
        gridView.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
