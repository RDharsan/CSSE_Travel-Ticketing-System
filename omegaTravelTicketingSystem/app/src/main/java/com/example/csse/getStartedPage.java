package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Get started page display page
public class getStartedPage extends AppCompatActivity {
    Button wb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swc);
        wb2=(Button)findViewById(R.id.wb21);

        wb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getStartedPage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}