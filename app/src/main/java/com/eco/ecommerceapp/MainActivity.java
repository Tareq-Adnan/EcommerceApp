package com.eco.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eco.ecommerceapp.Model.Users;
import com.eco.ecommerceapp.Prevelent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button login,join;
    private ProgressDialog loading;
    private String parentDB="Users";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.login);
        join=findViewById(R.id.join);
        Paper.init(this);
        loading=new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        String UserNumber = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPass=Paper.book().read(Prevalent.UserPasswordKey);

        if (UserNumber !="" && UserPass !=""){
            if (!TextUtils.isEmpty(UserNumber) && !TextUtils.isEmpty(UserPass)){
                AllowAccess(UserNumber,UserPass);
            }

        }

    }

    private void AllowAccess(String number, String password) {


        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance("https://ecommerce-app-bc541-default-rtdb.firebaseio.com/").getReference();

        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDB).child(number).exists()){

                    Users usersData=dataSnapshot.child(parentDB).child(number).getValue(Users.class);

                    if (usersData.getPhone().equals(number)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                            Prevalent.onlineUsers=usersData;
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "No Account Found!", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}