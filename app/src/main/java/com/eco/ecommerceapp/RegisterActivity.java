package com.eco.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private Button register;
    private EditText name,phoneNumber,password;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register_button);
        name=findViewById(R.id.register_Name);
        phoneNumber=findViewById(R.id.register_phone_Number);
        password=findViewById(R.id.register_password);
        loading= new ProgressDialog(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }




    private void createAccount() {

        String uname=name.getText().toString();
        String number=phoneNumber.getText().toString();
        String upassword=password.getText().toString();


        if (TextUtils.isEmpty(uname)){
            Toast.makeText(this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter Your number!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(upassword)){
            Toast.makeText(this, "Please Enter Your password!", Toast.LENGTH_SHORT).show();
        }
        else {
            loading.setTitle("Create Account");
            loading.setMessage("Please Wait!");
            loading.setCanceledOnTouchOutside(false);
            loading.show();


            validatePhoneNumber(uname,number,upassword);
        }
    }

    private void validatePhoneNumber(final String name,final String number,final String password) {

        final DatabaseReference rootRef;
        rootRef= FirebaseDatabase.getInstance("https://ecommerce-app-bc541-default-rtdb.firebaseio.com/").getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number).exists())){

                    HashMap<String,Object> userDataMap=new HashMap<>();
                    userDataMap.put("Phone",number);
                    userDataMap.put("password",password);
                    userDataMap.put("Name",name);

                    rootRef.child("Users").child(number).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registraiton Complete!", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed!, Try Again Letter", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        }
                    });



                }
                else {
                    Toast.makeText(RegisterActivity.this, "This Number is already Exists.", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(RegisterActivity.this, "Try Using Another Number", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}