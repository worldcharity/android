package com.example.olfakaroui.android.UI.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.MoreEventAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class CharityEventsListActivity extends AppCompatActivity {
    private static final String TAG = "CharityEventsList";

    public static final String EXTRA_EVENT_USER= "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_events_list);

        RecyclerView recyclerView = findViewById(R.id.charity_events_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Event> events = new ArrayList<>();
        MoreEventAdapter adapter = new MoreEventAdapter(events, this);
        recyclerView.setAdapter(adapter);
        User user = (User) getIntent().getSerializableExtra(EXTRA_EVENT_USER);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events by "+ user.getFirstName());
        EventService.getInstance().getEventsByUser(user.getId(), new EventService.EventServiceGetCallBack() {
            @Override
            public void onResponse(List<Event> events) {
                adapter.mEvents = events;
                adapter.itemsFiltered = events;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, error);


            }

        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}
