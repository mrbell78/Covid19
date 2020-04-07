package com.criddam.covid_19criddam.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    String docneed,docemergency;
    ProgressDialog mdialog;

    String apiKey ="AIzaSyAtW128tmvUvIxGGrfDSK-Fy0_DRyStGoI";
    private static final String TAG = "RegistrationActivity";
    public static final int AUTOCOMPLETE_REQUEST_CODE=100;

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




        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }




        Intent intent = getIntent();
         docneed = intent.getStringExtra("supply");
        docemergency  = intent.getStringExtra("emergency");

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

                ) {


                    Retrofit retrofit  = new Retrofit.Builder()
                            .baseUrl("http://sales.criddam.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    api_covid = retrofit.create(Api_covid.class);
                    createPost("doctor",edt_name.getText().toString(),edt_mobile.getText().toString(),edt_email.getText().toString(),docneed,docemergency,
                            edt_password.getText().toString(),edt_hospital.getText().toString(),edt_location.getText().toString()
                            );


                    startActivity(new Intent(getApplicationContext(), Submissio_completeActivity.class).putExtra("identify", "doctor_p"));


                    Toast.makeText(RegistrationActivity.this, docneed, Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegistrationActivity.this, docemergency, Toast.LENGTH_SHORT).show();


                    finish();
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


    /*private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyBB5KBkuA4-qm8QxaXX8FhHqhHsESMdAzI";

            String input = "";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input + "&" + types + "&" + sensor + "&" + key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;

            try {
                // Fetching the data from we service
                data = downloadUrl(url);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }

        private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

            JSONObject jObject;
            private static final String TAG = "ParserTask";

            @Override
            protected List<HashMap<String, String>> doInBackground(String... jsonData) {

                List<HashMap<String, String>> places = null;

                PlaceJSONParser placeJsonParser = new PlaceJSONParser();

                try {
                    jObject = new JSONObject(jsonData[0]);

                    // Getting the parsed data as a List construct
                    places = placeJsonParser.parse(jObject);

                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
                return places;
            }

            @Override
            protected void onPostExecute(List<HashMap<String, String>> result) {

                String[] from = new String[]{"description"};
                int[] to = new int[]{android.R.id.text1};

                // Creating a SimpleAdapter for the AutoCompleteTextView
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), result, android.R.layout.simple_list_item_1, from, to);

                // Setting the adapter
                location.setAdapter(adapter);
            }
        }


        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuilder sb = new StringBuilder();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            } catch (Exception e) {
                Log.d("Exception  url", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }*/


    public void onSearchCalled() {
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setCountry("BN") //NIGERIA
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                Toast.makeText(RegistrationActivity.this, "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                String address = place.getAddress();
                // do query with address

               /// location.setText(address);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(RegistrationActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private void createPost(String usertype,String name,String mobile,String email,String what_u_need,String how_soon_do_u_need_it,String password,String hospital,String location) {

        Post postresponsee = new Post(usertype,name,mobile,email,what_u_need, how_soon_do_u_need_it,password,hospital,location);

        Call<Post> call = api_covid.newPost(postresponsee);

        //Call<Post> call = api_covid.createPost(usertype,name,mobile,email,what_u_need, how_soon_do_u_need_it,password,hospital,location);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: ..............unSuccessful "+response.code());
                }

                Post postrespone = response.body();

                Log.d(TAG, "onResponse: .........successful "+ response.code());
                Log.d(TAG, "onResponse: .....................data retrive "+ postresponsee.getWhat_u_need());

                DatabaseHelper db = new DatabaseHelper(RegistrationActivity.this);

                if(db.insert(postresponsee.getWhat_u_need(),postresponsee.getLocation())){
                    Toast.makeText(RegistrationActivity.this, "data upload succesfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RegistrationActivity.this, "data upload failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Log.d(TAG, "onFailure: ...........error " + t.getMessage());

            }
        });

    }
}
