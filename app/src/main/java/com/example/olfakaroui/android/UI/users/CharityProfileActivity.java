package com.example.olfakaroui.android.UI.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.service.UserService;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

public class CharityProfileActivity extends AppCompatActivity {


    ImageView avatarView;
    TextView name,followers,following,events;
    ImageView share,follow;
    User charity = new User();
    UserInfos infos = new UserInfos();
    User current = new User();
    boolean isFollowed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_profile);

        int id = getIntent().getIntExtra("charity", 0);
        /*SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);*/
        //int id = 5;
        current.setId(6);
        avatarView = findViewById(R.id.charity_avatar);
        name = findViewById(R.id.charity_name);
        followers = findViewById(R.id.charity_followers);
        following = findViewById(R.id.charity_following);
        events = findViewById(R.id.charity_events);
        share = findViewById(R.id.share_charity);
        follow = findViewById(R.id.follow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        UserService.getInstance().getUser(id, new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User u) {
                charity = u;
                name.setText(charity.getFirstName());
                following.setText(String.valueOf(charity.getFollowing().size()));
                followers.setText(String.valueOf(charity.getFollowers().size()));
                events.setText(String.valueOf(charity.getEvents().size()));

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
                    follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                    isFollowed = true;
                }
                else
                {
                    follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
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
                            follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                            charity.getFollowers().remove(current);
                            followers.setText(String.valueOf(charity.getFollowers().size()));
                            isFollowed =false;
                        }
                        else
                        {
                            UserService.getInstance().follow(id, current.getId(), new UserService.UserServiceFollowCallBack() {
                                @Override
                                public void onResponse() {
                                }

                                @Override
                                public void onFailure(String error) {
                                }
                            });
                            follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
