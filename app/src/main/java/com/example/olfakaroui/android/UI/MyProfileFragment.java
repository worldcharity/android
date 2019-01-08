package com.example.olfakaroui.android.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.login.LoginActivity;
import com.example.olfakaroui.android.UI.users.CharityEventsListActivity;
import com.example.olfakaroui.android.UI.users.FollowersFollowingActivity;
import com.example.olfakaroui.android.UI.users.UserCollabsListActivity;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.service.UserService;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

public class MyProfileFragment extends Fragment {


    ImageView avatarView;
    TextView name,followers,following,collabs;
    RatingBar rating;
    ImageView signout;
    User user = new User();
    UserInfos infos = new UserInfos();
    User current = new User();
    String token;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_profile, container, false);
        SessionManager sessionManager = new SessionManager(getActivity());
        sessionManager.getLogin(current);
        avatarView = view.findViewById(R.id.user_avatar);
        name = view.findViewById(R.id.user_name);
        followers = view.findViewById(R.id.user_followers);
        following = view.findViewById(R.id.user_following);
        rating = view.findViewById(R.id.user_rating);
        collabs = view.findViewById(R.id.user_collabs);
        signout = view.findViewById(R.id.signout);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logoff();

                LoginManager.getInstance().logOut();
                if(MainActivity.mGoogleApiClient != null)
                {
                    Auth.GoogleSignInApi.signOut(MainActivity.mGoogleApiClient);
                }
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        UserService.getInstance().getUser(current.getId(), new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User u) {
                user = u;

                name.setText(user.getFirstName()+ " "+ user.getLastName());
                following.setText(String.valueOf(user.getFollowing().size()));
                followers.setText(String.valueOf(user.getFollowers().size()));
                following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),FollowersFollowingActivity.class);
                        intent.putExtra("liste", 0);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });

                followers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),FollowersFollowingActivity.class);
                        intent.putExtra("liste", 1);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });
                collabs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(current.getRole().equals("user"))
                        {
                            Intent intent = new Intent(getActivity(),UserCollabsListActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                        }
                        if(current.getRole().equals("charity"))
                        {
                            Intent intent = new Intent(getActivity(),CharityEventsListActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                        }

                    }
                });

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
                if(current.getRole().equals("charity"))
                {
                    collabs.setText(String.valueOf(user.getEvents().size()));
                }


            }

            @Override
            public void onFailure(String error) {
                Log.d("error", error);
            }

        });
        if(current.getRole().equals("user"))
        {
        UserService.getInstance().getInfos(current.getId(), new UserService.UserServiceGetInfosCallBack() {
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
        }
        if(current.getRole().equals("charity"))
        {
            rating.setVisibility(View.GONE);

        }



        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
