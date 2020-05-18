package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

public class MainActivity extends AppCompatActivity {


    Button btn_next,btn_previous;
    TextView edtitemtext,forwho;
    Toolbar mToolbaar;
    EditText edt_item;
    String type ;
    String  loginstaus = null;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);





        btn_next=findViewById(R.id.btn_add);
        btn_previous=findViewById(R.id.btn_previous);

        edt_item=findViewById(R.id.edt_item);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("COVID-19 CRID DAM");


        Intent i  = getIntent();
        type = i.getStringExtra("doc");
        Log.d(TAG, "onCreate: ...........what u need activity............the user type "+ type);


        pref = MainActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        loginstaus = pref.getString("loginstatus", null);


        if(loginstaus!=null){
            btn_previous.setVisibility(View.INVISIBLE);
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_item.getText().toString())){

                    Intent intent = new Intent(getApplicationContext(),DoctorEmergencyActivity.class);
                    intent.putExtra("docneed",edt_item.getText().toString()).putExtra("type",type);
                    startActivity(intent);



                }else {
                    Toast.makeText(MainActivity.this, "Please mention what you need", Toast.LENGTH_SHORT).show();
                    edt_item.setError("Please mention what you need ");
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
                pref = MainActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logout);

        pref = MainActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        loginstaus = pref.getString("loginstatus", null);

            if(loginstaus==null){
                item.setTitle("Login");
            }





        return super.onPrepareOptionsMenu(menu);
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


}
