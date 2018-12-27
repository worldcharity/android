package com.example.olfakaroui.android.UI.events;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.MoreEventAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class SeeAllEventsActivity extends AppCompatActivity {

    private static final String TAG = "MoreActivity";
    private SearchView searchView;
    MoreEventAdapter adapter;

    public static final String EXTRA_EVENT_CATEGORY = "category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_events);

        RecyclerView recyclerView = findViewById(R.id.activity_see_all_events_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Event> events = new ArrayList<>();
        adapter = new MoreEventAdapter(events, this);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventService.getInstance().getAllEvents(new EventService.EventServiceGetCallBack() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
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
