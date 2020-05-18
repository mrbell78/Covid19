package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

import java.util.ArrayList;

public class SupplierEmergencyActivity extends AppCompatActivity {

    Button btn_next,btn_previous;
    EditText edt_time;
    Toolbar mToolbaar;
    String  value,other;
    String loginstaus=null;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_emergency);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        btn_previous=findViewById(R.id.btn_sprevious);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("COVID-19 CRID DAM");
        btn_next=findViewById(R.id.btn_snext);
        edt_time=findViewById(R.id.edt_sptime);

        Intent intent = getIntent();
        value = intent.getStringExtra("suppy_nedd");
        other = intent.getStringExtra("othersuply");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!TextUtils.isEmpty(edt_time.getText().toString())){

                   startActivity(new Intent(getApplicationContext(),SignsuppliActivity.class).putExtra("sp_need",value)
                           .putExtra("time",edt_time.getText().toString()).putExtra("othersply",other));
                   finish();
               }else{
                   edt_time.setError("Please mention your urgency ");
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


        switch (item.getItemId()) {
            case R.id.logout:

                if("Login".equals(item.getTitle())){
                    login();
                }else {
                    logout();
                }
                return true;
            case R.id.list:
                 pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

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

        pref = SupplierEmergencyActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        loginstaus = pref.getString("loginstatus", null);

        if(loginstaus==null){
            item.setTitle("Login");
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
