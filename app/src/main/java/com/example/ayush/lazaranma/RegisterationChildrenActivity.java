package com.example.ayush.lazaranma;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;

public class RegisterationChildrenActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    EditText etMid,etNAme,etBCN;
    Button btDOB,btRegister;
    Date date;
    private static final String TAG = "RegisterationChildrenAc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_children);
        etMid=findViewById(R.id.et_mid);
        etNAme=findViewById(R.id.et_name);
        etBCN=findViewById(R.id.et_bcn);
        btDOB=findViewById(R.id.bt_dob);
        btRegister=findViewById(R.id.bt_register);

        date= Calendar.getInstance().getTime();

        btDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterationChildrenActivity.this, RegisterationChildrenActivity.this, 2018, 03, 30);
                datePickerDialog.show();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> pq=ParseQuery.getQuery(Constants.Parse.Mother.TABLE_NAME);
                CustomProgressDialog.showCustomDialog(RegisterationChildrenActivity.this);
                pq.getInBackground(etMid.getText().toString(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        CustomProgressDialog.dismissCustomDialog();
                        if(e==null){
                            ParseObject po=new ParseObject(Constants.Parse.Child.TABLE_NAME);
                            po.put(Constants.Parse.Child.BCN,etBCN.getText().toString());
                            po.put(Constants.Parse.Child.DOB,date);
                            po.put(Constants.Parse.Child.NAME,etNAme.getText().toString());
                            po.put(Constants.Parse.Child.LID,object.getString(Constants.Parse.Mother.LOCATION_ID));
                            po.put(Constants.Parse.Child.MOTHER_ID,object);
                            po.saveInBackground();
                            Toast.makeText(RegisterationChildrenActivity.this, "Registered ", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "done: ");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c=Calendar.getInstance();
        c.set(year, month - 1, dayOfMonth, 0, 0);
        date=c.getTime();
    }


}
