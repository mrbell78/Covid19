package com.criddam.covid_19criddam.tab;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.criddam.covid_19criddam.R;
import com.criddam.covid_19criddam.activity.LoginActivity;
import com.criddam.covid_19criddam.activity.MyProductActivity;
import com.criddam.covid_19criddam.activity.SplashActivity;
import com.criddam.covid_19criddam.activity.SupplierActivity;
import com.criddam.covid_19criddam.activity.SupplierEmergencyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmititemFragment extends Fragment {


    Button btn_next,btn_previous;
    CheckBox mask,ppe,medicne,equipment,Hand ,saitizer;
    EditText edt_other;

    TextView tv_otherclik;

    String suplyablelist="";
    String supplyneed=null ;
    String loginstaus= null;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    List<String> itemlist;

    LinearLayout llayoutother;





    ImageView imgproduct;
    Button btn_takepic;
    Uri img_uri;

    List<Bitmap> bitmapslist;
    List<Uri>imageslist;
    LinearLayout llayout_defaultimage;

    ImageView img1,img2,img3,img4,img5;
    TextView removeall;

    EditText edt_name,edt_details,edt_model,edt_area,edt_price;

    Button btn_post;

    StorageReference mStorage;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;
    int imagecounter =0;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser mUser;
    String userId;

    String phonenumber = null;
    String name = null;

    ProgressDialog mProgressdialog;
    public SubmititemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        hideKeyboard(getContext());



        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_submititem,container,false);

        imgproduct= view.findViewById(R.id.img_productpic);
        btn_takepic=view.findViewById(R.id.btn_addpic);
        llayout_defaultimage=view.findViewById(R.id.grupimage);

        img1=view.findViewById(R.id.img1);
        img2=view.findViewById(R.id.img2);
        img3=view.findViewById(R.id.img3);
        img4=view.findViewById(R.id.img4);
        img5=view.findViewById(R.id.img5);

        removeall = view.findViewById(R.id.removepic);

        edt_name=view.findViewById(R.id.productname);
        edt_details=view.findViewById(R.id.pdetals);
        edt_area = view.findViewById(R.id.parea);
        edt_model=view.findViewById(R.id.pmodel);
        edt_price=view.findViewById(R.id.pprice);
        btn_post = view.findViewById(R.id.post);

        mStorage = FirebaseStorage.getInstance().getReference();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId= mUser.getUid();
        mProgressdialog=new ProgressDialog(getContext());
        mDatabase= FirebaseDatabase.getInstance().getReference().child("User").child(userId);



        Log.d("TAG", "onCreateView: ...........phone number "+ phonenumber);
        Log.d("TAG", "onCreateView: ......................naem "+ name);

        removeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapslist=new ArrayList<>();
                removeall.setVisibility(View.INVISIBLE);
                llayout_defaultimage.setVisibility(View.INVISIBLE);
                imgproduct.setVisibility(View.VISIBLE);
            }
        });





        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        btn_takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(btn_takepic.getText().equals("Clear All")){

                    bitmapslist=new ArrayList<>();
                    llayout_defaultimage.setVisibility(View.INVISIBLE);
                    imgproduct.setVisibility(View.VISIBLE);
                    btn_takepic.setText("Add Image");

                }else if(btn_takepic.getText().equals("Add Image")){

                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                        }else{
                            BringImagePicker();

                        }
                    }else{
                        BringImagePicker();


                    }

                }




            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "button is clicked", Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){

                    if(!TextUtils.isEmpty(edt_price.getText().toString()) && ! TextUtils.isEmpty(edt_name.getText().toString())){

                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    uploadProduct(currentTime);

                    }else if(TextUtils.isEmpty(edt_price.getText().toString())){

                        edt_price.setError("must required");

                    }else if(TextUtils.isEmpty(edt_name.getText().toString())){
                        edt_name.setError("Must required filed");
                    }
                }else {
                    Toast.makeText(getActivity(), "no vlaid user found", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }

            }
        });




    }

    private void uploadProduct( String currentTime) {

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = mUser.getUid();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("storeroom").child(userid);
        HashMap productinfo = new HashMap();

        productinfo.put("pName",edt_name.getText().toString());
        productinfo.put("pDetails",edt_details.getText().toString());
        productinfo.put("pModel",edt_model.getText().toString());
        productinfo.put("pArea",edt_area.getText().toString());
        productinfo.put("pPrice",edt_price.getText().toString());
        productinfo.put("img1","default");
        productinfo.put("img2","default");
        productinfo.put("img3","default");
        productinfo.put("img4","default");
        productinfo.put("thum1","default");
        productinfo.put("thum2","default");
        productinfo.put("thum3","default");
        productinfo.put("thum4","default");
        productinfo.put("mobile","default");
        productinfo.put("userName","default");




        databaseReference.child("01762957451"+currentTime).updateChildren(productinfo).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {

                    DatabaseReference publicpost = FirebaseDatabase.getInstance().getReference().child("publicproduct");
                    publicpost.child("01762957451"+currentTime).updateChildren(productinfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){

                                DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference().child("User").child(userid);


                                databaseUser.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){

                                            String namelocal  = dataSnapshot.child("name").getValue().toString();
                                            String phonenumberlocal= dataSnapshot.child("phone").getValue().toString();
                                            HashMap updainfo = new HashMap();
                                            updainfo.put("userName",namelocal);
                                            updainfo.put("mobile",phonenumberlocal);

                                            DatabaseReference productdatabase = FirebaseDatabase.getInstance().getReference().child("storeroom").child(userid).child("01762957451"+currentTime);
                                            DatabaseReference publicproduct = FirebaseDatabase.getInstance().getReference().child("publicproduct").child("01762957451"+currentTime);
                                            productdatabase.updateChildren(updainfo);
                                            productdatabase.updateChildren(updainfo);

                                            publicproduct.updateChildren(updainfo);
                                            publicproduct.updateChildren(updainfo);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                uploadimages(userid,currentTime);


                            }
                        }
                    });

                }
            }
        });

    }

    private void uploadimages(String userid,String currenttime) {

        Toast.makeText(getContext(), "image metod is called", Toast.LENGTH_SHORT).show();
        StorageReference Imagefolder = FirebaseStorage.getInstance().getReference().child("Imagesfolder");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    Log.d("TAG", "run: .................imagelist size "+ imageslist.size());

                    if(imageslist.size()>4){

                        for( i =0;i<4;i++){

                            Uri singleiamge = imageslist.get(i);
                            StorageReference Imagesname = Imagefolder.child("Image"+singleiamge.getLastPathSegment());
                            StorageReference thumnail =Imagefolder.child("Allthumnail"+singleiamge.getLastPathSegment()+".jpg");



                            Imagesname.putFile(singleiamge).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Imagesname.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            HashMap imagelink = new HashMap();

                                            DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("storeroom").child(userid);
                                            DatabaseReference publicproduct = FirebaseDatabase.getInstance().getReference().child("publicproduct");

                                            // imagelink.put("img1"+imagecounter,uri.toString());
                                            Log.d("TAG", "onSuccess: ..................imagecounter index " + imagecounter);



                                            if (imagecounter == 0) {

                                                mdatabase.child("01762957451" + currenttime).child("img1").setValue(uri.toString());
                                                publicproduct.child("01762957451" + currenttime).child("img1").setValue(uri.toString());

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum1").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum1").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            }
                                           else if (imagecounter == 1){
                                                mdatabase.child("01762957451" + currenttime).child("img2").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img2").setValue(uri.toString());

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum2").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum2").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                        }
                                            else if(imagecounter==2){
                                                mdatabase.child("01762957451"+currenttime).child("img3").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img3").setValue(uri.toString());

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();


                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum3").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum3").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            }

                                            else if(imagecounter==3){
                                                mdatabase.child("01762957451"+currenttime).child("img4").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img4").setValue(uri.toString());


                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum4").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum4").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            }

                                            imagecounter++;
                                        }
                                    });
                                }
                            });


                        }
                    }else if(imageslist.size()<=4){

                        for( i =0;i<imageslist.size();i++){

                            Uri singleiamge = imageslist.get(i);
                            StorageReference Imagesname = Imagefolder.child("Image"+singleiamge.getLastPathSegment());
                            StorageReference thumnail =Imagefolder.child("Allthumnail"+singleiamge.getLastPathSegment());
                            Imagesname.putFile(singleiamge).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Imagesname.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            HashMap imagelink = new HashMap();

                                            DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("storeroom").child(userid);
                                            DatabaseReference publicproduct = FirebaseDatabase.getInstance().getReference().child("publicproduct");

                                            // imagelink.put("img1"+imagecounter,uri.toString());
                                            Log.d("TAG", "onSuccess: ..................imagecounter index " + imagecounter);


                                            if (imagecounter == 0) {

                                                mdatabase.child("01762957451" + currenttime).child("img1").setValue(uri.toString());
                                                publicproduct.child("01762957451" + currenttime).child("img1").setValue(uri.toString());


                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum1").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum1").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            } else if (imagecounter == 1){
                                                mdatabase.child("01762957451" + currenttime).child("img2").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img2").setValue(uri.toString());

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum2").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum2").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });

                                            }
                                            else if(imagecounter==2){
                                                mdatabase.child("01762957451"+currenttime).child("img3").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img3").setValue(uri.toString());

                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum3").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum3").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });

                                            }

                                            else if(imagecounter==3){
                                                mdatabase.child("01762957451"+currenttime).child("img4").setValue(uri.toString());
                                                publicproduct.child("01762957451"+currenttime).child("img4").setValue(uri.toString());



                                                Bitmap bmp = null;
                                                try {
                                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), singleiamge);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                                                byte[] data = baos.toByteArray();

                                                UploadTask uploadTask = thumnail.putBytes(data);
                                                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            thumnail.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    mdatabase.child("01762957451" + currenttime).child("thum4").setValue(uri.toString());
                                                                    publicproduct.child("01762957451" + currenttime).child("thum4").setValue(uri.toString());
                                                                }
                                                            });

                                                        }
                                                    }
                                                });
                                            }

                                            imagecounter++;
                                        }
                                    });
                                }
                            });


                        }
                    }

                }
            }).start();

            startActivity(new Intent(getContext(),MyProductActivity.class));




    }




    private Bitmap getbitmap(Uri singleimage) {

        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(singleimage.toString()).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;

    }



    private void BringImagePicker() {

     /*   CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512,512)
                .setAspectRatio(1, 1)
                .start(getContext());*/




    /* Intent intent = CropImage.activity()
             .setAspectRatio(450,400)
             .getIntent(getContext());

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);*/

/*        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/



    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
    intent.setType("image/*");

        startActivityForResult(intent, 1);




      /*  CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512,512)
                .setAspectRatio(1, 1)
                .start(getActivity());*/

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

    private void logout() {

        SharedPreferences pref =getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userexistancy", "null"); // Storing string
        editor.putString("mobile",null);
        editor.putString("loginstatus",null);
        editor.putString("type",null);
        editor.commit();
        startActivity(new Intent(getContext(), SplashActivity.class));


    }

    public static void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context context) {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){

             if(resultCode==RESULT_OK){

                 if(data.getClipData()!=null)
                 {

                      llayout_defaultimage.setVisibility(View.VISIBLE);
                      //removeall.setVisibility(View.VISIBLE);
                      imgproduct.setVisibility(View.INVISIBLE);
                      btn_takepic.setText("Clear All");
                     imageslist=new ArrayList<>();
                     bitmapslist=new ArrayList<>();
                     int countClipdata = data.getClipData().getItemCount();
                     Log.d("TAG", "onActivityResult: ..................clip datasize "+countClipdata);
                     int i =0;
                     while (i<countClipdata){

                         img_uri = data.getClipData().getItemAt(i).getUri();

                         imageslist.add(img_uri);


                         i++;
                     }

                     for(int j =0 ; j<imageslist.size();j++){

                         img1.setImageURI(imageslist.get(0));
                         if(j==1)
                             img2.setImageURI(imageslist.get(1));
                         if(j==2)
                             img3.setImageURI(imageslist.get(2));
                         if(j==3)
                             img4.setImageURI(imageslist.get(3));

                     }


                 }else {
                     llayout_defaultimage.setVisibility(View.VISIBLE);
                     //removeall.setVisibility(View.VISIBLE);
                     imgproduct.setVisibility(View.INVISIBLE);
                     btn_takepic.setText("Clear All");
                     imageslist= new ArrayList<>();
                     img_uri= data.getData();
                     img1.setImageURI(img_uri);
                     imageslist.add(img_uri);
                     Log.d("TAG", "onActivityResult: ..............single image size of imagelist "+imageslist.size());
                     Log.d("TAG", "onActivityResult: .........imaglist index is image  "+ imageslist.get(0));
                 }



             }
        }
    }



/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                img_uri  = result.getUri();

                llayout_defaultimage.setVisibility(View.VISIBLE);
                removeall.setVisibility(View.VISIBLE);
                imgproduct.setVisibility(View.INVISIBLE);

                Picasso.with(getContext()).load(img_uri).into(img1);

                File orginalImagefile =  new File(img_uri.getPath());

                try {
                    Bitmap thumb_bitmap = new Compressor(getContext())
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(orginalImagefile);
                    ByteArrayOutputStream  bos  = new ByteArrayOutputStream();
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                    thumbdata=bos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }*/


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

       if(imageslist!=null){
           imageslist.clear();
           bitmapslist.clear();
       }
        Toast.makeText(context, "on attatch is called", Toast.LENGTH_SHORT).show();
    }
}
