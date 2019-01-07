package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.posts.PostsTabsFragment;
import com.example.olfakaroui.android.adapter.CausesListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.service.EventService;

import java.util.List;

public class ChooseCauseAddEventActivity extends AppCompatActivity {


    GridView gridview;
    List<Cause> lu ;
    CausesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cause_add_event);
        gridview=(GridView) findViewById(R.id.gridcauses);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.grid_anim);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        gridview.setLayoutAnimation(controller);
        EventService.getInstance().getCauses(new EventService.EventServiceGetCausesCallBack() {
            @Override
            public void onResponse(List<Cause> posts) {

                lu = posts;
                adapter = new CausesListAdapter(ChooseCauseAddEventActivity.this,posts);
                gridview.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {

            }

        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ChooseCauseAddEventActivity.this, AddEventFormActivity.class);
                intent.putExtra("cause", lu.get(position).getId());
                startActivity(intent);

            }

        });
    }
}
