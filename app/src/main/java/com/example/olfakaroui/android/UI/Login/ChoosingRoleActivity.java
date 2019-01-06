package com.example.olfakaroui.android.UI.Login;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.utils.PictureRendrer;

import java.util.HashMap;
import java.util.Map;

public class ChoosingRoleActivity extends AppCompatActivity {

    LinearLayout mainView;
    Button userBtn,charityBtn;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_role);
        getSupportActionBar().hide();
        mainView = findViewById(R.id.choosing_role_view);
        BitmapDrawable myBackground = new BitmapDrawable(PictureRendrer.decodeSampledBitmapFromResource(getResources(), R.drawable.app_background, 100, 100));
        mainView.setBackgroundDrawable(myBackground);
        userBtn =  findViewById(R.id.continue_as_user);
        charityBtn =  findViewById(R.id.continue_as_charity);
        final SessionManager session = new SessionManager(this);
        session.getLogin(user);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setRole(getResources().getString(R.string.user));
                session.setRole(user);
                editRole();
                Intent intent = new Intent(ChoosingRoleActivity.this, UserRegistrationActivity.class);
                startActivity(intent);

            }
        });
        charityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setRole(getResources().getString(R.string.charity));
                session.setRole(user);
                editRole();
                Intent intent = new Intent(ChoosingRoleActivity.this, CharityRegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void editRole()
    {
        String url = UrlConst.editRole;
        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getId()));
                params.put("role", user.getRole());

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(putRequest);

    }
}
