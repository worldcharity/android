package com.example.olfakaroui.android.UI.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UI.events.EventLocationActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.EventsByCauseListAdapter;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.UserService;
import com.example.olfakaroui.android.SessionManager;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharityProfileActivity extends AppCompatActivity {


    ImageView avatarView;
    TextView name,followers,following,events;
    ImageView share,location;
    CheckBox follow;
    User charity = new User();
    UserInfos infos = new UserInfos();
    User current = new User();
    String token;
    boolean isFollowed = false;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager mLayoutManager;
    EventsByCauseListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_profile);

        int id = getIntent().getIntExtra("charity", 0);
        /*SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);*/
        token = SessionManager.getToken(getApplicationContext());
        //int id = 5;
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);
        avatarView = findViewById(R.id.charity_avatar);
        name = findViewById(R.id.charity_name);
        followers = findViewById(R.id.charity_followers);
        following = findViewById(R.id.charity_following);
        events = findViewById(R.id.charity_events);
        share = findViewById(R.id.share_charity);
        follow = findViewById(R.id.follow);
        location = findViewById(R.id.locate_charity);
        mRecyclerView = findViewById(R.id.charity_profile_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        UserService.getInstance().getUser(id, new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User u) {
                charity = u;
                Log.d("charity", " "+charity.getId());
                if(current.getRole().equals("charity"))
                {
                    follow.setVisibility(View.GONE);

                }
                else
                {
                    EventService.getInstance().getEventsByUser(charity.getId(), new EventService.EventServiceGetCallBack() {
                        @Override
                        public void onResponse(List<Event> events) {
                            adapter =  new EventsByCauseListAdapter(CharityProfileActivity.this,events);
                            mRecyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(String error) {

                        }

                    });
                }
                name.setText(charity.getFirstName());
                following.setText(String.valueOf(charity.getFollowing().size()));
                followers.setText(String.valueOf(charity.getFollowers().size()));
                events.setText(String.valueOf(charity.getEvents().size()));
                events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(current.getRole().equals("user"))
                        {
                            Intent intent = new Intent(CharityProfileActivity.this,CharityEventsListActivity.class);
                            intent.putExtra("user",charity);
                            startActivity(intent);
                        }

                    }
                });

                following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CharityProfileActivity.this,FollowersFollowingActivity.class);
                        intent.putExtra("liste", 0);
                        intent.putExtra("user",charity);
                        startActivity(intent);
                    }
                });

                followers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CharityProfileActivity.this,FollowersFollowingActivity.class);
                        intent.putExtra("liste", 1);
                        intent.putExtra("user",charity);
                        startActivity(intent);
                    }
                });

                events.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CharityProfileActivity.this,CharityEventsListActivity.class);
                        intent.putExtra("user",charity);
                        startActivity(intent);

                    }
                });

                if(charity.getFollowers().contains(current))
                {
                    //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                    follow.setChecked(true);
                    isFollowed = true;
                }
                else
                {
                    follow.setChecked(false);
                    //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                    isFollowed = false;
                }
                Picasso.get().load(charity.getPhoto()).resize(525, 559).centerCrop().into(avatarView);
                if(charity.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(charity.getFirstName().substring(0,1).toUpperCase(), getResources().getColor(R.color.colorAccent));
                    avatarView.setImageDrawable(drawable);
                    //avatarView.setImageDrawable(new LetterAvatar(charityProfileActivity.this, getResources().getColor(R.color.colorAccent), charity.getFirstName().substring(0,1).toUpperCase() + charity.getLastName().substring(0,1).toUpperCase(), 20));
                }

                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFollowed)
                        {
                            UserService.getInstance().unfollow(id, current.getId(), new UserService.UserServiceUnfollowCallBack() {
                                @Override
                                public void onResponse() {
                                }
                                @Override
                                public void onFailure(String error) {
                                }
                            });
                            follow.setChecked(false);
                            //follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                            charity.getFollowers().remove(current);
                            followers.setText(String.valueOf(charity.getFollowers().size()));
                            isFollowed =false;
                        }
                        else
                        {
                            UserService.getInstance().follow(id, current.getId(), token ,current.getFirstName()+ " " + current.getLastName(),new UserService.UserServiceFollowCallBack() {
                                @Override
                                public void onResponse() {
                                }

                                @Override
                                public void onFailure(String error) {
                                }
                            });
                            follow.setChecked(true);
                            //ollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                            charity.getFollowers().add(current);
                            followers.setText(String.valueOf(charity.getFollowers().size()));
                            isFollowed = true;
                        }
                    }
                });


            }

            @Override
            public void onFailure(String error) {
                Log.d("error", error);
            }

        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharityProfileActivity.this,LocateCharityActivity.class);
                intent.putExtra("charity",charity);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, charity.getFirstName());
                share.putExtra(Intent.EXTRA_TEXT, UrlConst.IMAGES+charity.getPhoto());
                CharityProfileActivity.this.startActivity(Intent.createChooser(share, "Share link!"));


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
