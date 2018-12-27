package com.example.olfakaroui.android.UI.events;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.UserService;
import com.example.olfakaroui.android.utils.PictureRendrer;
import com.example.olfakaroui.android.utils.SessionManager;

import java.util.List;


public class HomePageFragment extends Fragment implements EventsByCauseFragment.OnFragmentInteractionListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    LinearLayout linearLayout;

    User user = new User();
    ImageView image;
    List<Cause> prefs;
    TextView seeallevents;
    Fragment first,second,third,fourth,fifth;

    private OnFragmentInteractionListener mListener;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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

    private Fragment buildCategoryFragment(Cause cause){
        Fragment fragment = EventsByCauseFragment.newInstance(null, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EventsByCauseFragment.CATEGORY_NAME_KEY, cause);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_home_page, container, false);
        //load suggested fragment
        linearLayout = fragment.findViewById(R.id.fragment_container);
        seeallevents = fragment.findViewById(R.id.all_events_button);
        image = fragment.findViewById(R.id.banner);
        SpannableString content = new SpannableString("see all events");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        seeallevents.setText(content);
        BitmapDrawable myBackground = new BitmapDrawable(PictureRendrer.decodeSampledBitmapFromResource(getResources(), R.drawable.app_background, 100, 100));
        image.setBackgroundDrawable(myBackground);
        //final SessionManager session = new SessionManager(this.getActivity());
        //session.getLogin(user);
        user.setId(6);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        UserService.getInstance().getPrefrences(user.getId(), new UserService.UserServiceGetCallBack() {
            @Override
            public void onResponse(List<Cause> causes) {
                prefs = causes;
                first = buildCategoryFragment(prefs.get(0));
                second = buildCategoryFragment(prefs.get(1));
                third = buildCategoryFragment(prefs.get(2));
                getChildFragmentManager().beginTransaction().replace(R.id.first_container, first).commit();
                getChildFragmentManager().beginTransaction().replace(R.id.second_container, second).commit();
                getChildFragmentManager().beginTransaction().replace(R.id.third_container, third).commit();
                if(prefs.size() > 3)
                {
                    fourth = buildCategoryFragment(prefs.get(3));
                    getChildFragmentManager().beginTransaction().replace(R.id.fourth_container, fourth).commit();
                }
                if(prefs.size() == 5)
                {
                    fifth = buildCategoryFragment(prefs.get(4));
                    getChildFragmentManager().beginTransaction().replace(R.id.fifth_container, fifth).commit();
                }
            }

            @Override
            public void onFailure(String error) {

            }

        });

        seeallevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeeAllEventsActivity.class);
                getActivity().startActivity(intent);

            }
        });

        return fragment;
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        // HW layer support only exists on API 11+
        if (Build.VERSION.SDK_INT >= 11) {
            if (animation == null && nextAnim != 0) {
                animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            }

            if (animation != null) {
                getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {
                        getView().setLayerType(View.LAYER_TYPE_NONE, null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    // ...other AnimationListener methods go here...
                });
            }
        }

        return animation;
    }
}
