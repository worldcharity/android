package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.users.UserCollabsListActivity;
import com.example.olfakaroui.android.adapter.PendingCollabsAdapter;
import com.example.olfakaroui.android.adapter.UserCollabsAdapter;
import com.example.olfakaroui.android.adapter.UserCollabsMyEventAdapter;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class EventCollabsActivity extends AppCompatActivity {

    Event event = new Event();
    UserCollabsMyEventAdapter adapter;
    List<Collab> col;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_collabs);
        RecyclerView recyclerView = findViewById(R.id.activity_collabs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        event = (Event) getIntent().getSerializableExtra("event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Donations");
        EventService.getInstance().getEventCollabs(event.getId(), new EventService.EventServiceGetPendingCollabsCallBack() {
            @Override
            public void onResponse(List<Collab> collabs) {
                col = collabs;
                ArrayList<Collab> filtered = new ArrayList<>();
                for (Collab com : collabs) {
                    if (com.getState() == 1)
                    {
                        filtered.add(com);

                    }
                }
                col = filtered;
                adapter = new UserCollabsMyEventAdapter(col, EventCollabsActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(EventCollabsActivity.this));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(String error) {

            }

        });
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
