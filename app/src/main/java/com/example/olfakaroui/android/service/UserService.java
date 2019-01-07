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
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.entity.Vote;
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
    public interface UserServiceGetInfosCallBack{
        void onResponse(UserInfos infos);
        void onFailure(String error);
    }
    public interface UserServiceCheckUserCallBack{
        void onResponse(User user, boolean isAdded);
        void onFailure(String error);
    }
    public interface UserServiceGetUserCallBack{
        void onResponse(User user);
        void onFailure(String error);
    }
    public interface UserServiceGetCharitiesCallBack{
        void onResponse(List<User> users);
        void onFailure(String error);
    }
    public interface UserServiceGetCollabsCallBack{
        void onResponse(List<Collab> collabs);
        void onFailure(String error);
    }
    public interface UserServiceUnfollowCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface UserServiceLocCallBack{
        void onResponse(String s);
        void onFailure(String error);
    }
    public interface UserServiceFollowCallBack{
        void onResponse();
        void onFailure(String error);
    }

    public void addCharityLocation(int event, double lat,double lng, final UserService.UserServiceLocCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.POST, UrlConst.ADD_CHARITY_LOCATION+event+"/"+lng+"/"+lat,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        callBack.onResponse(" ");

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<String,String>();
                params.put("longitude", String.valueOf(lng));
                params.put("latitude", String.valueOf(lat));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(dr);

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
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    public void checkUser(User user, final UserServiceCheckUserCallBack callBack){
        StringRequest putRequest = new StringRequest(Request.Method.POST, UrlConst.checkUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean createdAccount = jsonObject.getBoolean("created");
                            Log.d("created", createdAccount+" ");
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            User checked = mGson.fromJson(jsonObject.getString("user"), User.class);
                            callBack.onResponse(checked,createdAccount);
                        } catch (JSONException e) {
                            callBack.onFailure(e.toString());
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onFailure(error.toString());

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("social_id", user.getSocialId());
                params.put("social_platform", user.getSocialPlatform());
                params.put("first_name", user.getFirstName());
                params.put("last_name", user.getLastName());
                params.put("photo", user.getPhoto());

                return params;
            }

        };

        putRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(putRequest);

    }


    public void getInfos(int userId, final UserServiceGetInfosCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.USER_INFOS+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("userinfooo", UrlConst.USER_INFOS+userId);
                        Log.d("userinfooo", response);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            List<UserInfos> infos = Arrays.asList(mGson.fromJson(response, UserInfos[].class));
                            callBack.onResponse(infos.get(0));


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

    public void getUser(int userId, final UserServiceGetUserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.FIND_A_USER+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<User> users = Arrays.asList(mGson.fromJson(response, User[].class));
                        callBack.onResponse(users.get(0));


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

    public void getCharities(final UserServiceGetCharitiesCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.ALL_CHARITIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<User> users = Arrays.asList(mGson.fromJson(response, User[].class));
                        callBack.onResponse(users);


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

    public void getCollabs(int userId, final UserServiceGetCollabsCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.USER_Collabs+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<Collab> collabs = Arrays.asList(mGson.fromJson(response, Collab[].class));
                        callBack.onResponse(collabs);


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

    public void unfollow(int userId, int currentUser, final UserServiceUnfollowCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.DELETE, UrlConst.UNFOLLOW+userId+"/"+currentUser,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        AppController.getInstance().addToRequestQueue(dr);

    }

    public void follow(int userId, int currentUser, String token, String name, final UserServiceFollowCallBack callBack){
        StringRequest postRequest = new StringRequest(Request.Method.POST, UrlConst.FOLLOW,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("userId", String.valueOf(userId));
                params.put("SubId", String.valueOf(currentUser));
                params.put("token", token);
                params.put("name", name);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);

    }





}
