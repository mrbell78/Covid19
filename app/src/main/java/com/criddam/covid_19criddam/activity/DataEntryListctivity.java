package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.adapter.DataCustomadapter_suppler;
import com.criddam.covid_19criddam.adapter.DataCustomdapter;
import com.criddam.covid_19criddam.apicalling.Api_covid;
import com.criddam.covid_19criddam.model.Data;
import com.criddam.covid_19criddam.model.Responseclass;
import com.criddam.covid_19criddam.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataEntryListctivity extends AppCompatActivity {
    List<Data> listdata;
    DatabaseHelper db;
    RecyclerView recyclerView;

    private static final String TAG = "DataEntryListctivity";
    Button btn_addnew;
    String value=null;
    String mobile=null;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String type_dp=null;
    String usertypeglobal=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_listctivity);
        db= new DatabaseHelper(this);
        recyclerView=findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_addnew=findViewById(R.id.addnew);

        listdata= new ArrayList<>();


        Intent intent = getIntent();
         value = intent.getStringExtra("valu");

         mobile=intent.getStringExtra("mobile");


        pref = DataEntryListctivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        usertypeglobal = pref.getString("type", null);

        if(usertypeglobal.equals("doctor")||usertypeglobal.equals("patient")){

            getdata(mobile);
        }else if(usertypeglobal.equals("supplier")){

            getdataforsupply(mobile);
        }




        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value=="supplier"){
                    startActivity(new Intent(getApplicationContext(),SupplierActivity.class));

                }else {

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });
    }

    private void getdataforsupply(String mobile) {


        Api_covid api;

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/show_my_data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api_covid.class);

        Call<Responseclass> call = api.getData(mobile);



        call.enqueue(new Callback<Responseclass>() {
            @Override
            public void onResponse(Call<Responseclass> call, Response<Responseclass> response) {

                String usertype=null,name=null,email=null,mobile=null,password=null,hospital=null,location=null;
                if(!response.isSuccessful()){

                    Log.d(TAG, "onResponse: ................error "+response.code());
                }



                List<Data> data =response.body().getData();
                for(Data d : data){

                    Data addata = new Data(d.getUsertype(),d.getFullname(),d.getMobile(),d.getUsername(),d.getLocation(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getHospital(),d.getEmail(),d.getPassword());
                    listdata.add(addata);
                }

                DataCustomadapter_suppler adapter = new DataCustomadapter_suppler(DataEntryListctivity.this,listdata);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<Responseclass> call, Throwable t) {

                Log.d(TAG, "onFailure: .........fail "+t.getMessage());

            }
        });





    }

    private void getdata(String mobile) {

        Api_covid api;

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://sales.criddam.com/api/show_my_data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api_covid.class);

        Call<Responseclass> call = api.getData(mobile);



        call.enqueue(new Callback<Responseclass>() {
            @Override
            public void onResponse(Call<Responseclass> call, Response<Responseclass> response) {

                String usertype=null,name=null,email=null,mobile=null,password=null,hospital=null,location=null;
                if(!response.isSuccessful()){

                    Log.d(TAG, "onResponse: ................error "+response.code());
                }



                List<Data> data =response.body().getData();
                for(Data d : data){

                    Data addata = new Data(d.getUsertype(),d.getFullname(),d.getMobile(),d.getUsername(),d.getLocation(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getHospital(),d.getEmail(),d.getPassword());
                    listdata.add(addata);
                      }

                DataCustomdapter adapter = new DataCustomdapter(DataEntryListctivity.this,listdata);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<Responseclass> call, Throwable t) {

                Log.d(TAG, "onFailure: .........fail "+t.getMessage());

            }
        });




    }
}
