package com.example.olfakaroui.android.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public interface EventServiceGetPendingCollabsCallBack{
        void onResponse(List<Collab> collabs);
        void onFailure(String error);
    }
    public interface EventServiceConfirmOrRefuseCollabCallBack{
        void onResponse(String response);
        void onFailure(String error);
    }
    public interface EventServiceGetTotalCallBack{
        void onResponse(double total);
        void onFailure(String error);
    }
    public interface EventServiceGetCausesCallBack{
        void onResponse(List<Cause> causes);
        void onFailure(String error);
    }
    public interface EventServiceAddPrefCallBack{
        void onResponse(String response);
        void onFailure(String error);
    }

    public interface EventServiceAddCollabCallBack{
        void onResponse(String responses);void onFailure(String error);
    }
    public void getEventsByCause(int causeId, final EventServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.EVENTS_BY_CAUSE+causeId,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                Log.d("EVENTS", response);
                List events = new ArrayList(Arrays.asList(mGson.fromJson(response, Event[].class)));
                callBack.onResponse(events);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    public void getEventsByType(String type, final EventServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.EVENTS_BY_TYPE+type,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Log.d("EVENTS", response);
                        List events = new ArrayList(Arrays.asList(mGson.fromJson(response, Event[].class)));
                        callBack.onResponse(events);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void getCauses(final EventServiceGetCausesCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.allCauses,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<Cause> causes = Arrays.asList(mGson.fromJson(response, Cause[].class));
                        callBack.onResponse(causes);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void getEventsByUser(int userId, final EventServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.CHARITY_EVENTS+userId,
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
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    public void getAllEvents(final EventServiceGetCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.ALL_EVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Log.d("EVENTS", response);
                        List events = new ArrayList(Arrays.asList(mGson.fromJson(response, Event[].class)));
                        callBack.onResponse(events);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void addCollab(Collab collab, final EventService.EventServiceAddCollabCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.POST, UrlConst.ADD_COLLAB,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        callBack.onResponse("");

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onResponse("");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_event", String.valueOf(collab.getEvent().getId()));
                params.put("id_user", String.valueOf(collab.getCollab_by().getId()));
                params.put("id_type", String.valueOf(collab.getCollab_type().getId()));
                params.put("body", collab.getBody());
                params.put("amount", String.valueOf(collab.getAmount()));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(dr);

    }

    public void getTotal(int typeId, int eventId, final EventServiceGetTotalCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.TOTAL_COLLAB+eventId+"/"+typeId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                                JSONObject jresponse =
                                        jsonArray.getJSONObject(0);
                                double total =
                                        jresponse.getDouble("total");
                            callBack.onResponse(total);

                        } catch (JSONException e) {
                            e.printStackTrace();



                    }}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    public void getPendingCollabs(int userId, final EventServiceGetPendingCollabsCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.PENDING_COLLABS+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Log.d("collabs", response);

                        List events = new ArrayList(Arrays.asList(mGson.fromJson(response, Collab[].class)));
                        callBack.onResponse(events);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());

            }
        }
        );
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void confirmCollab(Collab c, final EventService.EventServiceConfirmOrRefuseCollabCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, UrlConst.UPDATE_COLLAB+c.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        callBack.onResponse(" ");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                        callBack.onResponse(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("state", String.valueOf(c.getState()));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void addPref(Cause c, User user, final EventService.EventServiceAddPrefCallBack callBack){

        StringRequest sr = new StringRequest(Request.Method.POST, UrlConst.ADD_PREFS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("id_user", Integer.toString(user.getId()));
                params2.put("id_cause", Integer.toString(c.getId()));
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
