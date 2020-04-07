package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");

        radioGroup=findViewById(R.id.radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.doctor:
                        Toast.makeText(SplashActivity.this, "you selected as doctor", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("doc","Doctor"));
                        finish();
                        break;
                    case R.id.patient:

                        Toast.makeText(SplashActivity.this, "you selected as patient", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("doc","Patient"));
                        finish();
                        break;
                    case R.id.supplier:
                        Toast.makeText(SplashActivity.this, "you selected as supplier", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),SupplierActivity.class));
                        finish();
                        break;
                    case R.id.volunteer:
                        Toast.makeText(SplashActivity.this, "you selected as volunteer", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

}
