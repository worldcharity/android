package com.example.olfakaroui.android.UI.events;

import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.CommentListAdapter;
import com.example.olfakaroui.android.adapter.MoreEventAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.service.EventService;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    Event event;
    private static final String TAG = "CommentActivity";

    public static final String EXTRA_EVENT_CATEGORY = "event";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT_CATEGORY);
        RecyclerView recyclerView = findViewById(R.id.comments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextInputEditText commentaire = findViewById(R.id.add_comment);
        ImageView commentBtn = findViewById(R.id.add_comment_btn);
        final CommentListAdapter adapter = new CommentListAdapter(event.getComments());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Suggestions on "+ event.getName());

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
