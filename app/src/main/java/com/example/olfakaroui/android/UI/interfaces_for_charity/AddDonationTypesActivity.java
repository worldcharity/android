package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.DonationListAdapter;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.service.EventService;

import java.util.List;

public class AddDonationTypesActivity extends AppCompatActivity {

    RecyclerView rv ;
    List<DonationType> lu ;
    DonationListAdapter whatever;
    final Context context = this;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation_types);
        rv = (RecyclerView) findViewById(R.id.donationRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        eventId = getIntent().getIntExtra("event", 0);
        EventService.getInstance().getDonationTypes(new EventService.EventServiceGetDonationTypesCallBack() {
            @Override
            public void onResponse(List<DonationType> events) {
                lu = events;
                whatever = new DonationListAdapter(context,lu,eventId);
                rv.setAdapter(whatever);

            }

            @Override
            public void onFailure(String error) {

            }

        });

        registerForContextMenu(rv);

    }

}
