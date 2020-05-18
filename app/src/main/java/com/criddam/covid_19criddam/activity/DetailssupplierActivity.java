package com.criddam.covid_19criddam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.model.Alluserdata;

import java.util.List;

public class DetailssupplierActivity extends AppCompatActivity {

    private static final String TAG = "DetailssupplierActivity";

    TextView item, otheritem, price, phone;
    Button btn_call;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailssupplier);

        item = findViewById(R.id.item_id);
        otheritem = findViewById(R.id.brand_id);
        price = findViewById(R.id.price_id);
        phone = findViewById(R.id.phone_id);

        btn_call = findViewById(R.id.call);
        btn_back = findViewById(R.id.dismiss);


        Intent i = getIntent();


        String mobile = i.getStringExtra("mobile");
        String itemv = i.getStringExtra("item");
        String otheritem = i.getStringExtra("otheritem");
        String location = i.getStringExtra("location");


        item.setText(itemv);
        phone.setText(mobile);
        price.setText(location);

        Log.d(TAG, "onCreate: ................data found " + mobile);
        Log.d(TAG, "onCreate: ................data found " + item);
        Log.d(TAG, "onCreate: ................data found " + otheritem);
        Log.d(TAG, "onCreate: ................data found " + location);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorRequestActivity.class));
                finish();
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mobile));
                startActivity(intent);




            }
        });
    }
}
