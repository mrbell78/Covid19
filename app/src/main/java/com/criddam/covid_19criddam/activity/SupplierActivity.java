package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.adapter.AdapterSupplierfragment;
import com.criddam.covid_19criddam.adapter.Pageradapter;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Alluserdata;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SupplierActivity extends AppCompatActivity  {

    Api_covid api_covid;
    List<Alluserdata> alluser;
    private static final String TAG = "DoctorRequestActivity";


    RecyclerView recyclerView;
    ViewPager2 viewPager2;
    ViewPager viewPager;

    Toolbar mToolbar;

  /*  SharedPreferences pref = getApplicationContext().getSharedPreferences("suplyPref", 0); // 0 - for private mode
    SharedPreferences.Editor editor = pref.edit();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("COVID-19 CRID DAM");


        viewPager2 = findViewById(R.id.viewpagerdoctor);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        viewPager2.setAdapter(new AdapterSupplierfragment(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){

                    case 0:
                        tab.setText("Client Request");
                        break;
                    case 1:
                        tab.setText("Submit Item");
                        break;
                }

            }
        });
        tabLayoutMediator.attach();


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);

    }

}
