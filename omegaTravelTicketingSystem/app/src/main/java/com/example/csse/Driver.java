package com.example.mad;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
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

//Driver class
public class Driver extends AppCompatActivity {

    DB_Driver mydb;
    Button logout,search;
    Spinner spinner1;
    EditText Did,Dname,Daddress,Dphone,Demail,Dtype,Dsalary;
    Button viewbtn,addbtn,editbtn,deletebtn;
    String[] Type = {"Fulltime", "Partime","Weekend"};
    AwesomeValidation awesomeValidation;

    //create driver
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d__employee);
        logout=findViewById(R.id.log);
        mydb = new DB_Driver(this);
        Did=(EditText)findViewById(R.id.emp1);
        Dname=(EditText)findViewById(R.id.emp2);
        Daddress=(EditText)findViewById(R.id.emp3);
        Dphone=(EditText)findViewById(R.id.emp4);
        Demail=(EditText)findViewById(R.id.emp5);
        Dtype=(EditText)findViewById(R.id.emp6);
        Dsalary=(EditText)findViewById(R.id.emp8);
        addbtn=(Button)findViewById(R.id.btn2);
        viewbtn=(Button)findViewById(R.id.btn1);
        editbtn=(Button)findViewById(R.id.btn3);
        deletebtn=(Button)findViewById(R.id.btn4);
        search=(Button)findViewById(R.id.btn5);
        AddData();
        UpdateDetail();
        DeleteDetail();
        ViewDetail();
        SearchData();
        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.emp1, RegexTemplate.NOT_EMPTY, R.string.ivalid_Id);
        awesomeValidation.addValidation(this, R.id.emp2, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.emp3, RegexTemplate.NOT_EMPTY, R.string.ivalid_address);
        awesomeValidation.addValidation(this,R.id.emp4,".{10}",R.string.invalid_phoneno);
        awesomeValidation.addValidation(this, R.id.emp5, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        awesomeValidation.addValidation(this, R.id.emp6, RegexTemplate.NOT_EMPTY, R.string.ivalid_position);
        awesomeValidation.addValidation(this,R.id.emp8, Range.closed(5000,200000),R.string.invalid_salary);









        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);



    }

    final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(Type[position]);
                    Dtype.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };


    //Delete Details
    public void DeleteDetail() {
        deletebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = mydb.deleteDetail(Did.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText( Driver.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();

                        } else
                            Toast.makeText(Driver.this, "Data Not deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    //update code
    public void UpdateDetail() {
        editbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = mydb.updateDetail(Did.getText().toString(), Dname.getText().toString(), Daddress.getText().toString(), Dphone.getText().toString(), Demail.getText().toString(), Dtype.getText().toString(), Dsalary.getText().toString());
                        if (isUpdate == true && awesomeValidation.validate()) {
                            Toast.makeText( Driver.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Driver.this, "Data Not updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    //create code
    public void AddData() {

        addbtn.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isInserted = mydb.insertData(Did.getText().toString(), Dname.getText().toString(), Daddress.getText().toString(), Dphone.getText().toString(), Demail.getText().toString(), Dtype.getText().toString(), Dsalary.getText().toString());
                        if (isInserted == true && awesomeValidation.validate()) {
                            Toast.makeText( Driver.this, "Data Inserted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Driver.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(Driver.this, Manager.class);
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity(logout);
            }
        });

    }
    //view data code
    public void ViewDetail() {
        viewbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydb.getAllData();
                        Cursor res = mydb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("View is Empty !!!", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Driver ID :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Address :" + res.getString(2) + "\n");
                            buffer.append("Telephone :" + res.getString(3) + "\n");
                            buffer.append(" Mail :" + res.getString(4) + "\n");
                            buffer.append(" Driver type :" + res.getString(5) + "\n");
                            buffer.append("Salary :" + res.getString(6) + "\n\n");

                        }
                        showMessage("Driver Details", buffer.toString());

                    }
                }
        );
    }

    private void showMessage(String title, String toString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(toString);
        builder.show();
    }
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = mydb.searchData(Did.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    Did.setText( data.getString(0));
                    Dname.setText( data.getString(1));
                    Daddress.setText( data.getString(2));
                    Dphone.setText( data.getString(3));
                    Demail.setText( data.getString(4));
                    Dtype.setText( data.getString(5));
                    Dsalary.setText( data.getString(6));


                }
            }
        });

    }
    private void clearControls(){
        Did.setText("");
        Dname.setText("");
        Daddress.setText("");
        Dphone.setText("");
        Demail.setText("");
        Dtype.setText("");
        Dsalary.setText("");

    }







}

