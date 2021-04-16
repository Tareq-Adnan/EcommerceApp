package com.eco.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eco.ecommerceapp.Model.Users;
import com.eco.ecommerceapp.Prevelent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText login_number,login_password;
    private ProgressDialog loading;
    private String parentDB;
    private CheckBox remember;
    private TextView adminLogin,UserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_number=findViewById(R.id.login_phone_number);
        LoginButton=findViewById(R.id.login_button);
        login_password=findViewById(R.id.login_password);
        loading=new ProgressDialog(this);
        parentDB="Users";
        remember=findViewById(R.id.remember_login);
        Paper.init(this);
        adminLogin=findViewById(R.id.admin);
        UserLogin=findViewById(R.id.userlogin);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUsers();
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminlogin();
            }
        });

        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                adminLogin.setVisibility(View.VISIBLE);
                UserLogin.setVisibility(View.INVISIBLE);
                parentDB="Users";

            }
        });
    }


    private void LoginUsers() {

        String number=login_number.getText().toString();
        String password=login_password.getText().toString();

        if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Please Enter Number!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter your Password!", Toast.LENGTH_SHORT).show();
        }
        else {
            loading.setTitle("Login In");
            loading.setMessage("Please Wait!");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            AllowAccess(number,password);
        }
    }

    private void AllowAccess(String number, String password) {

        if (remember.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,number);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }



        final DatabaseReference  rootref;
        rootref= FirebaseDatabase.getInstance("https://ecommerce-app-bc541-default-rtdb.firebaseio.com/").getReference();

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDB).child(number).exists()){

                    Users usersData=dataSnapshot.child(parentDB).child(number).getValue(Users.class);

                    if (usersData.getPhone().equals(number)){
                        if (usersData.getPassword().equals(password)){

                            if (parentDB.equals("Admin")){
                                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                Intent intent=new Intent(LoginActivity.this,CategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDB.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.onlineUsers=usersData;
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "No Account Found!", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }
            

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void adminlogin() {

        LoginButton.setText("Login Admin");
        adminLogin.setVisibility(View.INVISIBLE);
        UserLogin.setVisibility(View.VISIBLE);
        parentDB="Admin";


    }
}