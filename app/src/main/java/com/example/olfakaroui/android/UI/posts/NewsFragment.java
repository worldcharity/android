package com.example.olfakaroui.android.UI.posts;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.FeedAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.RSSObject;
import com.example.olfakaroui.android.utils.HTTPDataHandler;
import com.google.gson.Gson;


public class NewsFragment extends Fragment {
   // Toolbar  toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    //RSS link
    private String RSS_link;
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    public Cause cause = new Cause();

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        RSS_link = cause.getLink();
        Log.d("link", cause.getLink());
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadRSS();
        return v;
    }
    private   void loadRSS() {
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(getContext());

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please wait...");
                //mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s) {
                //mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                if(rssObject != null)
                {
                    FeedAdapter adapter = new FeedAdapter(rssObject,getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());
    }


    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }
}
