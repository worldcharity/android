package com.example.olfakaroui.android.UI.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Event;

public class DonateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DonateFragment() {
        // Required empty public constructor
    }
    public interface DonateCallBack{
        void onDonateClickListener(String amount, String description);
    }
    private DonateCallBack mCallBack;


    public static DonateFragment newInstance(Event event, DonateCallBack callBack) {

        Bundle args = new Bundle();
        args.putSerializable("event", event);
        DonateFragment fragment = new DonateFragment();
        fragment.setArguments(args);
        fragment.setCallBack(callBack);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        Event event = (Event) getArguments().getSerializable("event");
        TextInputEditText amount = view.findViewById(R.id.fragment_donation_amount);
        TextInputEditText description = view.findViewById(R.id.fragment_donation_desc);
        Button addButton = view.findViewById(R.id.fragment_donation_donate);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onDonateClickListener(amount.getText().toString(), description.getText().toString());

            }
        });

        return view;
    }

    public void setCallBack(DonateCallBack callBack) {
        mCallBack = callBack;
    }
}
