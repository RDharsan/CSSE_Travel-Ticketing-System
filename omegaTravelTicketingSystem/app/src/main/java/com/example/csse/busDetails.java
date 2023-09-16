package com.example.mad;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Bus details - passenger
public class busDetails extends AppCompatActivity {
    Button logout,book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_rooms);
        logout=(Button)findViewById(R.id.log);
        book=(Button)findViewById(R.id.button4);


        logout=(Button)findViewById(R.id.log);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log =new Intent(busDetails.this,Home.class);
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity(log);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bu=new Intent(busDetails.this,booking.class);
                startActivity(bu);
            }
        });
    }
}