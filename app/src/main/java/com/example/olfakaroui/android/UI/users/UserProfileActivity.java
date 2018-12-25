package com.example.olfakaroui.android.UI.users;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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



public class UserProfileActivity extends AppCompatActivity {

    ImageView avatarView;
    TextView name,followers,following,collabs;
    RatingBar rating;
    ImageView share,follow;
    User user = new User();
    UserInfos infos = new UserInfos();
    User current = new User();
    boolean isFollowed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /*int id = getIntent().getIntExtra("user", 0);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);*/
        int id = 5;
        current.setId(6);
        avatarView = findViewById(R.id.user_avatar);
        name = findViewById(R.id.user_name);
        followers = findViewById(R.id.user_followers);
        following = findViewById(R.id.user_following);
        rating = findViewById(R.id.user_rating);
        collabs = findViewById(R.id.user_collabs);
        share = findViewById(R.id.share_user);
        follow = findViewById(R.id.follow);

        UserService.getInstance().getUser(id, new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User u) {
                user = u;
                name.setText(user.getFirstName()+ " "+ user.getLastName());
                following.setText(String.valueOf(user.getFollowing().size()));
                followers.setText(String.valueOf(user.getFollowers().size()));

                if(user.getFollowers().contains(current))
                {
                    follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_unsub));
                    isFollowed = true;
                }
                else
                {
                    follow.setImageDrawable(getResources().getDrawable(R.drawable.ic_sub));
                    isFollowed = false;
                }
                if(user.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), getResources().getColor(R.color.colorAccent));
                    avatarView.setImageDrawable(drawable);
                    //avatarView.setImageDrawable(new LetterAvatar(UserProfileActivity.this, getResources().getColor(R.color.colorAccent), user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), 20));
                }
                else
                {
                    Picasso.get().load(user.getPhoto()).noFade().resize(525, 559).centerCrop().into(avatarView);

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
                            user.getFollowers().remove(current);
                            followers.setText(String.valueOf(user.getFollowers().size()));
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
                            user.getFollowers().add(current);
                            followers.setText(String.valueOf(user.getFollowers().size()));
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
        UserService.getInstance().getInfos(id, new UserService.UserServiceGetInfosCallBack() {
            @Override
            public void onResponse(UserInfos u) {
                infos = u;
                collabs.setText(String.valueOf(infos.getCollaborations()));
                rating.setRating((infos.getRating()/100));
            }

            @Override
            public void onFailure(String error) {

            }

        });
        Log.d("user", user.getFirstName() + " followers");




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
