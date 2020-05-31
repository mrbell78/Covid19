package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.model.Alluserdata;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailssupplierActivity extends AppCompatActivity {

    private static final String TAG = "DetailssupplierActivity";

    TextView item, otheritem, price, phone,model,area;
    Button btn_call;
    ImageButton btn_back;
    ImageView img_mainImage;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailssupplier);

        mToolbar=findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("COVID 19");


        item = findViewById(R.id.item_id);
        otheritem = findViewById(R.id.brand_id);
        price = findViewById(R.id.price_id);
        phone = findViewById(R.id.phone_id);


        btn_call = findViewById(R.id.call);
        btn_back = findViewById(R.id.dismiss);
        img_mainImage = findViewById(R.id.img_mainid);
        model = findViewById(R.id.brand_id);
        area = findViewById(R.id.area_id);




        Intent i = getIntent();


        String key = i.getStringExtra("key");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("publicproduct").child(key);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String name = dataSnapshot.child("pName").getValue().toString();
                    String details = dataSnapshot.child("pDetails").getValue().toString();
                    String modell = dataSnapshot.child("pModel").getValue().toString();
                    String areal = dataSnapshot.child("pArea").getValue().toString();
                    String pricel = dataSnapshot.child("pPrice").getValue().toString();
                    String img1 = dataSnapshot.child("img1").getValue().toString();

                    Picasso.with(DetailssupplierActivity.this).load(img1).error(R.drawable.defaultimage).placeholder(R.drawable.defaultimage).into(img_mainImage);

                    item.setText(name);
                    otheritem.setText(details);
                    price.setText(pricel+" TK");
                    model.setText(modell);
                    area.setText(areal);
                    String mobile = dataSnapshot.child("mobile").getValue().toString();
                    phone.setText(mobile);
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorRequestActivity.class));
                finish();
            }
        });


    }
}
