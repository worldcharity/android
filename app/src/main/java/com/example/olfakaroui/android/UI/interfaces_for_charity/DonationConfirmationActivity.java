package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.DonationConfirmationAdapter;
import com.example.olfakaroui.android.service.EventService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DonationConfirmationActivity extends AppCompatActivity implements Serializable {
    List<Integer> name ;
    List<String> don ;
    RecyclerView rv ;
    int event;
    DonationConfirmationAdapter adapter;
    final Context context = this;
    HashMap<Integer,String> nameandgoal;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_confirmation);
        name = new ArrayList<>();
        Intent i=getIntent();
        confirm = findViewById(R.id.confirmdonationeventfinal);
        nameandgoal =(HashMap<Integer, String>) i.getSerializableExtra("goals");
        name = ((Intent) i).getIntegerArrayListExtra("ids");
        event = ((Intent) i).getIntExtra("event", 0);
        don = ((Intent) i).getStringArrayListExtra("don");
        rv = (RecyclerView) findViewById(R.id.rvdonconf);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DonationConfirmationAdapter(context,nameandgoal,name,don);
        rv.setAdapter(adapter);
        confirm.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           for(Integer id : name)
                                           {
                                               EventService.getInstance().addDonationEvent(event, id, nameandgoal.get(id), new EventService.EventServiceAddDonationCallBack() {
                                                   @Override
                                                   public void onResponse(String c) {

                                                   }

                                                   @Override
                                                   public void onFailure(String error) {
                                                   }

                                               });

                                           }
                                           Intent intent = new Intent(DonationConfirmationActivity.this, AddEventLocationActivity.class);
                                           intent.putExtra("event", event);
                                           startActivity(intent);
                                       }
                                           }
        );
        registerForContextMenu(rv);

    }

}
