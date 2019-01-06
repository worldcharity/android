package com.example.olfakaroui.android.UI.Login;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfirmationPhotoActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private Button btnUpload,get;
    private Uri selectedImage;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_photo);
        getSupportActionBar().hide();
        btnUpload = findViewById(R.id.confirmation);
        get = findViewById(R.id.button_logo);

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri filePath =selectedImage;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Bitmap lastBitmap = null;
                    lastBitmap = bitmap;
                    String image = getStringImage(selectedImage);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMG);
                    }
                    else
                    {
                        SendImage(image);
                        Intent intent = new Intent(ConfirmationPhotoActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void loadImagefromGallery(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                selectedImage = data.getData();
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageURI(selectedImage);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public String getStringImage(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void SendImage( final String image) {
        final SimpleMultiPartRequest stringRequest = new SimpleMultiPartRequest(Request.Method.POST, UrlConst.confirmationPhoto +user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmationPhotoActivity.this, "No internet connection", Toast.LENGTH_LONG).show();

                    }
                });

        stringRequest.addFile("uploadfile", image);
        stringRequest.addMultipartParam("body","form-data","d");
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
