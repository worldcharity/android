package com.example.olfakaroui.android.UI.events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.interfaces_for_charity.CommentsForCharityActivity;
import com.example.olfakaroui.android.UI.interfaces_for_charity.EventCollabsActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.DonationPagerAdapter;
import com.example.olfakaroui.android.adapter.DonationTypesAdapter;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.InteractionService;
import com.example.olfakaroui.android.utils.DonationViewPager;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventDetailActivity extends AppCompatActivity

        {

    private static final String TAG = "EventDetailsActivity";
    public static final String EXTRA_EVENT_ID = "event";
    private static final int RC_PERM_PHONE = 1;
    private int INDEX_OF_EVENT;

    private ImageView mImageView;
    private CollapsingToolbarLayout bar;
    private TextView mDayCountTextView;
    private TextView mHourCountTextView;
    private TextView mMinuteCountTextView;
    private TextView mSecondCountTextView;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private TextView mStartDateTextView;
    private TextView mStartTimeTextView;
    private TextView mEndDateTextView;
    private TextView mEndTimeTextView;
    private TextView mAddressTextView;
    private TextView mInfoLineButton;
    private TextView mPhotosButton;
    private TextView comments;
    private LinearLayout mExpiredLayout;
    private LinearLayout mCountLayout;
    private Button causeBtn, typeBtn;
    private boolean isLiked = false;
    private boolean isFav = false;
    private User current = new User();
    private MenuItem like,share,bookmark,donate;
    private int positionOfVote = -1;
    private int positionOfFav = -1;


    //private TabLayout mReservationTabLayout;
    //private ReservationViewPager mReservationViewPager;

    private Event mEvent;
    private CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(current);
        getViewReferences();
        getEvent();


    }

    private void getViewReferences() {
        bar = findViewById(R.id.bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        comments = findViewById(R.id.activity_event_comments);
        mImageView = findViewById(R.id.details_event_image_view);
        mNameTextView = findViewById(R.id.activity_event_details_name_text);
        mAddressTextView = findViewById(R.id.activity_event_details_address_text);
        mDescriptionTextView = findViewById(R.id.details_event_description_text);
        mInfoLineButton = findViewById(R.id.activity_event_details_infoline_text);
        mStartTimeTextView = findViewById(R.id.activity_event_details_start_date_time_text);
        mStartDateTextView = findViewById(R.id.activity_event_details_start_date_text);
        mEndTimeTextView = findViewById(R.id.activity_event_details_end_date_time_text);
        mEndDateTextView = findViewById(R.id.activity_event_details_end_date_text);
        mDayCountTextView = findViewById(R.id.activity_event_details_day_count_text);
        mHourCountTextView = findViewById(R.id.activity_event_details_hour_count_text);
        mMinuteCountTextView = findViewById(R.id.activity_event_details_minute_count_text);
        mSecondCountTextView = findViewById(R.id.activity_event_details_second_count_text);
        mExpiredLayout = findViewById(R.id.activity_event_details_expired_layout);
        mCountLayout = findViewById(R.id.activity_event_details_count_layout);
        mPhotosButton = findViewById(R.id.activity_event_details_gallery);
        causeBtn = findViewById(R.id.cause_btn);
        typeBtn =  findViewById(R.id.type_btn);



        LinearLayout infolineLayout = findViewById(R.id.activity_event_infoline_layout);
        infolineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + mEvent.getInfoline()));
                if (ActivityCompat.checkSelfPermission(EventDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EventDetailActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            RC_PERM_PHONE);
                    return;
                }
                startActivity(intent);
            }
        });
    }

    private void updateUI(){
        Picasso.get().load(UrlConst.IMAGES+mEvent.getPhotos().get(0).getPhoto()).resize(525, 559).centerCrop().into(mImageView);
        mNameTextView.setText(mEvent.getName());
        SpannableString content1;
        ArrayList<Comment> filtered = new ArrayList<>();
        for (Comment com : mEvent.getComments()) {
            if (com.getState() == 0)
            {
                filtered.add(com);

            }
        }
        mEvent.setComments(filtered);

        if(mEvent.getComments().size() > 1)
        {
            content1 = new SpannableString(mEvent.getComments().size() + " comments");

        }
        else if(mEvent.getComments().size() == 1)
        {
            content1 = new SpannableString(mEvent.getComments().size() + " comment");
        }
        else
        {
            content1 = new SpannableString("no comment");
        }
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        comments.setText(content1);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.getRole().equals("user"))
                {
                    Intent intent = new Intent(EventDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.EXTRA_EVENT_CATEGORY, mEvent);
                    startActivityForResult(intent, 1);
                }
                else
                {
                    Intent intent = new Intent(EventDetailActivity.this, CommentsForCharityActivity.class);
                    intent.putExtra(CommentsForCharityActivity.EXTRA_EVENT_CATEGORY, mEvent);
                    startActivityForResult(intent,2);
                }


            }
        });
        mPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailActivity.this, PhotosGalleryActivity.class);
                intent.putExtra("event", mEvent);
                startActivity(intent);

            }
        });
        mAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailActivity.this,EventLocationActivity.class);
                intent.putExtra("event",mEvent);
                startActivity(intent);
            }
        });

        SpannableString content = new SpannableString(getCompleteAddressString(mEvent.getLatitude(),mEvent.getLongitude()));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        mAddressTextView.setText(content);
        mDescriptionTextView.setText(mEvent.getDescription());
        content = new SpannableString(mEvent.getInfoline());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mInfoLineButton.setText(content);
        getSupportActionBar().setTitle("");
        typeBtn.setText("#"+mEvent.getType().replaceAll("\\s+","_"));

        content = new SpannableString(mEvent.getPhotos().size() + " photos");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        mPhotosButton.setText(content);
        causeBtn.setText("#"+mEvent.getCause().getName().replaceAll("\\s+","_"));


        setDate(mStartDateTextView, mEvent.getStartingDate());
        setDate(mEndDateTextView, mEvent.getEndingDate());
        setTime(mStartTimeTextView, mEvent.getStartingDate());
        setTime(mEndTimeTextView, mEvent.getEndingDate());

        long timeDiff = mEvent.getStartingDate().getTime() - new Date().getTime();
        if(timeDiff <= 0){

            mExpiredLayout.setVisibility(View.VISIBLE);
            mCountLayout.setVisibility(View.GONE);

        }else{

            setupTimer(timeDiff);

        }
        causeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.getRole().equals("user"))
                {
                Intent intent = new Intent(EventDetailActivity.this, MoreActivity.class);
                intent.putExtra(MoreActivity.EXTRA_EVENT_CATEGORY, mEvent.getCause());
                intent.putExtra("tag", 1);
                startActivity(intent);
            }}
        });

        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(current.getRole().equals("user"))
                    {
                        Intent intent = new Intent(EventDetailActivity.this, EventsByTypeSearchActivity.class);
                        intent.putExtra(EventsByTypeSearchActivity.EXTRA_EVENT_CATEGORY, mEvent.getType());
                        startActivity(intent);
                }

            }
        });

    }



    private void setupTimer(final long timeDiff) {
        cTimer = new CountDownTimer(timeDiff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / 1000 / 60 / 60 / 24;
                long hours = (millisUntilFinished - days * 24 * 60 * 60 * 1000) / 1000 / 60 / 60;
                long minutes = (millisUntilFinished - (hours + days * 24) * 60 * 60 * 1000) / 1000 / 60;
                long seconds = (millisUntilFinished - (hours *60 + days * 24 * 60 + minutes) * 60 * 1000) / 1000;
                mDayCountTextView.setText(days + "");
                mHourCountTextView.setText(hours + "");
                mMinuteCountTextView.setText(minutes + "");
                mSecondCountTextView.setText(seconds + "");
            }

            @Override
            public void onFinish() {
                mExpiredLayout.setVisibility(View.VISIBLE);
                mCountLayout.setVisibility(View.GONE);
            }
        }.start();
    }

    private void setTime(TextView textView, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        textView.setText(dateFormat.format(date));
    }

    private void setDate(TextView textView, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        textView.setText(dateFormat.format(date));
    }

    private void getEvent() {
        mEvent= (Event) getIntent().getSerializableExtra("event");
        INDEX_OF_EVENT = getIntent().getIntExtra("index", -1);
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cTimer != null) cTimer.cancel();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_interaction_bar, menu);
        if(current.getRole().equals("user"))
        {


        bookmark = menu.findItem(R.id.toolbar_bookmark);
        share = menu.findItem(R.id.toolbar_share);
        like = menu.findItem(R.id.toolbar_like);
        donate =  menu.findItem(R.id.toolbar_donate);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, mEvent.getName());
                share.putExtra(Intent.EXTRA_TEXT, UrlConst.IMAGES+mEvent.getPhotos().get(0));
                EventDetailActivity.this.startActivity(Intent.createChooser(share, "Share link!"));

                return false;
            }
        });
        long timeDiff = mEvent.getStartingDate().getTime() - new Date().getTime();
        if(timeDiff <= 0){
            donate.setVisible(false);


        }else{

            donate.setVisible(true);
        }
        int index = 0;
        while ((index < mEvent.getVotes().size()) && (!isLiked))
        {
            Vote v = mEvent.getVotes().get(index);
            if(v.getVoted_by().getId() == current.getId())
            {
                isLiked = true;
                positionOfVote = index;
            }
            index++;

        }
        index = 0;
        while ((index < mEvent.getFavBy().size()) && (!isFav))
        {
            User u = mEvent.getFavBy().get(index);
            if(u.getId() == current.getId())
            {
                isFav = true;
                positionOfFav = index;
            }
            index++;
        }
        if(isLiked)
        {
            like.setIcon(R.drawable.ic_like_selected_24dp);
        }

        if(isFav)
        {
            bookmark.setIcon(R.drawable.ic_bookmark_saved_24dp);
        }

        bookmark.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(isFav)
                {
                    mEvent.getFavBy().remove(positionOfFav);
                    unfav(mEvent,current);
                    isFav = false;
                    positionOfFav = -1;
                    bookmark.setIcon(R.drawable.ic_bookmark_24dp);
                }
                else
                {
                    addFav(mEvent,current);
                    mEvent.getFavBy().add(current);
                    isFav = true;
                    positionOfFav = mEvent.getFavBy().size() -1;
                    bookmark.setIcon(R.drawable.ic_bookmark_saved_24dp);
                }
                return false;
            }
        });
        donate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(EventDetailActivity.this, DonateActivity.class);
                intent.putExtra("event", mEvent);
                startActivity(intent);
                return false;
            }
        });
        like.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(isLiked)
                {

                    Log.d(TAG, positionOfVote + "voteeeeeeee");
                    removeVote(mEvent.getVotes().get(positionOfVote));
                    mEvent.getVotes().remove(positionOfVote);
                    isLiked = false;
                    positionOfVote = -1;
                    like.setIcon(R.drawable.ic_like_unselected_24dp);
                }
                else
                {
                    Vote vo = new Vote();
                    vo.setType("upvote");
                    vo.setEvent(mEvent);
                    vo.setVoted_by(current);
                    vo.setState(0);
                    mEvent.getVotes().add(vo);
                    addVote(vo);
                    isLiked = true;
                    positionOfVote = mEvent.getVotes().size() - 1;
                    like.setIcon(R.drawable.ic_like_selected_24dp);
                }
                return false;
            }
        });
        }
        else
        {
            bookmark = menu.findItem(R.id.toolbar_bookmark).setVisible(false);
            share = menu.findItem(R.id.toolbar_share).setVisible(false);
            like = menu.findItem(R.id.toolbar_like).setVisible(false);
            donate =  menu.findItem(R.id.toolbar_donate);
            donate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(EventDetailActivity.this, EventCollabsActivity.class);
                    intent.putExtra("event", mEvent);
                    startActivity(intent);
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RC_PERM_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mEvent.getInfoline()));
                } else {
                    Snackbar.make(findViewById(R.id.activity_event_details_coordinator_layout), "You should grant us permission to call on your behalf.", Snackbar.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    public void addVote(Vote v)
    {

        InteractionService.getInstance().voteEvent(v, new InteractionService.InteractionServiceAddVoteCallBack() {
            @Override
            public void onResponse(int vote) {
                v.setId(vote);
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void removeVote(Vote v)
    {
        InteractionService.getInstance().unvote(v, new InteractionService.InteractionServiceUnVoteCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void addFav(Event event, User user)
    {

        InteractionService.getInstance().addToFav(user.getId(), event.getId(), new InteractionService.InteractionServiceFavEventCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void unfav(Event event, User user)
    {
        InteractionService.getInstance().unfav(user.getId(), event.getId(), new InteractionService.InteractionServiceUnFavEventCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

            private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
                String strAdd = "";
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
                    if (addresses != null) {
                        Address returnedAddress = addresses.get(0);
                        StringBuilder strReturnedAddress = new StringBuilder("");

                        for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        strAdd = strReturnedAddress.toString();

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return strAdd;
            }
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 1) {

                    if(resultCode == RESULT_OK){
                        mEvent = (Event) data.getSerializableExtra("event");
                        SpannableString content1;
                        if(mEvent.getComments().size() > 1)
                        {
                            content1 = new SpannableString(mEvent.getComments().size() + " comments");

                        }
                        else if(mEvent.getComments().size() == 1)
                        {
                            content1 = new SpannableString(mEvent.getComments().size() + " comment");
                        }
                        else
                        {
                            content1 = new SpannableString("no comment");
                        }
                        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
                        comments.setText(content1);

                    }
                    if (resultCode == RESULT_CANCELED) {
                        //Do nothing?
                    }
                }
                if (requestCode == 2) {

                    if(resultCode == RESULT_OK){
                        mEvent = (Event) data.getSerializableExtra("event");
                        SpannableString content1;
                        if(mEvent.getComments().size() > 1)
                        {
                            content1 = new SpannableString(mEvent.getComments().size() + " comments");

                        }
                        else if(mEvent.getComments().size() == 1)
                        {
                            content1 = new SpannableString(mEvent.getComments().size() + " comment");
                        }
                        else
                        {
                            content1 = new SpannableString("no comment");
                        }
                        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
                        comments.setText(content1);

                    }
                    if (resultCode == RESULT_CANCELED) {
                        //Do nothing?
                    }
                }
            }

        }
