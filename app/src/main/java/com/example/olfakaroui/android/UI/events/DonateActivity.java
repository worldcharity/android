package com.example.olfakaroui.android.UI.events;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.DonationPagerAdapter;
import com.example.olfakaroui.android.adapter.DonationTypesAdapter;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.utils.DonationViewPager;

import java.util.ArrayList;
import java.util.List;

public class DonateActivity extends AppCompatActivity implements DonateFragment.DonateCallBack, DonateMessageFragment.DonateMessageCallback, DonationTypesAdapter.DonateCallBack{

    Event mEvent;
    private TabLayout mDonationTabLayout;
    private DonationViewPager mDonationViewPager;
    private RecyclerView typesList;
    private DonationTypesAdapter adapter;
    private DonationType selectedDonationType = new DonationType();
    CardView card;
    User current = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        mEvent = (Event) getIntent().getSerializableExtra("event");
        //SessionManager sessionManager = new SessionManager(this);
        //sessionManager.getLogin(current);
        current.setId(6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Donate to "+ mEvent.getName());
        mDonationTabLayout = findViewById(R.id.event_donation_tab_layout);
        mDonationViewPager = findViewById(R.id.event_donation_viewpager);
        card = findViewById(R.id.activity_details_donation_card_view);
        typesList = findViewById(R.id.event_detail_types);
        typesList.setNestedScrollingEnabled(false);
        typesList.setLayoutManager(new LinearLayoutManager(this));
        Log.d("types ", " " +mEvent.getDonationEvents().size());
        adapter = new DonationTypesAdapter(mEvent.getDonationEvents(), mEvent.getId(), this);
        adapter.setCallBack(this);
        typesList.setAdapter(adapter);
        setupDonationViewPager();
    }

    private void setupDonationViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DonateFragment.newInstance(mEvent, this));
        fragments.add(DonateMessageFragment.newInstance(this));
        DonationPagerAdapter adapter = new DonationPagerAdapter(getSupportFragmentManager(), fragments);
        mDonationViewPager.setPagingEnabled(false);
        mDonationViewPager.setAdapter(adapter);
        mDonationTabLayout.setupWithViewPager(mDonationViewPager, true);
        mDonationTabLayout.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onDonateClickListener(String amount, String description) {
        try{
            double am = Double.parseDouble(amount);
            if(am <= 0){
                new AlertDialog.Builder(DonateActivity.this)
                        .setTitle("Sorry")
                        .setMessage("You can't donate nothing or negative values.")
                        .setPositiveButton("Ok", null)
                        .show();
                mDonationViewPager.setVisibility(View.INVISIBLE);

            }else{

                Collab collab = new Collab();
                collab.setEvent(mEvent);
                collab.setCollab_by(current);
                //SessionManager sessionManager = new SessionManager(context);
                //sessionManager.getLogin(user);
                collab.setAmount(am);
                collab.setCollab_type(selectedDonationType);
                collab.setBody(description);
                EventService.getInstance().addCollab(collab, new EventService.EventServiceAddCollabCallBack() {
                    @Override
                    public void onResponse(String resp) {
                        mDonationViewPager.setCurrentItem(1);
                        mDonationTabLayout.setVisibility(View.INVISIBLE);
                        mDonationViewPager.setPagingEnabled(false);
                    }

                    @Override
                    public void onFailure(String error) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_event_details_coordinator_layout),
                                "There was an error!",
                                Snackbar.LENGTH_LONG);
                        snackbar.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onDonateClickListener(amount,description);
                            }

                        });
                    }
                });


            }
        }catch (Exception e){
            new AlertDialog.Builder(DonateActivity.this)
                    .setTitle("Warning")
                    .setMessage("Make sure to write only numbers!")
                    .setPositiveButton("Ok", null)
                    .show();
        }

    }

    @Override
    public void onCheckoutClickListener() {
        mDonationTabLayout.setVisibility(View.INVISIBLE);
        mDonationViewPager.setCurrentItem(0, true);
        mDonationViewPager.setPagingEnabled(false);
        card.setVisibility(View.GONE);

    }

    @Override
    public void onDonateBtnClickListener(DonationType selectedType) {
        selectedDonationType = selectedType;
        findViewById(R.id.activity_details_donation_card_view).setVisibility(View.VISIBLE);
        final NestedScrollView scrollView = findViewById(R.id.donate_scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
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
