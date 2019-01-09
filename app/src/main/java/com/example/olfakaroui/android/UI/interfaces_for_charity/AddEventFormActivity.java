package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEventFormActivity extends AppCompatActivity {

    private int id = 1 ;
    private Button confirm;
    TextInputEditText name,desc,info,start,end;
    Spinner sp ;
    String url;
    Event event;
    User user = new User();
    int cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_form);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        b.getInt("causeId");
        name = (TextInputEditText)findViewById(R.id.nameevfo);
        desc = (TextInputEditText)findViewById(R.id.descevfo);
        info = (TextInputEditText)findViewById(R.id.infevfo);
        start = (TextInputEditText)findViewById(R.id.startevfo);
        end = (TextInputEditText)findViewById(R.id.endevfo);
        sp = (Spinner)findViewById(R.id.spinevfo);
        confirm = (Button)findViewById(R.id.Confirm_event_1);
        cause = getIntent().getIntExtra("cause",0);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check(name,desc,info,start,end))
                {
                    String st = start.getText().toString();
                    String en = end.getText().toString();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    try {
                        Date date_start = format.parse(st);
                        Date date_end = format.parse(en);
                        if(date_end.after(date_start))
                        {
                            Log.d("ok", "ok");
                            event = new Event();
                            event.setDescription(desc.getText().toString());
                            event.setName(name.getText().toString());
                            event.setInfoline(info.getText().toString());
                            event.setStartingDate(date_start);
                            event.setEndingDate(date_end);
                            event.setType(sp.getSelectedItem().toString());
                            SessionManager sessionManager = new SessionManager(AddEventFormActivity.this);
                            sessionManager.getLogin(user);
                            EventService.getInstance().addEvent(event,cause, user.getId(), new EventService.EventServiceAddEventCallBack() {
                                @Override
                                public void onResponse(Event event1) {
                                    event = event1;
                                    Intent intent = new Intent(AddEventFormActivity.this, AddPicturesEventActivity.class);
                                    intent.putExtra("event", event1.getId());
                                    startActivity(intent);

                                }

                                @Override
                                public void onFailure(String error) {
                                }

                            });

                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("notok", "notok");
                    }


                }
                else{

                }
            }
        });
    }
    public boolean check(TextInputEditText n ,TextInputEditText d,TextInputEditText i,TextInputEditText s,TextInputEditText e )
    {
        return  (!n.getText().toString().trim().isEmpty()
                && !s.getText().toString().trim().isEmpty()
                && !d.getText().toString().trim().isEmpty()
                && !e.getText().toString().trim().isEmpty()
                && !i.getText().toString().trim().isEmpty());

    }
    public void sendData()
    {

    }
}
