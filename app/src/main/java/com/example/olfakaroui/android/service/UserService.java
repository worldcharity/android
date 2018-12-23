package com.example.olfakaroui.android.service;

import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private static UserService instance;

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public interface UserServiceGetCallBack{
        void onResponse(List<Cause> prefs);
        void onFailure(String error);
    }
    public void getPrefrences(int userId, final UserServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.USER_PREFRENCES+userId,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                JSONArray array = new JSONArray(response);
                JSONObject jsonObject = (JSONObject) array.get(0);
                List<Cause> prefs = Arrays.asList(mGson.fromJson(jsonObject.getString("Prefrences"), Cause[].class));
                Log.d("Resp", response);
                callBack.onResponse(prefs);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}
