package com.example.olfakaroui.android.UI.Login;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.olfakaroui.android.adapter.ListAdapter;
import com.example.olfakaroui.android.entity.Cause;
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
    private List<Cause> selectedCauses;
    ListAdapter adapter;
    private List<Cause> causes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().hide();
        gridView = findViewById(R.id.grid);
        btnGo = findViewById(R.id.confirmprefs);
        getCauses();
        gridView.setBackgroundResource(R.drawable.rounded_cell);
        selectedCauses = new ArrayList<>();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    v.setBackgroundColor(Color.WHITE);
                } else {
                    adapter.selectedPositions.add(position);
                    v.setBackgroundColor(getResources().getColor(R.color.colorBackground));
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
                    Toast.makeText(UserRegistrationActivity.this, "Please choose 3 preferences or more",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /*private  void addPref()
    {
        for(  Cause c : ListAdapter.lc )
        { final Cause c1 = c ;

            StringRequest sr = new StringRequest(Request.Method.POST, prefs, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FirstLogUserActivity.this, "error someweher in the request", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    HashMap<String, String> params2 = new HashMap<String, String>();
                    params2.put("id_user", Integer.toString(user.getId()));
                    params2.put("id_cause", Integer.toString(c1.getId()));
                    return new JSONObject(params2).toString().getBytes();
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }


            };

            AppController.getInstance().addToRequestQueue(sr);
        }
    }
    */
    private void getCauses(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.allCauses, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("okay", "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                causes = Arrays.asList(mGson.fromJson(response, Cause[].class));
                adapter = new ListAdapter(UserRegistrationActivity.this, causes);
                gridView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hell no", "Error " + error.getMessage());

            }
        }
        );
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}
