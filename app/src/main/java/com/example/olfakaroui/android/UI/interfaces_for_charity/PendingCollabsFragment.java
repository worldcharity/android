package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.adapter.PendingCollabsAdapter;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.utils.PictureRendrer;
import com.example.olfakaroui.android.utils.SwipeItemHelper;
import com.example.olfakaroui.android.utils.SwipeItemToDenyHelper;

import java.util.List;


public class PendingCollabsFragment extends Fragment implements SwipeItemHelper.SwipeItemHelperListener, SwipeItemToDenyHelper.SwipeItemHelperListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    ImageView image;
    TextView addevent;
    User user = new User();
    RecyclerView mList;
    List<Collab> col;
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
        mList.setNestedScrollingEnabled(false);
        SpannableString content = new SpannableString("add a new event");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        addevent.setText(content);
        BitmapDrawable myBackground = new BitmapDrawable(PictureRendrer.decodeSampledBitmapFromResource(getResources(), R.drawable.app_background, 100, 100));
        image.setBackgroundDrawable(myBackground);
        SessionManager session = new SessionManager(this.getActivity());
        session.getLogin(user);
        EventService.getInstance().getPendingCollabs(user.getId(), new EventService.EventServiceGetPendingCollabsCallBack() {
            @Override
            public void onResponse(List<Collab> collabs) {
                col = collabs;
                adapter = new PendingCollabsAdapter(col, getActivity());
                mList.setNestedScrollingEnabled(false);
                mList.setLayoutManager(new LinearLayoutManager(getActivity()));
                mList.setAdapter(adapter);

            }

            @Override
            public void onFailure(String error) {

            }

        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new SwipeItemHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mList);
        ItemTouchHelper.SimpleCallback itemTouchHelper= new SwipeItemToDenyHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mList);


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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PendingCollabsAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            // remove the item from recycler view
            if(direction == ItemTouchHelper.LEFT)
            {


                adapter.removeItem(viewHolder.getAdapterPosition());

            }
            if(direction == ItemTouchHelper.RIGHT)
            {

                adapter.denyItem(viewHolder.getAdapterPosition());

            }


        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
