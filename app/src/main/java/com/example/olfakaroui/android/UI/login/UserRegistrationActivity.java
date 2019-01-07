package com.example.olfakaroui.android.UI.login;

import android.content.Intent;
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

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.adapter.CausesListForRegistrationAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationActivity extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    User user = new User();
    CausesListForRegistrationAdapter adapter;
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    v.setBackgroundColor(Color.WHITE);
                }
                else
                {
                    if(adapter.selectedPositions.size() < 5)
                    {
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

                    for(int x : adapter.selectedPositions)

                    {
                        Log.d("adding", "pref");
                        final Cause c = causes.get(x) ;

                        EventService.getInstance().addPref(c,user, new EventService.EventServiceAddPrefCallBack() {
                            @Override
                            public void onResponse(String string) {

                                Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(String error) {

                            }

                        });
                    }



                }
                else
                {
                    Toast.makeText(UserRegistrationActivity.this, "Please choose at least 3 preferences",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

        private void getCauses(){

            EventService.getInstance().getCauses(new EventService.EventServiceGetCausesCallBack() {
                @Override
                public void onResponse(List<Cause> posts) {

                    Animation animation = AnimationUtils.loadAnimation(UserRegistrationActivity.this,R.anim.grid_anim);
                    GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
                    gridView.setLayoutAnimation(controller);
                    causes = posts;
                    adapter = new CausesListForRegistrationAdapter(UserRegistrationActivity.this, causes);
                    gridView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String error) {

                }

            });

        }


}
