package com.example.mad;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class user extends AppCompatActivity implements View.OnClickListener {
    public CardView card1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        card1 = (CardView) findViewById(R.id.c1);
        card1.setOnClickListener(this);

    }


    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.c1:
                i = new Intent(this, busDetails.class);
                startActivity(i);


        }

    }}



