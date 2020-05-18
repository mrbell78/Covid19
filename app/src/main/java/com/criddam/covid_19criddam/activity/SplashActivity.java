package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

public class SplashActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Toolbar mToolbaar;
    private static final String TAG = "SplashActivity";


    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");

        radioGroup=findViewById(R.id.radiogroup);


        pref = SplashActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        user = pref.getString("type", null);

        Log.d(TAG, "onCreate: ................user chek "+ user);

        if(user!=null){
            userchek(user);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.doctor:


                       /* pref = SplashActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                        editor = pref.edit();
                        editor.putString("type","doctor");
                        editor.commit();*/

                          startActivity(new Intent(getApplicationContext(),DoctorRequestActivity.class).putExtra("doc","Doctor"));
                            finish();
                            break;
                    case R.id.patient:

                        /*pref = SplashActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                        editor = pref.edit();
                        editor.putString("type","patient");
                        editor.commit();*/

                        Toast.makeText(SplashActivity.this, "you selected as patient", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),DoctorRequestActivity.class).putExtra("doc","Patient"));
                        finish();
                        break;
                    case R.id.supplier:

                       /* pref = SplashActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                        editor = pref.edit();
                        editor.putString("type","supplier");
                        editor.commit();*/

                        Toast.makeText(SplashActivity.this, "you selected as supplier", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),SupplierActivity.class).putExtra("sp","supplier"));
                        finish();
                        break;

                }
            }
        });




    }

    private void userchek(String user) {


        Log.d(TAG, "onStart: .........................userandtype "+ user);
        if(user.equals("Doctor")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("type","Doctor"));
            finish();
        }else if(user.equals("Patient")){

            startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("type","Patient"));
            finish();

        }else if(user.equals("Supplier")){
            startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
            finish();
        }
    }


}
