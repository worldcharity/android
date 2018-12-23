package com.example.olfakaroui.android.UI.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.adapter.MoreEventAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity {

    private static final String TAG = "MoreActivity";

    public static final String EXTRA_EVENT_CATEGORY = "category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        RecyclerView recyclerView = findViewById(R.id.activity_more_events_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Event> events = new ArrayList<>();
        final MoreEventAdapter adapter = new MoreEventAdapter(events, this);
        recyclerView.setAdapter(adapter);
        Cause cause = (Cause) getIntent().getSerializableExtra(EXTRA_EVENT_CATEGORY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cause.getName() + " related events");
        EventService.getInstance().getEventsByCause(cause.getId(), new EventService.EventServiceGetCallBack() {
            @Override
            public void onResponse(List<Event> events) {
                adapter.mEvents = events;
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

}

