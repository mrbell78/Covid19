package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;

import java.util.ArrayList;
import java.util.List;

public class SupplierActivity extends AppCompatActivity  {

    Button btn_next,btn_previous;
    CheckBox mask,ppe,medicne,equipment,Hand ,saitizer;
    EditText edt_other;

    String suplyablelist="";
    String supplyneed=null ;

    List<String>itemlist;
    private static final String TAG = "SupplierActivity";

    Toolbar mToolbaar;

  /*  SharedPreferences pref = getApplicationContext().getSharedPreferences("suplyPref", 0); // 0 - for private mode
    SharedPreferences.Editor editor = pref.edit();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btn_next=findViewById(R.id.btn_snext);
        btn_previous=findViewById(R.id.btn_sprevious);
        edt_other=findViewById(R.id.edt_supply_other);

        mToolbaar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbaar);
        getSupportActionBar().setTitle("Welcome to COVID-19 CRID DAM");

        mask=findViewById(R.id.chmask);
        ppe=findViewById(R.id.chPPE);
        equipment=findViewById(R.id.mequipment);
        saitizer= findViewById(R.id.senitaizer);
        medicne=findViewById(R.id.medine);



        itemlist=new ArrayList<>();



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getchvalue();
                if(itemlist.size()==0 && TextUtils.isEmpty(edt_other.getText().toString())){
                    Toast.makeText(SupplierActivity.this, "Please choose at least one item ", Toast.LENGTH_SHORT).show();
                    mask.setFocusable(true);

                }

                else {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        supplyneed = String.join(",",itemlist);
                    }
                    Toast.makeText(SupplierActivity.this, suplyablelist, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),SupplierEmergencyActivity.class).putExtra("suppy_nedd",suplyablelist)
                            .putExtra("othersuply",edt_other.getText().toString()));
                     finish();
                   /* editor.putString("supply_needs", suplyablelist); // Storing string
                    editor.commit(); // commit changes*/

                }

            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SplashActivity.class));
            }
        });
    }

    private String getchvalue() {

       if(mask.isChecked()){
           itemlist.add(mask.getText().toString());
           suplyablelist=suplyablelist+(mask.getText().toString())+"  ";
       }
        if(ppe.isChecked()){
            itemlist.add(ppe.getText().toString());
            suplyablelist=suplyablelist+(ppe.getText().toString())+"  ";
        }
        if(equipment.isChecked()){
            itemlist.add(equipment.getText().toString());
            suplyablelist=suplyablelist+(equipment.getText().toString())+"  ";
        }
        if(saitizer.isChecked()){
            itemlist.add(saitizer.getText().toString());
            suplyablelist=suplyablelist+(saitizer.getText().toString())+"  ";

        }
        if(medicne.isChecked()){
            itemlist.add(medicne.getText().toString());
            suplyablelist=suplyablelist+(medicne.getText().toString())+"  ";
        }

        return suplyablelist;


    }


}
