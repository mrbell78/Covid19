package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    String mobilegolbal= null;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String type_dp=null;
    String usertypeglobal=null;
    Toolbar mToolbar;

    ProgressDialog mDialog;

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
        mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("COVID-19 CRID DAM");
        mDialog=new ProgressDialog(this);

        mDialog.setTitle("Loading");
        mDialog.setMessage("Please Wait....");


        Intent intent = getIntent();
         value = intent.getStringExtra("valu");

         mobile=intent.getStringExtra("mobile");




        pref = DataEntryListctivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode

        usertypeglobal = pref.getString("type", null);
        mobilegolbal=pref.getString("mobile",null);
        Log.d(TAG, "onCreate: ...................user type in data view  "+ usertypeglobal);
        Log.d(TAG, "onCreate: ...................mobile in data entry acivity  "+ mobilegolbal);


        if(usertypeglobal.equals("Doctor")||usertypeglobal.equals("Patient")){

            getdata(mobilegolbal);
            mDialog.show();
        }else if(usertypeglobal.equals("Supplier")){

            getdataforsupply(mobilegolbal);
           mDialog.show();
            Log.d(TAG, "onCreate: ...................suppler data call");
           
        }



        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usertypeglobal.equals("Supplier")){
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
                    mDialog.dismiss();
                }






                List<Data> data =response.body().getData();
                for(Data d : data){

                    Data addata = new Data(d.getId(),d.getMobile(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getWhat_u_supply(),
                            d.getWhat_u_supply_other(),d.getHow_soon_can_u_supply(),d.getHospital(),d.getLocation());
                    listdata.add(addata);
                }

                DataCustomadapter_suppler adapter = new DataCustomadapter_suppler(DataEntryListctivity.this,listdata);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                mDialog.dismiss();



            }

            @Override
            public void onFailure(Call<Responseclass> call, Throwable t) {

                Log.d(TAG, "onFailure: .........fail "+t.getMessage());
                mDialog.dismiss();

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
                    mDialog.dismiss();
                }
                Log.d(TAG, "onResponse: .....................chek null "+ response.body());

                    if(response.body()!=null){

                        List<Data> data =response.body().getData();

                        Log.d(TAG, "onResponse: ..................chek data exist or not in list  "+data.toString());

                        for(Data d : data){

                            Data addata = new Data(d.getId(),d.getMobile(),d.getWhat_u_need(),d.getHow_soon_do_u_need_it(),d.getWhat_u_supply(),
                                    d.getWhat_u_supply_other(),d.getHow_soon_can_u_supply(),d.getHospital(),d.getLocation());
                            listdata.add(addata);
                            DataCustomdapter adapter = new DataCustomdapter(DataEntryListctivity.this,listdata);
                            //adapter.updateData(listdata);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mDialog.dismiss();

                        }
                    }
             //   adapter.updateData(listdata);


            }

            @Override
            public void onFailure(Call<Responseclass> call, Throwable t) {

                Log.d(TAG, "onFailure: .........fail "+t.getMessage());
                mDialog.dismiss();

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
                logout();
                return true;

            default:
                return false;

        }

    }

    private void logout() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("userexistancy", "null"); // Storing string
        editor.putString("mobile",null);
        editor.putString("password",null);
        editor.putString("type",null);
        editor.commit();


        startActivity(new Intent(getApplicationContext(),SplashActivity.class));
        finish();

    }
}
