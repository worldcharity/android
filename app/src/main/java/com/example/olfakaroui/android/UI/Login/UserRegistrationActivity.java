package com.example.olfakaroui.android.UI.Login;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.CausesListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserRegistrationActivity extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    User user = new User();
    CausesListAdapter adapter;
    private List<Cause> causes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().hide();
        gridView = findViewById(R.id.grid);
        btnGo = findViewById(R.id.confirmprefs);
        getCauses();
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
        gridView.setBackgroundResource(R.drawable.rounded_cell);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.grid_anim);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        gridView.setLayoutAnimation(controller);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    v.setBackgroundColor(Color.WHITE);
                }
                if(adapter.selectedPositions.size() < 5)
                {
                    if (selectedIndex == -1) {
                        adapter.selectedPositions.add(position);
                        v.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    }
                }

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.selectedPositions.size() >= 3)
                {
                    //Intent intent = new Intent(UserRegistrationActivity.this, SelectedItemsActivity.class);
                    //startActivity(intent);
                    //add prefs
                }
                else
                {
                    Toast.makeText(UserRegistrationActivity.this, "Please choose at least 3 preferences",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private  void addPref()
    {
        for(int x : adapter.selectedPositions)

        {
            final Cause c = causes.get(x) ;

            EventService.getInstance().addPref(c,user, new EventService.EventServiceAddPrefCallBack() {
                @Override
                public void onResponse(String string) {


                }

                @Override
                public void onFailure(String error) {

                }

            });
        }

    }

        private void getCauses(){

            EventService.getInstance().getCauses(new EventService.EventServiceGetCausesCallBack() {
                @Override
                public void onResponse(List<Cause> posts) {

                    adapter = new CausesListAdapter(UserRegistrationActivity.this, causes);
                    gridView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String error) {

                }

            });

        }


}
