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
import com.example.olfakaroui.android.entity.Event;
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
    public interface InteractionServiceAddVoteCallBack{
        void onResponse(int vote);
        void onFailure(String error);
    }
    public interface InteractionServiceUpdateVoteCallBack{
        void onResponse();
        void onFailure(String error);
    }
    public interface InteractionServiceUnVoteCallBack{
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

    public void commentEvent(Comment comment, final InteractionService.InteractionServiceAddCommentCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.POST, UrlConst.COMMENT_EVENT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<Comment> comments = Arrays.asList(mGson.fromJson(response, Comment[].class));
                        callBack.onResponse(comments.get(0));

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
                params.put("id_event", String.valueOf(comment.getEvent().getId()));
                params.put("id_user", String.valueOf(comment.getPosted_by().getId()));
                params.put("body", comment.getBody());
                params.put("state", String.valueOf(comment.getState()));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(dr);

    }

    public void commentPost(Comment comment, final InteractionService.InteractionServiceAddCommentCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.POST, UrlConst.COMMENT_POST,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<Comment> comments = Arrays.asList(mGson.fromJson(response, Comment[].class));
                        callBack.onResponse(comments.get(0));

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
                params.put("id_post", String.valueOf(comment.getPost().getId()));
                params.put("id_user", String.valueOf(comment.getPosted_by().getId()));
                params.put("body", comment.getBody());
                params.put("state", String.valueOf(comment.getState()));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(dr);

    }

    public void voteComment(Vote v, final InteractionService.InteractionServiceAddVoteCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, UrlConst.addVote,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Vote newVote = mGson.fromJson(response, Vote.class);
                        callBack.onResponse(newVote.getId());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", v.getType());
                params.put("id_comment", String.valueOf(v.getComment().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void unvote(Vote v, final InteractionService.InteractionServiceUnVoteCallBack callBack) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, UrlConst.updateVote+"/"+v.getId(),
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

    public void updateVoteComment(Vote v, final InteractionService.InteractionServiceUpdateVoteCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, UrlConst.updateVote+"/"+v.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("type", v.getType());
                params.put("id", String.valueOf(v.getId()));
                params.put("id_comment", String.valueOf(v.getComment().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }
    public void hideComment(Comment v, final InteractionService.InteractionServiceUpdateVoteCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, UrlConst.HIDE_COMMENT+v.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void voteEvent(Vote v, final InteractionService.InteractionServiceAddVoteCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, UrlConst.addVoteEvent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Vote newVote = mGson.fromJson(response, Vote.class);
                        callBack.onResponse(newVote.getId());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "upvote");
                params.put("id_event", String.valueOf(v.getEvent().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void votePost(Vote v, final InteractionService.InteractionServiceAddVoteCallBack callBack) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, UrlConst.addVotePost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Vote newVote = mGson.fromJson(response, Vote.class);
                        callBack.onResponse(newVote.getId());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "upvote");
                params.put("id_post", String.valueOf(v.getPost().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void deleteComment(Comment comment, final InteractionService.InteractionServiceRemoveCommentCallBack callBack) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, UrlConst.DELETE_COMMENT+comment.getId(),
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

    public void unfav(int userId, int event, final InteractionServiceUnFavEventCallBack callBack){
        StringRequest dr = new StringRequest(Request.Method.DELETE, UrlConst.UNFAV+userId+"/"+event,
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

    public void addToFav(int userId, int event, final InteractionServiceFavEventCallBack callBack){
        StringRequest postRequest = new StringRequest(Request.Method.POST, UrlConst.FAV,
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
                params.put("eventId", String.valueOf(event));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);

    }


}
