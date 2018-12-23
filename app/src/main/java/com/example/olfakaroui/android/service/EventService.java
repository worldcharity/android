package com.example.olfakaroui.android.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class EventService {

    private static EventService instance;

    public static EventService getInstance(){
        if(instance == null){
            instance = new EventService();
        }
        return instance;
    }

    public interface EventServiceGetCallBack{
        void onResponse(List<Event> events);
        void onFailure(String error);
    }
    public void getEventsByCause(int causeId, final EventServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.EVENTS_BY_CAUSE+causeId,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                Log.d("EVENTS", response);
                List<Event> events = Arrays.asList(mGson.fromJson(response, Event[].class));
                callBack.onResponse(events);

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
