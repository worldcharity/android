package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.CommentListAdapter;
import com.example.olfakaroui.android.adapter.CommentListForCharityAdapter;
import com.example.olfakaroui.android.adapter.PendingCollabsAdapter;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.InteractionService;
import com.example.olfakaroui.android.utils.SwipeItemHelper;
import com.example.olfakaroui.android.utils.SwipeItemToHideHelper;

public class CommentsForCharityActivity extends AppCompatActivity implements SwipeItemToHideHelper.SwipeItemHelperListener{

    Event event;
    User user = new User();
    CommentListForCharityAdapter adapter;
    private static final String TAG = "CommentActivity";

    public static final String EXTRA_EVENT_CATEGORY = "event";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_for_charity);
        event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT_CATEGORY);
        user.setId(6);
        //SessionManager sessionManager = new SessionManager(this);
        //sessionManager.getLogin(user);
        RecyclerView recyclerView = findViewById(R.id.comments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommentListForCharityAdapter(event.getComments());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Suggestions on "+ event.getName());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new SwipeItemToHideHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CommentListForCharityAdapter.ViewHolder) {

            if(direction == ItemTouchHelper.LEFT)
            {
                adapter.removeItem(viewHolder.getAdapterPosition());

            }

        }
    }
}
