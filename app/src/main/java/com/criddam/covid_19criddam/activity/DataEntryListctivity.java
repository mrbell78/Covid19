package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.adapter.DataCustomdapter;
import com.criddam.covid_19criddam.utils.DatabaseHelper;

import java.util.ArrayList;

public class DataEntryListctivity extends AppCompatActivity {
    ArrayList<String> listdata;
    DatabaseHelper db;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_listctivity);
        db= new DatabaseHelper(this);
        recyclerView=findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingadapter();
    }

    private void settingadapter() {

        Cursor data = db.fetch();
        listdata= new ArrayList<>();
       /* new Thread(new Runnable() {
            @Override
            public void run() {

                while (data.moveToNext()){
                    listdata.add(data.getString(1));
                }
            }
        }).start();*/
       int i =0;
       while (data.moveToNext()){
           listdata.add(data.getString(i));
           i++;

       }

        DataCustomdapter adapter = new DataCustomdapter(this,listdata);
       recyclerView.setAdapter(adapter);
       adapter.notifyDataSetChanged();


    }
}
