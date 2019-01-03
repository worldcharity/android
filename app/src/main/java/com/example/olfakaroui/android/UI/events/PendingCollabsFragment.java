package com.example.olfakaroui.android.UI.events;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.adapter.PendingCollabsAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.utils.PictureRendrer;

import java.util.List;


public class PendingCollabsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    ImageView image;
    List<Cause> prefs;
    TextView addevent;
    User user = new User();
    RecyclerView mList;
    PendingCollabsAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public PendingCollabsFragment() {
        // Required empty public constructor
    }

    public static PendingCollabsFragment newInstance(String param1, String param2) {
        PendingCollabsFragment fragment = new PendingCollabsFragment();
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
        View fragment = inflater.inflate(R.layout.fragment_pending_collabs, container, false);
        //load suggested fragment
        addevent = fragment.findViewById(R.id.add_event_button);
        image = fragment.findViewById(R.id.banner_collab);
        mList = fragment.findViewById(R.id.pendingcollabs);
        SpannableString content = new SpannableString("add a new event");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        addevent.setText(content);
        BitmapDrawable myBackground = new BitmapDrawable(PictureRendrer.decodeSampledBitmapFromResource(getResources(), R.drawable.app_background, 100, 100));
        image.setBackgroundDrawable(myBackground);
        //final SessionManager session = new SessionManager(this.getActivity());
        //session.getLogin(user);
        user.setId(6);
        EventService.getInstance().getPendingCollabs(user.getId(), new EventService.EventServiceGetPendingCollabsCallBack() {
            @Override
            public void onResponse(List<Collab> collabs) {
                adapter = new PendingCollabsAdapter(collabs, getActivity());
                mList.setNestedScrollingEnabled(false);
                mList.setLayoutManager(new LinearLayoutManager(getActivity()));
                mList.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {

            }

        });

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
