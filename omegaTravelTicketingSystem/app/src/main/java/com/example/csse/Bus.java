package com.example.mad;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

//Bus functions
public class Bus extends AppCompatActivity {
    DB_Bus myDb;
    EditText busId, busType, busSeat, busCost, busFac;
    Button add,search;
    Button view;
    Button update;
    Button delete;
    AwesomeValidation awesomeValidation;
    Button log;
    //Spinner
    String[] BusCategory = {"AC", "Non-AC"," Luxary"," Superluxary"};


    //Create Bus
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_dvehicle);
        myDb=new DB_Bus(this);
        busId = findViewById(R.id.id);
        busType = findViewById(R.id.type);
        busSeat = findViewById(R.id.vehicle);
        busCost = findViewById(R.id.cost);
        busFac = findViewById(R.id.fac);
        add = findViewById(R.id.fb1);
        view = findViewById(R.id.button7 );
        update = findViewById(R.id.button4);
        delete = findViewById(R.id.fb2);
        search = findViewById(R.id.search);
        log=findViewById(R.id.log);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        SearchData();

        log.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout =new Intent( Bus.this, Manager.class );
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity( logout );
            }
        } );
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.id, RegexTemplate.NOT_EMPTY,R.string.invalid_id);
        awesomeValidation.addValidation(this,R.id.type, RegexTemplate.NOT_EMPTY,R.string.invalid_Bustype);
        awesomeValidation.addValidation(this,R.id.vehicle,".{1}",R.string.invalid_Busseat);
        awesomeValidation.addValidation(this,R.id.cost, Range.closed(50,20000),R.string.invalid_Buscost);
        awesomeValidation.addValidation(this,R.id.fac, RegexTemplate.NOT_EMPTY,R.string.invalid_Busfacilities);



        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, BusCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);



                }

                final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
                        new AdapterView.OnItemSelectedListener() {

                            //Performing action onItemSelected and onNothing selected
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                String s1 = String.valueOf(BusCategory[position]);
                    busType.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };




    public void DeleteData(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows =myDb.deleteData(busId.getText().toString());
                        if(deletedRows >0) {
                            Toast.makeText( Bus.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Bus.this,"Data Not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    //Update Bus
    public void UpdateData() {
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isUpdate = myDb.updateData(busId.getText().toString(), busType.getText().toString(), busSeat.getText().toString(), busCost.getText().toString(), busFac.getText().toString());
                        if (isUpdate == true &&awesomeValidation.validate()) {
                            Toast.makeText( Bus.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Bus.this, "Data Not updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    //add Bus details
    public void AddData(){
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(busId.getText().toString(),busType.getText().toString(),busSeat.getText().toString(),busCost.getText().toString(),busFac.getText().toString());
                        if(isInserted == true &&awesomeValidation.validate() ) {
                            clearControls();
                            Toast.makeText( Bus.this, "Data Inserted", Toast.LENGTH_LONG ).show();

                        }else
                            Toast.makeText(Bus.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //View Details
    public void viewAll(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDb.getAllData();
                        Cursor res =myDb.getAllData();
                        if(res.getCount()==0){
                            showMessage("View is Empty !!!","No Data Found");
                            return;
                        }
                        StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Bus ID :"+res.getString(0)+"\n");
                            buffer.append("Bus Type :"+res.getString(1)+"\n");
                            buffer.append("No Of Seats :"+res.getString(2)+"\n");
                            buffer.append("Bus Cost:"+res.getString(3)+"\n");
                            buffer.append("Bus Facilities:"+res.getString(4)+"\n\n");

                        }
                        showMessage("Bus Details",buffer.toString());

                    }
                }
        );
    }
    //Show message
    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    //Search Bus data
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = myDb.searchData(busId.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    busId.setText( data.getString(0));
                    busType.setText( data.getString(1));
                    busSeat.setText( data.getString(2));
                    busCost.setText( data.getString(3));
                    busFac.setText( data.getString(4));

                }
            }
        });

    }
    private void clearControls(){
        busId.setText("");
        busType.setText("");
        busSeat.setText("");
        busCost.setText("");
        busFac.setText("");

    }
}