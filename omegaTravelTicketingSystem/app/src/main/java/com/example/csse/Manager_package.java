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
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

//Package details functions
public class Manager_package extends AppCompatActivity {
    DBpackage db;
    EditText pId,pName,pCost,pFeatures;
    Button add,delete,update,view,logout,search;
    AwesomeValidation awesomeValidation;
    String[] type_of_package = {"Per day package ", "Monthly package", "Pay as you go"};
    private TextView checktext;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpackage);
//        checkButton = findViewById(R.id.cb);
//        checktext = findViewById(R.id.ct);

        db=new DBpackage(this);

        pId=(EditText)findViewById(R.id.p1);
        pName=(EditText)findViewById(R.id.p2);
        pCost=(EditText)findViewById(R.id.p3);
        pFeatures=(EditText)findViewById(R.id.p4);

        add=findViewById(R.id.add);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        view=findViewById(R.id.view);
        search=findViewById(R.id.search);
        logout=findViewById(R.id.log);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        SearchData();
        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.p1, RegexTemplate.NOT_EMPTY, R.string.invalid_packageID);
        awesomeValidation.addValidation(this, R.id.p2, RegexTemplate.NOT_EMPTY, R.string.invalid_packagename);
        awesomeValidation.addValidation(this,R.id.p3, Range.closed(500,20000),R.string.invalid_packagecost);
        awesomeValidation.addValidation(this, R.id.p4, RegexTemplate.NOT_EMPTY, R.string.invalid_facilities_pac );


//        checkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (pCost.getText().toString().length() == 0) {
//                    pCost.setText("0");
//                }
////                checktext.setText(String.valueOf(calculate()));
//
//
//            }
//        });


        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log=new Intent( Manager_package.this, Manager.class );

                startActivity( log );
            }
        } );



        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type_of_package);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);



    }

    final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(type_of_package[position]);
                    pName.setText(s1);
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
                        Integer deletedRows =db.deleteData(pId.getText().toString());
                        if(deletedRows >0) {
                            Toast.makeText( Manager_package.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data Not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void UpdateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate= db.updateData(pId.getText().toString(),pName.getText().toString(),pCost.getText().toString(),pFeatures.getText().toString());
                        if(isUpdate==true && awesomeValidation.validate()) {
                            Toast.makeText( Manager_package.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data Not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData(){
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted=db.insertData(pId.getText().toString(),pName.getText().toString(),pCost.getText().toString(),pFeatures.getText().toString());
                        if(isInserted == true && awesomeValidation.validate()) {
                            Toast.makeText( Manager_package.this, "Data Inserted", Toast.LENGTH_SHORT ).show();
                            clearControls();
                        } else
                            Toast.makeText(Manager_package.this,"Data  notInserted",Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }  public void viewAll(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.getAllData();
                        Cursor res =db.getAllData();
                        if(res.getCount()==0){
                            showMessage("View is Empty !!!","No Data Found");
                            return;
                        }
                        StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Package ID :"+res.getString(0)+"\n");
                            buffer.append("Package Type :"+res.getString(1)+"\n");
                            buffer.append("Package Cost :"+res.getString(2)+"\n");
                            buffer.append("Package features:"+res.getString(3)+"\n");


                        }
                        showMessage("Package Details",buffer.toString());

                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = db.searchData(pId.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    pId.setText( data.getString(0));
                    pName.setText( data.getString(1));
                    pCost.setText( data.getString(2));
                    pFeatures.setText( data.getString(3));


                }
            }
        });

    }
    private void clearControls(){
        pId.setText("");
        pName.setText("");
        pCost.setText("");
        pFeatures.setText("");


    }

//    public float calculate() {
//        float rcost1 = 0.25F;
//        float rcost2 =  12000;
//
//        float rnum = Integer.parseInt(pCost.getText().toString());
//
//
//        if(pCost.getText().toString().equalsIgnoreCase("10000")){
//
//           float cal = rcost1 * rnum;
//            return cal;
//
//
//        }
//        else{
//            float cal = rcost2 * rnum;
//            return cal;
//        }
//    }


}



