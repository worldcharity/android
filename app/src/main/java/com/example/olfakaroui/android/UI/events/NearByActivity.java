package com.example.olfakaroui.android.UI.events;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.service.EventService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearByActivity extends AppCompatActivity {

    double lng,lat;
    private SearchView searchView;
    MoreEventAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
       lng =  getIntent().getDoubleExtra("lng", 0);
       lat =  getIntent().getDoubleExtra("lat", 0);
        Log.d("location", lng+" "+lat);
       Location loc1 = new Location("");
       loc1.setLatitude(lat);
       loc1.setLongitude(lng);
        RecyclerView recyclerView = findViewById(R.id.activity_see_all_events_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Event> events = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearest events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EventService.getInstance().getAllEvents(new EventService.EventServiceGetCallBack() {
            @Override
            public void onResponse(List<Event> events) {
                Collections.sort(events, new Comparator<Event>(){
                    public int compare(Event obj1, Event obj2) {
                        Log.d(" ", "compare: ");
                        Location location1 = new Location("");
                        location1.setLatitude(obj1.getLatitude());
                        location1.setLongitude(obj1.getLongitude());
                        Location location2 = new Location("");
                        location2.setLatitude(obj2.getLatitude());
                        location2.setLongitude(obj2.getLongitude());
                         return Float.valueOf(loc1.distanceTo(location1)).compareTo(loc1.distanceTo(location2));

                    }
                });
                if(events.size() > 5)
                {
                    Log.d("size", "5");
                    List<Event> newList = new ArrayList<>(events.subList(0,5));
                    adapter = new MoreEventAdapter(newList, NearByActivity.this);
                    recyclerView.setAdapter(adapter);
                }
                if(events.size() < 5)
                {
                    Log.d("size", "<5");
                    adapter = new MoreEventAdapter(events, NearByActivity.this);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(String error) {



            }

        });
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
