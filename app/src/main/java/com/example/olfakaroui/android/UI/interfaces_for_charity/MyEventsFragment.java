package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.adapter.MyEventsAdapter;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;

import java.util.ArrayList;
import java.util.List;


public class MyEventsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    User user = new User();

    private OnFragmentInteractionListener mListener;

    public MyEventsFragment() {
        // Required empty public constructor
    }

   public static MyEventsFragment newInstance(String param1, String param2) {
        MyEventsFragment fragment = new MyEventsFragment();
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
        SessionManager sessionManager = new SessionManager(getActivity());
        sessionManager.getLogin(user);
        View view =  inflater.inflate(R.layout.fragment_my_events, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.myeventslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final List<Event> events = new ArrayList<>();
        final MyEventsAdapter adapter = new MyEventsAdapter(events, getActivity());
        recyclerView.setAdapter(adapter);
        EventService.getInstance().getEventsByUser(user.getId(), new EventService.EventServiceGetCallBack() {
            @Override
            public void onResponse(List<Event> events) {
                adapter.mEvents = events;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {


            }

        });
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
