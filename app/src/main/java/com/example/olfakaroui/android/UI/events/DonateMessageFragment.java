package com.example.olfakaroui.android.UI.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.olfakaroui.android.R;


public class DonateMessageFragment extends Fragment {
    public interface DonateMessageCallback{
        void onCheckoutClickListener();
    }

    private DonateMessageCallback mCallBack;

    public static DonateMessageFragment newInstance(DonateMessageCallback callBack) {

        Bundle args = new Bundle();
        DonateMessageFragment fragment = new DonateMessageFragment();
        fragment.setArguments(args);
        fragment.setCallBack(callBack);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_message, container, false);

        Button checkout = view.findViewById(R.id.fragment_checkout_button);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onCheckoutClickListener();
            }
        });

        return view;
    }

    public void setCallBack(DonateMessageCallback callBack) {
        mCallBack = callBack;
    }
}