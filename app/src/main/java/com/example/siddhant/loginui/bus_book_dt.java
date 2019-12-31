package com.example.siddhant.loginui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;
import java.util.Date;

public class bus_book_dt extends AppCompatActivity {

    EditText mt1;
    EditText mt2;
    Button btn,btn2,gtbtn;
    Calendar c;
    DatePickerDialog dp;

    AutoCompleteTextView psrc,pdes;
    String trip_type;

    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_book_dt);


        btn=findViewById(R.id.dpbtn);
        btn2=findViewById(R.id.dpbtn2);
        mt1=findViewById (R.id.datetime1);
        mt2=findViewById (R.id.datetime2);


        //btn listener for 1st data picker

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();

                final int day= c.get(Calendar.DAY_OF_MONTH);
                final int month=c.get(Calendar.MONTH);
                final int year=c.get(Calendar.YEAR);


                dp=new DatePickerDialog(bus_book_dt.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker , int myear, int ymonth, int dayOfMonth) {

                        datePicker.setMinDate(System.currentTimeMillis()-1000);
                        mt1.setText(dayOfMonth+"/"+ (ymonth) + "/"+myear);

                    }
                },year,month,day);


                dp.show();
            }
        } );

        //button listner for 2nd datapicker


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                final int day= c.get(Calendar.DAY_OF_MONTH);
                final int month=c.get(Calendar.MONTH);
                final int year=c.get(Calendar.YEAR);



                dp=new DatePickerDialog(bus_book_dt.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker , int myear, int ymonth, int dayOfMonth) {
                        datePicker.init(year,month,day,null  );
                        mt2.setText(dayOfMonth+"/"+ (ymonth) + "/"+myear);

                    }
                },year,month,day);
                dp.show();
            }
        } );



        gtbtn=findViewById(R.id.gt_confirm);

        psrc=findViewById(R.id.psrc);
        pdes=findViewById(R.id.pdes);




        sw=findViewById(R.id.switch1);

        gtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(psrc.getText().toString().isEmpty())
                {
                    psrc.setError("Please enter the source");
                }
                else if(pdes.getText().toString().isEmpty())
                {
                    pdes.setError("Please enter the destination");
                }
                else if(mt1.getText().toString().isEmpty())
                {
                    mt1.setError("Please enter starting date");
                }
                else if(mt2.getText().toString().isEmpty())
                {
                    mt2.setError("Please enter ending date");
                }

                else {

                    if(sw.isChecked())
                    {
                        trip_type="One way trip";
                    }
                    else {
                        trip_type="Round trip";
                    }

                    Intent i= new Intent(bus_book_dt.this,confirm_book.class);


                    //storing the data in array
                    String[] dt_arr={psrc.getText().toString(),pdes.getText().toString(),mt1.getText().toString(),mt2.getText().toString(),trip_type};

                    //passing array to
                    i.putExtra("dtarr",dt_arr);
                    startActivity(i);

                }

            }
        });


    }


}
