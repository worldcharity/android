package com.example.olfakaroui.android.UI.posts;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.interfaces_for_charity.AddEventFormActivity;
import com.example.olfakaroui.android.UI.interfaces_for_charity.AddPicturesEventActivity;
import com.example.olfakaroui.android.adapter.PagerAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.Post;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.PostService;


public class PostsTabsFragment extends Fragment {

    Cause cause;
    ViewPager viewPager;
    TabLayout tabLayout;

    public PostsTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //getActivity().setActionBar(toolbar);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Trending"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        toolbar.inflateMenu(R.menu.post);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        Intent i = getActivity().getIntent();
        cause  = (Cause) i.getSerializableExtra("cause");
        adapter.setCause(cause);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post :
            {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.add_post_dialog);
                dialog.setTitle("Share your thoughts");
                TextInputEditText title = dialog.findViewById(R.id.title_add);
                TextInputEditText body = dialog.findViewById(R.id.body_add);
                Button cancel = dialog.findViewById(R.id.cancel_add);
                Button add = dialog.findViewById(R.id.post_add);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().trim().isEmpty() && !body.getText().toString().trim().isEmpty())
                        {
                            Post post = new Post();
                            post.setBody(body.getText().toString());
                            post.setTitle(title.getText().toString());
                            post.setCause(cause);
                            User user = new User();
                            SessionManager sessionManager = new SessionManager(getActivity());
                            sessionManager.getLogin(user);
                            post.setUser(user);
                            PostService.getInstance().addPost(post,new PostService.PostServiceAddCallBack() {
                                @Override
                                public void onResponse(Post p) {
                                    PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
                                    adapter.setCause(cause);
                                    viewPager.setAdapter(adapter);
                                    Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                                    intent.putExtra("post", p);
                                    dialog.dismiss();
                                    getActivity().startActivity(intent);

                                }

                                @Override
                                public void onFailure(String error) {
                                }

                            });

                        }
                        else
                        {
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }

                return true;
            }

        return super.onOptionsItemSelected(item);
    }

}
