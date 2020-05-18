package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

public class DoctorEmergencyActivity extends AppCompatActivity {
    Button btn_next,btn_previous;
    EditText edt_time;
    String type;
     String value;
    private static final String TAG = "DoctorEmergencyActivity";
    Toolbar mToolbaar;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    String loginstaus=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_emergency);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mToolbaar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("COVID-19 CRID DAM");
        btn_next=findViewById(R.id.btn_snext);
        btn_previous=findViewById(R.id.btn_sprevious);
        edt_time=findViewById(R.id.edt_stime);

        Intent intent = getIntent();
         value = intent.getStringExtra("docneed");
        type = intent.getStringExtra("type");
        Log.d(TAG, "onCreate: ........how soon activity ...............the user type "+ type);




        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_time.getText().toString())){
                    startActivity(new Intent(getApplicationContext(),SigninActivity.class).putExtra("docneed",value).
                            putExtra("emergency",edt_time.getText().toString()
                    ).putExtra("type",type)

                    );
                    finish();
                }else{
                    edt_time.setError("Please mention your urgency here");
                    Toast.makeText(DoctorEmergencyActivity.this, "Please mention your urgency here", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()){
            case R.id.logout:
                if(item.getTitle().equals("Login")){
                    login();
                }else {
                    logout();
                }
                return true;

            case R.id.list:
                pref = DoctorEmergencyActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

                loginstaus = pref.getString("loginstatus", null);
                if(loginstaus!=null){
                    startActivity(new Intent(getApplicationContext(),DataEntryListctivity.class));
                }else {
                    Toast.makeText(this, "Please Login first ", Toast.LENGTH_SHORT).show();
                }

                return true;
            default:
                return false;

        }

    }

    private void login() {
        Toast.makeText(this, "Click next ", Toast.LENGTH_SHORT).show();
    }

    private void logout() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userexistancy", "null"); // Storing string
        editor.putString("mobile",null);
        editor.putString("password",null);
        editor.putString("loginstatus",null);
        editor.putString("type",null);
        editor.commit();
        startActivity(new Intent(getApplicationContext(),SplashActivity.class));
        finish();

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logout);

        pref = DoctorEmergencyActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        loginstaus = pref.getString("loginstatus", null);

        if(loginstaus==null){
            item.setTitle("Login");
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
