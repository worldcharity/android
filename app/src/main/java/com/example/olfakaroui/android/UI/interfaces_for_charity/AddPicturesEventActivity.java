package com.example.olfakaroui.android.UI.interfaces_for_charity;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.adapter.GalleryAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPicturesEventActivity extends AppCompatActivity {

    private Button btn,conf;
    private  static int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    int wehedwalabarcha=0;
    Uri selectedImage;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures_event);

        btn = findViewById(R.id.btn);
        gvGallery = (GridView)findViewById(R.id.gv);
        conf = findViewById(R.id.confirmphotos);
        eventId = getIntent().getIntExtra("event", 0);
        imagesEncodedList = new ArrayList<>();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select the event images "), PICK_IMAGE_MULTIPLE);
            }
        });
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* for (String s : imagesEncodedList)
                {  //  Log.d("el image ",s);
                    //SendImage(s);
                }*/
                if(wehedwalabarcha==0)
                {
                    Uri filePath =selectedImage;
                    try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        Bitmap lastBitmap = null;
                        lastBitmap = bitmap;
                        //encoding image to string


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_MULTIPLE);
                        }
                        else
                        {
                            SendImage(imageEncoded);
                            Intent intent = new Intent(AddPicturesEventActivity.this, AddDonationTypesActivity.class);
                            intent.putExtra("event", eventId);
                            startActivity(intent);
                        }

                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else
                {    for(String filePath : imagesEncodedList)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_MULTIPLE);
                    }
                    else
                    {
                        SendImage(filePath);

                    }
                }
                    Intent intent = new Intent(AddPicturesEventActivity.this, AddDonationTypesActivity.class);
                    intent.putExtra("event", eventId);
                    startActivity(intent);


                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {


                if(data.getData()!=null){
                    wehedwalabarcha=0;
                    Log.d("pos","fi west l 1");
                    Uri mImageUri=data.getData();
                    Log.d("el uri ",mImageUri+"");
                    selectedImage=mImageUri;

                    // Will return "image:x*"
                    String wholeID = DocumentsContract.getDocumentId(mImageUri);
                    // Split at colon, use second item in the array
                    String id = wholeID.split(":")[1];
                    String[] column = { MediaStore.Images.Media.DATA };
                    // where id is equal to
                    String sel = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = getContentResolver().
                            query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    column, sel, new String[]{ id }, null);
                    String filePath = "";
                    int columnIndex = cursor.getColumnIndex(column[0]);

                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(columnIndex);
                        imageEncoded=filePath;
                    }

                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        wehedwalabarcha=1;
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            String wholeID = DocumentsContract.getDocumentId(uri);
                            String id = wholeID.split(":")[1];
                            String[] column = { MediaStore.Images.Media.DATA };
                            String sel = MediaStore.Images.Media._ID + "=?";
                            Cursor cursor = getContentResolver().
                                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            column, sel, new String[]{ id }, null);
                            String filePath = "";
                            int columnIndex = cursor.getColumnIndex(column[0]);

                            if (cursor.moveToFirst()) {
                                filePath = cursor.getString(columnIndex);
                                imagesEncodedList.add(filePath);

                            }

                            cursor.close();
                            mArrayUri.add(uri);

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked an Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void SendImage( final String image) {
        final SimpleMultiPartRequest stringRequest = new SimpleMultiPartRequest(Request.Method.POST, UrlConst.ADD_PIC+eventId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("uploade",response);
                     /*   try {
                            //JSONObject jsonObject = new JSONObject(response);
                            String responsea = response;

                        }
                     catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        stringRequest.addFile("uploadfile", image);
        Log.d("imageeee","hhh");
        //  stringRequest.add("","parametre", )
        stringRequest.addMultipartParam("body","form-data","d");
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}

