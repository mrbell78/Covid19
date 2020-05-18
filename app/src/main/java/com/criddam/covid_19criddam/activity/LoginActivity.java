package com.criddam.covid_19criddam.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText edtnumber,edtName,edtpin;
    String phonenumber=null;
    private String codesent;

    LinearLayout llayou_number,llaout_pin;

    Button btn_continue,btn_finish;
    private static final String TAG = "LoginActivity";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog mdialog;

    private DatabaseReference mDatabaseref;

    Button btndelete;
    AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtnumber = findViewById(R.id.edtNumber);
        edtName = findViewById(R.id.edtName);

        btn_continue = findViewById(R.id.btn_login);
        btn_finish=findViewById(R.id.btn_finish);

        llayou_number=findViewById(R.id.numlayout);
        llaout_pin=findViewById(R.id.llayoutpin);

        edtpin =findViewById(R.id.edtpin);

        btndelete=findViewById(R.id.delete);


        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mdialog=new ProgressDialog(this);
        mdialog.setTitle("Login");
        mdialog.setMessage("Please wait.......:)");


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: -------------------number length "+ edtnumber.getText().toString().length());

                if(!TextUtils.isEmpty(edtnumber.getText().toString()) && edtnumber.getText().toString().length()==11){
                    sendVarificationcode();

                    llayou_number.setVisibility(View.INVISIBLE);
                    llaout_pin.setVisibility(View.VISIBLE);
                }

                else if(TextUtils.isEmpty(edtnumber.getText().toString())) {

                    edtnumber.setError("number is empty!!");
                    Toast.makeText(LoginActivity.this, "Please Enter valid number", Toast.LENGTH_SHORT).show();
                }else if (edtnumber.getText().toString().length()<=10 || edtnumber.getText().toString().length()>=11 || edtnumber.getText().toString().length()!=11 ){
                    edtnumber.setError("enter 11 digit number without +88");
                    Toast.makeText(LoginActivity.this, "enter 11 digit number without +88", Toast.LENGTH_SHORT).show();
                }

            }
        });

      btn_finish.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String code =edtpin.getText().toString();
              varyfycode(code);
          }
      });


      btndelete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

             user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(LoginActivity.this, "user deleted", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Log.d(TAG, "onFailure: error credential :"+ e.getMessage());
                     Toast.makeText(LoginActivity.this, "error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
          }
      });

    }


    private void sendVarificationcode() {


        phonenumber = "+88"+edtnumber.getText().toString();
        Log.d(TAG, "sendVarificationcode: -------------------------------------phone number---------------------------------------" + phonenumber);
        if (phonenumber.isEmpty()) {
            edtnumber.setError("phone number is required!!");
            return;
        } else if (phonenumber.length() < 10) {
            edtnumber.setError("Please enter valid phone number");
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber, 30, TimeUnit.SECONDS, this, mCallback);
    }


    private void varyfycode(String s) {

        //String code = edt_number.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, s);
        this.credential= credential;
        signInWithPhoneAuthCredential(credential);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if(code!=null){
                varyfycode(code);
                edtpin.setText(code);
                mdialog.dismiss();
            }else {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            mdialog.dismiss();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            //super.onCodeSent(s, forceResendingToken);

            codesent=s;
            Toast.makeText(LoginActivity.this, "code sent", Toast.LENGTH_SHORT).show();

            llayou_number.setVisibility(View.INVISIBLE);
            llaout_pin.setVisibility(View.VISIBLE);

        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = mCurrentUser.getUid();
                            mDatabaseref = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

                            Map<String, String> userfield = new HashMap<>();
                            userfield.put("name", "Name");
                            userfield.put("email", "Email");
                            userfield.put("phone", phonenumber);
                            userfield.put("password", "default");
                            userfield.put("image", "default");
                            userfield.put("thumb_nail", "default");
                            userfield.put("gender","Gender");
                            userfield.put("dateofbirdth","Date of Birth");
                            userfield.put("address","Your Address");

                            mDatabaseref.setValue(userfield).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        startActivity(new Intent(getApplicationContext(),MyProductActivity.class));
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: .............failed reason "+e.getMessage());
                                    Toast.makeText(LoginActivity.this, "failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });



                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(LoginActivity.this, "incorrenct code", Toast.LENGTH_SHORT).show();
                                mdialog.dismiss();
                            }
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            startActivity(new Intent(getApplicationContext(),MyProductActivity.class));
            Log.d(TAG, "onStart: ..............current user is exist ");
        }
    }
}
