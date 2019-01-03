package com.example.olfakaroui.android.UI.users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.MoreEventAdapter;
import com.example.olfakaroui.android.adapter.UserCollabsAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserCollabsListActivity extends AppCompatActivity {

    User user = new User();
    UserCollabsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_collabs_list);
        RecyclerView recyclerView = findViewById(R.id.activity_mycollabs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Collab> collabs = new ArrayList<>();


        user = (User) getIntent().getSerializableExtra("user");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(user.getFirstName() + " 's donations");
        UserService.getInstance().getCollabs(user.getId(), new UserService.UserServiceGetCollabsCallBack() {
            @Override
            public void onResponse(List<Collab> events) {
                adapter = new UserCollabsAdapter(collabs, UserCollabsListActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(String error) {
               

            }

        });
    }
}
