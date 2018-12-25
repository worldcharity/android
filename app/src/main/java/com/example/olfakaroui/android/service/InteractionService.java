package com.example.olfakaroui.android.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.entity.Vote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractionService {

    private static InteractionService instance;

    public static InteractionService getInstance(){
        if(instance == null){
            instance = new InteractionService();
        }
        return instance;
    }

    public interface InteractionServiceRemoveCommentCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface InteractionServiceAddCommentCallBack{
        void onResponse(Comment comment);
        void onFailure(String error);
    }
    public interface InteractionServiceUpVoteEventCallBack{
        void onResponse(Vote vote);
        void onFailure(String error);
    }
    public interface InteractionServiceUpVotePostCallBack{
        void onResponse(Vote vote);
        void onFailure(String error);
    }
    public interface InteractionServiceUnVoteEventCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface InteractionServiceUnVotePostCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface InteractionServiceFavEventCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface InteractionServiceUnFavEventCallBack{
        void onResponse();
        void onFailure(String error);
    }


}
