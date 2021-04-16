package com.eco.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {

    private ImageView shirt,sportsShirt,femaleDress,sweaters,watches,bags,hats,shoes,headphones,laptops,mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        shirt=findViewById(R.id.tshirt);
        sportsShirt=findViewById(R.id.sports);
        femaleDress=findViewById(R.id.female_Dress);
        sweaters=findViewById(R.id.swaters);
        watches=findViewById(R.id.watches);
        bags=findViewById(R.id.bags);
        hats=findViewById(R.id.hats);
        shoes=findViewById(R.id.shoes);
        headphones=findViewById(R.id.headphones);
        laptops=findViewById(R.id.laptop);
        mobiles=findViewById(R.id.mobile);


        Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();

        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","shirt");
                startActivity(intent);
            }
        });

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","Sweaters");
                startActivity(intent);
            }
        });

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","bags");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","hats");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","shoes");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","watches");
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","headphones");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","laptops");
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","mobiles");
                startActivity(intent);
            }
        });

        sportsShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","Sports Shirts");
                startActivity(intent);
            }
        });

        femaleDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,AdminActivity.class);
                intent.putExtra("Category","Female Dress");
                startActivity(intent);
            }
        });
    }
}