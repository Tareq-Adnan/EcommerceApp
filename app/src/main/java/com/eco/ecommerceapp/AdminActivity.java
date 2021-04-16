package com.eco.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    private String CategoryName,Description,pname,price,CurrentDate,CurrentTime;
    private ImageView productImage;
    private Button AddProduct;
    private EditText productName,productDescription,productPrice;
    private static final int gallaryPick=1;
    private Uri imageUri;
    private String RandomKey,DownloadImageUrl;
    private StorageReference productImageRef;
    private DatabaseReference productRef;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CategoryName=getIntent().getExtras().get("Category").toString();
        productImage=findViewById(R.id.product_image);
        AddProduct=findViewById(R.id.add_Product);
        productName=findViewById(R.id.produt_Name);
        productDescription=findViewById(R.id.produt_Description);
        productPrice=findViewById(R.id.product_price);
        productImageRef=FirebaseStorage.getInstance("gs://ecommerce-app-bc541.appspot.com/").getReference().child("ProductImages");
        productRef=FirebaseDatabase.getInstance("https://ecommerce-app-bc541-default-rtdb.firebaseio.com/").getReference().child("Products");
        loading=new ProgressDialog(this);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallary();
            }
        });


        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });


    }

    private void openGallary() {


        Intent selectPic=new Intent();
        selectPic.setAction(Intent.ACTION_GET_CONTENT);
        selectPic.setType("image/*");
        startActivityForResult(selectPic,gallaryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==gallaryPick && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            productImage.setImageURI(imageUri);
        }
    }

    private void ValidateProductData() {

        Description=productDescription.getText().toString();
        pname=productName.getText().toString();
        price=productPrice.getText().toString();

        if (imageUri==null){
            Toast.makeText(this, "Please Insert an Image!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please Enter Product Description!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pname)){
            Toast.makeText(this, "Please Enter Product Name!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please Enter Product Price!", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();


        }

    }

    private void StoreProductInformation() {

        loading.setTitle("Uploading");
        loading.setMessage("Please Wait!");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        CurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        CurrentTime=currentTime.format(calendar.getTime());


        RandomKey=CurrentDate+CurrentTime;

        StorageReference filePath= productImageRef.child(imageUri.getLastPathSegment() + RandomKey);

        final UploadTask uploadTask=filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(AdminActivity.this, message, Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminActivity.this, "Image Upload Successful.", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();

                        }

                        DownloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            DownloadImageUrl=task.getResult().toString();
                            loading.dismiss();
                            Toast.makeText(AdminActivity.this, "Product Save Successful.", Toast.LENGTH_SHORT).show();

                            uploadProductInfo();
                        }
                    }
                });
            }
        });
    }

    private void uploadProductInfo() {

        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("PID",RandomKey);
        productMap.put("Date",CurrentDate);
        productMap.put("Time",CurrentTime);
        productMap.put("Description",Description);
        productMap.put("Image",DownloadImageUrl);
        productMap.put("Category",CategoryName);
        productMap.put("Name",pname);
        productMap.put("Price",price);

        productRef.child(RandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                    Intent intent=new Intent(AdminActivity.this,CategoryActivity.class);
                    startActivity(intent);
                    loading.dismiss();
                    Toast.makeText(AdminActivity.this, "Product update Successful.", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.dismiss();
                    String msg=task.getException().toString();
                    Toast.makeText(AdminActivity.this, "Error: "+ msg, Toast.LENGTH_SHORT).show();
                }
                    
            }
        });


    }
}