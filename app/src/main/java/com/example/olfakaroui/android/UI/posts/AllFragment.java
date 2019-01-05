package com.example.olfakaroui.android.UI.posts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.PostsListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Post;
import com.example.olfakaroui.android.service.PostService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;


public class AllFragment extends Fragment {
    ListView lv ;
    PostsListAdapter adapter;
    Cause cause = new Cause();



    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_posts, container, false);
        lv = v.findViewById(R.id.postslist);
        PostService.getInstance().getPosts(cause.getId(), new PostService.PostServiceGetCallBack() {
            @Override
            public void onResponse(List<Post> posts) {

                adapter = new PostsListAdapter(getActivity(),posts);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {

            }

        });

        return v;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

}
