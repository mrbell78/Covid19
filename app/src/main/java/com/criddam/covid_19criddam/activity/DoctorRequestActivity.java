package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;



import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.adapter.Adapter_alluser;
import com.criddam.covid_19criddam.adapter.Pageradapter;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Alluserdata;
import com.criddam.covid_19criddam.model.Alluserdataresponse;
import com.criddam.covid_19criddam.tab.Doctorend;
import com.criddam.covid_19criddam.tab.Doctororderend;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorRequestActivity extends AppCompatActivity {


    Api_covid api_covid;
    List<Alluserdata> alluser;
    private static final String TAG = "DoctorRequestActivity";


    RecyclerView recyclerView;
    ViewPager2 viewPager2;
    ViewPager viewPager;

    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_request);
        mToolbar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("COVID-19 CRID DAM");


        viewPager2 = findViewById(R.id.viewpagerdoctor);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        viewPager2.setAdapter(new Pageradapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){

                    case 0:
                        tab.setText("Itemlist");
                        break;
                    case 1:
                        tab.setText("Place Order");
                        break;
                }

            }
        });
        tabLayoutMediator.attach();


    }
  /*  private void getdata() {

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api_covid = retrofit.create(Api_covid.class);

        Call<Alluserdataresponse> call = api_covid.getalluser();

        call.enqueue(new Callback<Alluserdataresponse>() {
            @Override
            public void onResponse(Call<Alluserdataresponse> call, Response<Alluserdataresponse> response) {

               if(!response.isSuccessful()){

                   Log.d(TAG, "onResponse: ..................error alluserdata "+ response.code());
               }

                List<Alluserdata> alluserdata = response.body().getAlluserdataList();

                for(Alluserdata d : alluserdata){

                    Alluserdata modifedata = new Alluserdata(d.getSl(),d.getId(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getWhat_u_supply(),d.getWhat_u_supply_other(),
                            d.getHow_soon_can_u_supply(),d.getHospital(),d.getLocation(),d.getName(),d.getMobile(),d.getEmail(),d.getType()

                    );
                    alluser.add(modifedata);
                    Log.d(TAG, "onResponse: ................alll user data  "+ d.getType());

                }

                Adapter_alluser adapter = new Adapter_alluser(DoctorRequestActivity.this,alluser);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Alluserdataresponse> call, Throwable t) {

                Log.d(TAG, "onFailure: ..................failed all user "+ t.getMessage());

            }
        });

    }*/
}
