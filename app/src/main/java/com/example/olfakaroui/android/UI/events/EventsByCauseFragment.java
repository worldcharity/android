package com.example.olfakaroui.android.UI.events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.adapter.EventsByCauseListAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventsByCauseFragment extends Fragment {  private static final String TAG = EventsByCauseFragment.class.getSimpleName();


    public static final String CATEGORY_NAME_KEY = "category";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EventsByCauseFragment() {
        // Required empty public constructor
    }


    public static EventsByCauseFragment newInstance(String param1, String param2) {
        EventsByCauseFragment fragment = new EventsByCauseFragment();
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

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventsByCauseListAdapter adapter;
    private Cause mCause;
    private List<Event> allevents = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_events_by_cause, container, false);


        Bundle bundle = getArguments();
        mCause = (Cause) bundle.getSerializable(CATEGORY_NAME_KEY);

        TextView categoryNameTV = fragment.findViewById(R.id.cause_name);
        categoryNameTV.setText(mCause.getName());
        fragment.findViewById(R.id.cause_more_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoreActivity.class);
                intent.putExtra(MoreActivity.EXTRA_EVENT_CATEGORY, mCause);
                startActivity(intent);
            }
        });

        mRecyclerView = fragment.findViewById(R.id.cause_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new EventsByCauseListAdapter(allevents);
        mRecyclerView.setAdapter(adapter);
        EventService.getInstance().getEventsByCause(mCause.getId(), new EventService.EventServiceGetCallBack() {
            @Override
            public void onResponse(List<Event> events) {
                allevents = events;
                adapter.listData = events;
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, error);
               

            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Event event = allevents.get(position);
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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
