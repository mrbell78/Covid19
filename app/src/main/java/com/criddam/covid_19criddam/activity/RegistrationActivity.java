package com.criddam.covid_19criddam.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.adapter.PlaceAutoSuggetionAdapter;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Post;
import com.criddam.covid_19criddam.placeutils.PlaceJSONParser;
import com.criddam.covid_19criddam.utils.DatabaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    EditText edt_name, edt_mobile, edt_email, edt_hospital, edt_location,edt_password;
    AutoCompleteTextView location;
    Button btn_submit, btn_cancel;
    Toolbar mToolbaar;
    Api_covid api_covid;
    /*PlacesTask placesTask;
    PlacesTask.ParserTask parserTask;*/
    String docneed,docemergency,type;
    ProgressDialog mdialog;
    boolean status_glb= false;
    RadioButton btn_radio;
    DatabaseHelper db;

    String apiKey ="AIzaSyAtW128tmvUvIxGGrfDSK-Fy0_DRyStGoI";
    private static final String TAG = "RegistrationActivity";
    public static final int AUTOCOMPLETE_REQUEST_CODE=100;

    String usertypeglobal=null;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mToolbaar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        edt_name = findViewById(R.id.fullname);
        edt_mobile = findViewById(R.id.mobile);
        edt_email = findViewById(R.id.email);
        edt_hospital = findViewById(R.id.hospital);
        edt_location = findViewById(R.id.location);
        edt_password=findViewById(R.id.password_doc);
        btn_radio=findViewById(R.id.btn_radio);


        pref = RegistrationActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        usertypeglobal = pref.getString("type", null);

        db=new DatabaseHelper(this);

        mdialog=new ProgressDialog(this);
        mdialog.setTitle("Create Account");
        mdialog.setMessage("Please wait");



        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }




        Intent intent = getIntent();
         docneed = intent.getStringExtra("docneed");
        docemergency  = intent.getStringExtra("emergency");
        type= intent.getStringExtra("type");

       if(type!=null){
           btn_radio.setChecked(true);
           btn_radio.setText(type);
       }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(edt_name.getText().toString())
                        && !TextUtils.isEmpty(edt_mobile.getText().toString())
                        && !TextUtils.isEmpty(edt_hospital.getText().toString())
                        && !TextUtils.isEmpty(edt_location.getText().toString())
                        && !TextUtils.isEmpty(edt_password.getText().toString())
                        && !TextUtils.isEmpty(edt_email.getText().toString())
                        && docneed!=null
                        && docemergency!=null
                        && usertypeglobal!=null

                ) {

                    Retrofit retrofit  = new Retrofit.Builder()
                            .baseUrl("http://sales.criddam.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Toast.makeText(RegistrationActivity.this, "user type "+ type, Toast.LENGTH_SHORT).show();
                    api_covid = retrofit.create(Api_covid.class);
                    createPost(usertypeglobal,edt_name.getText().toString(),edt_mobile.getText().toString(),edt_email.getText().toString(),docneed,docemergency,
                            edt_password.getText().toString(),edt_hospital.getText().toString(),edt_location.getText().toString()
                            );

                   mdialog.show();



                } else if (TextUtils.isEmpty(edt_name.getText().toString())) {
                    edt_name.setError("You must have to fill up this filed");
                    Toast.makeText(RegistrationActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_mobile.getText().toString())) {
                    edt_mobile.setError("You must have to fill up this filed");
                    Toast.makeText(RegistrationActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_hospital.getText().toString())) {
                    edt_hospital.setError("You must have to fill up this filed");
                    Toast.makeText(RegistrationActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_location.getText().toString())) {
                    edt_location.setError("You must have to fill up this filed");
                    Toast.makeText(RegistrationActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edt_password.getText().toString())) {
                    edt_password.setError("You must have to fill up this filed");
                    Toast.makeText(RegistrationActivity.this, "filed is missing ", Toast.LENGTH_SHORT).show();
                } else if (docneed ==null || docemergency==null ) {

                    Toast.makeText(RegistrationActivity.this, "docnedd is emtpy ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(RegistrationActivity.this)
                        .setTitle("Cancel Registration")
                        .setMessage("Are you sure you want to Cancel the registration?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_warning)
                        .show();
            }
        });
    }





    private void createPost(String usertype,String name,String mobile,String email,String what_u_need,String how_soon_do_u_need_it,String password,String hospital,String location) {

        Call<ResponseBody> call = api_covid.createPost(usertype, name, mobile, email, what_u_need, how_soon_do_u_need_it, password, hospital, location);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String s = null;
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: ..............unSuccessful " + response.code());
                    mdialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "server error", Toast.LENGTH_SHORT).show();
                }


                try {

                    s = response.body().string();

                    Log.d(TAG, "onResponse: .................resposne by serveer " + s);
                    Log.d(TAG, "onResponse: .................resposne by serveer code " + response.code());


                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);

                        String status = jsonObject.getString("msg");
                        if (status.equals("Mobile number 01762957422 already exists in server.")) {
                            mdialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Mobile number 01762957422 already exists in server.", Toast.LENGTH_SHORT).show();
                            edt_mobile.setError("User exist");
                        } else {




                                mdialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Submissio_completeActivity.class).putExtra("type", "Doctor").putExtra("mobile",mobile));
                                finish();
                                Log.d(TAG, "onClick: ........................status glb "+status_glb);




                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
