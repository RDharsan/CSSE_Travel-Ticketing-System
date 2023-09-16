package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

//Passenger complaint class
public class Complaint_2 extends AppCompatActivity {

    EditText Name, complaint;
    ImageView click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint2);

        Name = findViewById(R.id.Name);
        complaint = findViewById(R.id.complaint);
        click = findViewById(R.id.btnshare);

        click.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String uname = Name.getText().toString();
                String ucomplaint = complaint.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_TEXT, "Enter your name"+uname+"\n\ncomplaint"+ucomplaint);
                startActivity(intent);

            }
        });



    }
}