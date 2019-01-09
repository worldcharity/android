package com.example.olfakaroui.android.UI.events;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.adapter.CommentListAdapter;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.InteractionService;

public class CommentsActivity extends AppCompatActivity {
    Event event;
    User user = new User();
    private static final String TAG = "CommentActivity";

    public static final String EXTRA_EVENT_CATEGORY = "event";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT_CATEGORY);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
        RecyclerView recyclerView = findViewById(R.id.comments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText commentaire = findViewById(R.id.add_comment);
        ImageView commentBtn = findViewById(R.id.add_comment_btn);
        final CommentListAdapter adapter = new CommentListAdapter(event.getComments(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Suggestions on "+ event.getName());

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!commentaire.getText().toString().trim().isEmpty())
                    {
                        Comment comment = new Comment();
                        comment.setBody(commentaire.getText().toString());
                        comment.setEvent(event);
                        comment.setState(0);
                        comment.setPosted_by(user);
                        InteractionService.getInstance().commentEvent(comment, new InteractionService.InteractionServiceAddCommentCallBack() {
                            @Override
                            public void onResponse(Comment c) {
                                event.getComments().add(c);
                                commentaire.getText().clear();
                                adapter.listData = event.getComments();
                                adapter.notifyItemInserted(event.getComments().size());
                            }

                            @Override
                            public void onFailure(String error) {
                            }

                        });

                    }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("event", event);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
