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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private byte[] thumbdata;
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
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "button is clicked", Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){

                    if(!TextUtils.isEmpty(edt_price.getText().toString()) && ! TextUtils.isEmpty(edt_name.getText().toString())){

                        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userid = mUser.getUid();
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("storeroom").child(userid+currentTime);
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

                        databaseReference.updateChildren(productinfo).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getContext(), "Post successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), MyProductActivity.class));
                                    getActivity().finish();

                                }
                            }
                            });

                    }else if(TextUtils.isEmpty(edt_price.getText().toString())){

                        edt_price.setError("must required");

                    }else if(TextUtils.isEmpty(edt_name.getText().toString())){
                        edt_name.setError("Must required filed");
                    }
                }

            }
        });




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
        getActivity().finish();

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
       /* if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            llayout_defaultimage.setVisibility(View.VISIBLE);
            removeall.setVisibility(View.VISIBLE);
            imgproduct.setVisibility(View.INVISIBLE);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                img1.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }*/



         if (requestCode == 1) {


            //List<Bitmap> bitmapslist = new ArrayList<>();
            bitmapslist = new ArrayList<>();
            imageslist = new ArrayList<>();
            ClipData clipData = data.getClipData();
            Uri localimaguri;


            /*if (data.getClipData() != null) {

                int countclipdata = data.getClipData().getItemCount();
                int k = 0;
                while (k < countclipdata) {

                    img_uri = data.getClipData().getItemAt(k).getUri();
                    imageslist.add(img_uri);
                    k++;
                }

                //Picasso.with(getContext()).load(imageslist.get(1)).into(img1);
                llayout_defaultimage.setVisibility(View.VISIBLE);
                removeall.setVisibility(View.VISIBLE);
                imgproduct.setVisibility(View.INVISIBLE);
            }*/

                if(clipData!= null){
                    llayout_defaultimage.setVisibility(View.VISIBLE);
                    removeall.setVisibility(View.VISIBLE);
                    imgproduct.setVisibility(View.INVISIBLE);

                    for(int i=0; i<clipData.getItemCount();i++){

                        img_uri = clipData.getItemAt(i).getUri();
                        localimaguri=data.getClipData().getItemAt(i).getUri();
                        imageslist.add(localimaguri);
                        try {
                            InputStream is = getContext().getContentResolver().openInputStream(img_uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            bitmapslist.add(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int j = 1; j<bitmapslist.size();j++){

                        img1.setImageBitmap(bitmapslist.get(1));
                        if(j==2)
                            img1.setImageBitmap(bitmapslist.get(2));
                        if(j==3)
                            img2.setImageBitmap(bitmapslist.get(3));
                        if(j==4)
                            img3.setImageBitmap(bitmapslist.get(4));

                    }


                }else {
                    llayout_defaultimage.setVisibility(View.VISIBLE);
                    removeall.setVisibility(View.VISIBLE);
                    imgproduct.setVisibility(View.INVISIBLE);
                    img_uri = data.getData();
                    try {
                        InputStream is = getContext().getContentResolver().openInputStream(img_uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmapslist.add(bitmap);
                        img1.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
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


}
