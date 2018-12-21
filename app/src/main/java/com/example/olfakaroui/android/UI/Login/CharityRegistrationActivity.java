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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.utils.SessionManager;

import java.io.IOException;

public class CharityRegistrationActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private Uri selectedImage;
    private static final String TAG = CharityRegistrationActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private String filePath = null;
    private TextView txtPercentage;
    private ImageView imgPreview;
    private EditText name,description;
    private Button btnUpload;
    long totalSize = 0;
    Button back;
    User user = new User();
    private String url = UrlConst.userModif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_registration);
        getSupportActionBar().hide();
        name = findViewById(R.id.name_charity_reg);
        description = findViewById(R.id.description_charity_reg);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.getLogin(user);
        btnUpload = findViewById(R.id.charity_reg);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri filePath =selectedImage;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Bitmap lastBitmap = null;
                    lastBitmap = bitmap;
                    //encoding image to string
                    String image = getStringImage(selectedImage);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMG);
                    }
                    else {
                        SendImage(image);
                        //Intent myIntent = new Intent(FirstLogCharityActivity.this, ConfirmationActivity.class);
                        //FirstLogCharityActivity.this.startActivity(myIntent);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                selectedImage = data.getData();
                ImageView imgView = (ImageView) findViewById(R.id.logoImg);
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

    public void loadImagefromGallery(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    private void SendImage( final String image) {
        url += user.getId() +"/"+name.getText()+"/"+description.getText();
        final SimpleMultiPartRequest stringRequest = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("uploade",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CharityRegistrationActivity.this, "No internet connection", Toast.LENGTH_LONG).show();

                    }
                });

        stringRequest.addFile("uploadfile", image);
        Log.d("imageeee","hhh");
        //  stringRequest.add("","parametre", )
        stringRequest.addMultipartParam("body","form-data","d");
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
