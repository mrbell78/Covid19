package com.criddam.covid_19criddam.tab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.DoctorEmergencyActivity;
import com.criddam.covid_19criddam.activity.MainActivity;
import com.criddam.covid_19criddam.activity.SplashActivity;

public class Doctororderend  extends Fragment {

    Button btn_next,btn_previous;
    EditText edt_item;

    String type ;
    String  loginstaus = null;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.doctororderend,container,false);

       /* btn_next=view.findViewById(R.id.btn_add);
        btn_previous=view.findViewById(R.id.btn_previous);

        edt_item=view.findViewById(R.id.edt_item);
*/
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       /* Intent i  = getActivity().getIntent();
        type = i.getStringExtra("doc");
        Log.d("TAG", "onCreate: ...........what u need activity............the user type "+ type);


        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode

        loginstaus = pref.getString("loginstatus", null);


        if(loginstaus!=null){
            btn_previous.setVisibility(View.INVISIBLE);
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edt_item.getText().toString())){

                    Intent intent = new Intent(getContext(), DoctorEmergencyActivity.class);
                    intent.putExtra("docneed",edt_item.getText().toString()).putExtra("type",type);
                    startActivity(intent);



                }else {
                    Toast.makeText(getContext(), "Please mention what you need", Toast.LENGTH_SHORT).show();
                    edt_item.setError("Please mention what you need ");
                }
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });*/

    }



    private void logout() {

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("userexistancy", "null"); // Storing string
        editor.putString("mobile",null);
        editor.putString("password",null);
        editor.putString("loginstatus",null);
        editor.putString("type",null);
        editor.commit();


        startActivity(new Intent(getContext(), SplashActivity.class));
        getActivity().finish();

    }
}
