package com.example.mad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Manager class
public class Manager extends AppCompatActivity {
    Button logout,bus,BusPackage,driver,book;


    //Create Main page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );
        logout = findViewById( R.id.log );
        bus = findViewById( R.id.empa );
        BusPackage = findViewById( R.id.pac );
        driver = findViewById( R.id.emp );
        book = findViewById( R.id.book );


        BusPackage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BusPackage = new Intent( Manager.this, Manager_package.class );
                startActivity( BusPackage );

            }
        } );

        //logout

        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent( Manager.this, Login.class );
                Toast.makeText( getApplicationContext(), "LOGOUT", Toast.LENGTH_SHORT ).show();
                startActivity( logout );
            }
        } );
        bus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bus = new Intent( Manager.this, Bus.class );
                startActivity( bus );
            }
        } );
        driver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent driverloyee = new Intent( Manager.this, Driver.class );
                startActivity( driverloyee );
            }
        } );

        book.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent driverloyee = new Intent( Manager.this, Manager_booking.class );
                startActivity( driverloyee );


            }
        } );

    }
}
