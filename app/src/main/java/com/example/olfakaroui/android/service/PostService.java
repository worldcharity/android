package com.example.olfakaroui.android.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class PostService {

    private static PostService instance;

    public static PostService getInstance(){
        if(instance == null){
            instance = new PostService();
        }
        return instance;
    }

    public interface PostServiceGetCallBack{
        void onResponse(List<Post> posts);
        void onFailure(String error);
    }
    public void getPosts(int causeId, final PostService.PostServiceGetCallBack callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.posts+ causeId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<Post> posts = Arrays.asList(mGson.fromJson(response, Post[].class));
                callBack.onResponse(posts);

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
    public void getPostsTrending(int causeId, final PostService.PostServiceGetCallBack callBack){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlConst.trendingPosts + causeId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<Post> posts = Arrays.asList(mGson.fromJson(response, Post[].class));
                callBack.onResponse(posts);

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
    }