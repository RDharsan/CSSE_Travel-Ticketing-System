package com.example.mad;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Calendar;

//Bus Booking functions
public class booking extends AppCompatActivity {
    DBHelper mydB;
    private TextView tvDisplayDate, tvDisplayDate2, type, totalcost;
    private Button btnChangeDate, btnChangeDate2, cButton, btnView, btnUpdate, btnDelete, btnlogout, btncost;
    EditText name, phone, mail, noSeat;
    private int year;
    private int month;
    private int day;
    Spinner spinner1;
    Spinner spinner2;


    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int cur = 0;

    AwesomeValidation awesomeValidation;
    String[] Type = {"AC", "NON AC","LUXURY","SUPERLUXURY"};

    String[] no_of_seat = {"01", "02", "03", "04", "05", "06", "07"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mydB = new DBHelper(this);
        cButton = findViewById(R.id.fb1);
        btnView = findViewById(R.id.view);
        btnUpdate = findViewById(R.id.update);
        btnlogout = findViewById(R.id.logout);
        btnDelete = findViewById(R.id.delete);
        type = findViewById(R.id.type);
        name = findViewById(R.id.personName);
        phone = findViewById(R.id.phoneNo);
        mail = findViewById(R.id.emailId);
        noSeat = findViewById(R.id.carno);
        tvDisplayDate = findViewById(R.id.check_in);
        tvDisplayDate2 = findViewById(R.id.check_out);
        btnChangeDate = findViewById(R.id.button1);
        btnChangeDate2 = findViewById(R.id.search );
        btncost = findViewById(R.id.button6);
        totalcost = findViewById(R.id.cost);


        AddData();
        ViewData();
        UpdateDetail();
        DeleteDetail();




        btnlogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout =new Intent( booking.this,Home.class );
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity( logout );
            }
        } );

        btncost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noSeat.getText().toString().length() == 0) {
                    noSeat.setText("0");
                }
                totalcost.setText(String.valueOf(calculate()));


            }
        });




        //initialize validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.personName, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.emailId, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.phoneNo,".{10}",R.string.invalid_phoneno);


        setCurrentDateOnView();
        addListenerOnButton();

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(onItemSelectedListener1);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner1.setAdapter(aa);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(onItemSelectedListener2);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, no_of_seat);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner2.setAdapter(aa1);


    }

    //Type selection
    AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s1 = String.valueOf(Type[position]);
                    type.setText(s1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };

    AdapterView.OnItemSelectedListener onItemSelectedListener2 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String s2 = String.valueOf(no_of_seat[position]);
                    noSeat.setText(s2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };

    //view booking data
    public void ViewData() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydB.getAllData();
                        Cursor res = mydB.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("View is Empty !!!", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Car Type :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Phone No :" + res.getString(2) + "\n");
                            buffer.append("Email :" + res.getString(3) + "\n");
                            buffer.append("Check In :" + res.getString(4) + "\n");
                            buffer.append("Check Out :" + res.getString(5) + "\n");
                            buffer.append("No of Cars :" + res.getString(6) + "\n");
                            buffer.append("Total cost :" + res.getString(7) + "\n\n");

                        }
                        showMessage("Booking Details", buffer.toString());

                    }
                }
        );
    }
//Show message
    private void showMessage(String rooms_details, String toString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(rooms_details);
        builder.setMessage(toString);
        builder.show();
    }


//Delete booking data
    public void DeleteDetail() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = mydB.deleteDetail(phone.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText( booking.this, "Booking Cancelled", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(booking.this, "Booking Not Cancelled", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
//update booking data

    public void UpdateDetail() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = mydB.updateDetail(type.getText().toString(), name.getText().toString(), phone.getText().toString(), mail.getText().toString(), tvDisplayDate.getText().toString(), tvDisplayDate2.getText().toString(), noSeat.getText().toString(), totalcost.getText().toString());
                        if (isUpdate == true && awesomeValidation.validate()) {
                            Toast.makeText( booking.this, "Booking Details Updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(booking.this, "Booking Details Not Updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
//Add booking data
    public void AddData() {

        cButton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isInserted = mydB.insertData(type.getText().toString(), name.getText().toString(), phone.getText().toString(), mail.getText().toString(), tvDisplayDate.getText().toString(), tvDisplayDate2.getText().toString(), Integer.parseInt(noSeat.getText().toString()), Integer.parseInt(totalcost.getText().toString()));
                        if (isInserted == true && awesomeValidation.validate())
                            Toast.makeText( booking.this, "Booking Confirmed", Toast.LENGTH_LONG ).show();

                        else
                            Toast.makeText(booking.this, "Booking Not Confirmed", Toast.LENGTH_LONG).show();
                    }
                }
        );
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(booking.this, Login.class);
                startActivity(logout);
            }
        });

    }


    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.check_in);
        tvDisplayDate2 = (TextView) findViewById(R.id.check_out);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        tvDisplayDate2.setText(tvDisplayDate.getText().toString());
    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.button1);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });
        btnChangeDate2 = (Button) findViewById(R.id.search );

        btnChangeDate2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID2);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
            case DATE_DIALOG_ID2:
                cur = DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);

        }
        return null;
    }
//Date picker
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if (cur == DATE_DIALOG_ID) {
                // set selected date into textview
                tvDisplayDate.setText("" + new StringBuilder().append(day).append("-").append(month + 1)
                        .append("-").append(year)
                        .append(" "));
            } else {
                tvDisplayDate2.setText("" + new StringBuilder().append(day).append("-").append(month + 1)
                        .append("-").append(year)
                        .append(" "));
            }

        }
    };

    public int calculate() {
        int rcost1 = 100;
        int rcost2 =  120;

        int rnum = Integer.parseInt(noSeat.getText().toString());


        if(type.getText().toString().equalsIgnoreCase("Dexe")){

            int cal = rcost1 * rnum;
            return cal;


        }
        else{
            int cal = rcost2 * rnum;
            return cal;
        }
    }
    private void clearControls(){
        type.setText("");
        name.setText("");
        phone.setText("");
        mail.setText("");
        noSeat.setText("");
        tvDisplayDate.setText("");
        tvDisplayDate2.setText("");
        totalcost.setText("");


    }
}








