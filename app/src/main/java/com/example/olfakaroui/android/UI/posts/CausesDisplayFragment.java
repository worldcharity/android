package com.example.olfakaroui.android.UI.posts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.CausesListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.service.EventService;
import java.util.List;


public class CausesDisplayFragment extends Fragment {


    GridView gridview;
    List<Cause> lu ;
    CausesListAdapter adapter;
    public CausesDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_causes_for_posts, container, false);
        this.getActivity().setTitle("Causes");
        gridview=(GridView) v.findViewById(R.id.gridcauses);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.grid_anim);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
        gridview.setLayoutAnimation(controller);
        EventService.getInstance().getCauses(new EventService.EventServiceGetCausesCallBack() {
            @Override
            public void onResponse(List<Cause> posts) {

                lu = posts;
                adapter = new CausesListAdapter(getContext(),posts);
                gridview.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {

            }

        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                PostsTabsFragment detailFragment = new PostsTabsFragment();
                getActivity().getIntent().putExtra("cause", lu.get(position));
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, detailFragment);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }

        });

        return  v;
    }


}
