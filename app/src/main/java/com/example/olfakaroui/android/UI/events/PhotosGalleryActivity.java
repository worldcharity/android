package com.example.olfakaroui.android.UI.events;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.PhotosAdapter;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.Photo;

public class PhotosGalleryActivity extends AppCompatActivity {

    GridView gridView;
    Event event = new Event();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_gallery);
        gridView = findViewById(R.id.gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        event= (Event) getIntent().getSerializableExtra("event");
        getSupportActionBar().setTitle(event.getName()+"'s gallery");
        PhotosAdapter photosAdapter = new PhotosAdapter(this,event.getPhotos());
        gridView.setAdapter(photosAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Photo photo = event.getPhotos().get(position);
                final ImagePopup imagePopup = new ImagePopup(PhotosGalleryActivity.this);
                imagePopup.setWindowHeight(800);
                imagePopup.setWindowWidth(800);
                imagePopup.setBackgroundColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.colorPrimary), 85));
                imagePopup.setFullScreen(true);
                imagePopup.setHideCloseIcon(false);
                imagePopup.setImageOnClickClose(true);
                imagePopup.initiatePopupWithGlide(UrlConst.IMAGES+photo.getPhoto());
                //imagePopup.initiatePopupWithPicasso(UrlConst.IMAGES+photo.getPhoto());
                imagePopup.viewPopup();


            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
