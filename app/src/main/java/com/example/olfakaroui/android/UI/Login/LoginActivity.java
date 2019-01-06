package com.example.olfakaroui.android.UI.Login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.MainActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.service.UserService;
import com.example.olfakaroui.android.utils.PictureRendrer;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private CallbackManager callbackManager;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    //Signin button
    private SignInButton signInButton;
    private ProgressDialog pDialog;
    //Signing Options
    private GoogleSignInOptions gso;
    User user = new User();
    boolean createdAccount = false;
    LinearLayout mainView;
    //google api client
    User checked = new User();
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    SessionManager session;
    String email;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mainView = findViewById(R.id.login_view);
        BitmapDrawable myBackground = new BitmapDrawable(PictureRendrer.decodeSampledBitmapFromResource(getResources(), R.drawable.app_background, 100, 100));
        mainView.setBackgroundDrawable(myBackground);
        session = new SessionManager(this);
        if(session.isLoggedIn())
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else
            {
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Profile profile = Profile.getCurrentProfile();
                        displayMessage(profile);

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }

                });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_signin);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();
                displayMessage(profile);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());


                                // Application code
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }


            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());

            }
        });
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.google_signin);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Setting onclick listener to signing button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        //accessTokenTracker.stopTracking();
        //profileTracker.stopTracking();
    }
    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }

    //Get Plus Data
    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            user.setFirstName(acct.getGivenName());
            user.setLastName(acct.getFamilyName());
            user.setSocialId(acct.getId());
            user.setSocialPlatform("google");
            if(acct.getPhotoUrl() != null)
            {
                String photo = acct.getPhotoUrl().toString();
                user.setPhoto(photo);
            }

            UserService.getInstance().checkUser(user, new UserService.UserServiceCheckUserCallBack() {
                @Override
                public void onResponse(User u, boolean isAdded) {
                    user.setRole(u.getRole());

                    session.setLogin(true, u, isAdded);

                    if(isAdded)
                    {
                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(String error) {

                }

            });


            //Displaying name and email
            Log.d(TAG, "handleSignInResult: " + acct.getDisplayName());
        } else {
            //If login fails
            //Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    //Get FaceBook Data
    private void displayMessage(Profile profile){
        if(profile != null){

            user.setFirstName(profile.getFirstName());
            user.setLastName(profile.getLastName());
            user.setSocialId(profile.getId());
            user.setSocialPlatform("facebook");
            String photo =profile.getProfilePictureUri(400, 400).toString();
            user.setPhoto(photo);
            UserService.getInstance().checkUser(user, new UserService.UserServiceCheckUserCallBack() {
                @Override
                public void onResponse(User u, boolean isAdded) {
                    user.setRole(u.getRole());
                    session.setLogin(true,u,isAdded);
                    Log.d(TAG, "displayMessage: " + profile.getId());
                    if(isAdded)
                    {
                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(String error) {

                }

            });



        }
    }




}
