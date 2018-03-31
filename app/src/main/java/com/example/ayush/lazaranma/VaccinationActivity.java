package com.example.ayush.lazaranma;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VaccinationActivity extends AppCompatActivity {

    TextView lastvcnname, lastvcndue,lastvcngiven, vcnname, vcndate,name;
    EditText etId;
    Button btVcc;
    public static final int MOTHER_SEL=1;
    public static final int CHILD_SEL=2;
    public static final String INTENT_SEL="IntentSel";
    public static final String PERSON="person";
    public static final int FINGERPRINT_RESULT=2209;
    int f=0;
    private static final String TAG = "VaccinationActivity";

    ParseObject person;
    ParseObject pending;
    ParseObject t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        lastvcnname=findViewById(R.id.tv_pastname);
        lastvcndue=findViewById(R.id.tv_pastdue);
        lastvcngiven=findViewById(R.id.tv_pastgivendate);
        vcnname=findViewById(R.id.tv_currentname);
        vcndate=findViewById(R.id.tv_currentdue);
        name=findViewById(R.id.tv_name);
        etId=findViewById(R.id.tv_ID);
        btVcc=findViewById(R.id.bt_vcc);
        btVcc.setText("Fetch Details");

        btVcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f==0) {
                    if (getIntent().getIntExtra(INTENT_SEL, 200) == MOTHER_SEL) {
                        ParseQuery<ParseObject> pq = ParseQuery.getQuery(Constants.Parse.Mother.TABLE_NAME);
                        CustomProgressDialog.showCustomDialog(VaccinationActivity.this);
                        pq.getInBackground(etId.getText().toString(), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                CustomProgressDialog.dismissCustomDialog();
                                if (e == null) {

                                    person = object;
                                    name.setText(object.getString(Constants.Parse.Mother.NAME_MOTHER));
                                    afterP();
                                }
                            }
                        });
                    } else {
                        ParseQuery<ParseObject> pq = ParseQuery.getQuery(Constants.Parse.Child.TABLE_NAME);
                        CustomProgressDialog.showCustomDialog(VaccinationActivity.this);
                        pq.getInBackground(etId.getText().toString(), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                CustomProgressDialog.dismissCustomDialog();
                                if (e == null) {

                                    person = object;
                                    name.setText(object.getString(Constants.Parse.Child.NAME));
                                    afterP();
                                }
                            }
                        });
                    }
                }else {
                    //start fingerprint
                    startActivityForResult(new Intent(VaccinationActivity.this,FingerPrintActivity.class),FINGERPRINT_RESULT);
                }
            }
        });

    }

    void afterP(){
        final ParseQuery<ParseObject> transactionQuery=ParseQuery.getQuery(Constants.Parse.Transactions.TABLE_NAME);
        transactionQuery.whereEqualTo(Constants.Parse.Transactions.PERSON,person);
        transactionQuery.addDescendingOrder(Constants.Parse.Transactions.DONE_DATE);
        CustomProgressDialog.showCustomDialog(VaccinationActivity.this);
        transactionQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                CustomProgressDialog.dismissCustomDialog();
                if(e==null) {
                    if(objects.size()>0) {
                        lastvcngiven.setText(new SimpleDateFormat("dd/MM/yyyy").format(objects.get(0).getDate(Constants.Parse.Transactions.DONE_DATE)));
                        lastvcndue.setText(new SimpleDateFormat("dd/MM/yyyy").format(objects.get(0).getDate(Constants.Parse.Transactions.DUE_DATE)));
                        lastvcnname.setText(objects.get(0).getString(Constants.Parse.Transactions.VACCINE));

                        ParseQuery<ParseObject> pendingQuery = ParseQuery.getQuery(Constants.Parse.Pending.TABLE_NAME);
                        pendingQuery.whereEqualTo(Constants.Parse.Pending.PERSON, person);
                        CustomProgressDialog.showCustomDialog(VaccinationActivity.this);

                        pendingQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                                                              @Override
                                                              public void done(ParseObject object, ParseException e) {
                                                                  CustomProgressDialog.dismissCustomDialog();
                                                                  if (e == null) {
                                                                      f = 1;
                                                                      pending=object;
                                                                      t = new ParseObject(Constants.Parse.Transactions.TABLE_NAME);
                                                                      t.put(Constants.Parse.Transactions.DUE_DATE,pending.getDate(Constants.Parse.Pending.DUE_DATE));
                                                                      t.put(Constants.Parse.Transactions.DONE_DATE, Calendar.getInstance().getTime());
                                                                      t.put(Constants.Parse.Transactions.PERSON,pending.getParseObject(Constants.Parse.Pending.PERSON));
                                                                      t.put(Constants.Parse.Transactions.VACCINE,pending.getString(Constants.Parse.Pending.VACCINE));
                                                                      btVcc.setText("Vaccinate");
                                                                      vcndate.setText(new SimpleDateFormat("dd/MM/yyyy").format(object.getDate(Constants.Parse.Pending.DUE_DATE)));
                                                                      vcnname.setText(object.getString(Constants.Parse.Pending.VACCINE));
                                                                  } else {
                                                                      e.printStackTrace();
                                                                  }
                                                              }
                                                          }
                        );
                    }
                }else{
                    e.printStackTrace();
                }
            }


        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==FINGERPRINT_RESULT){
            if(data.getIntExtra(FingerPrintActivity.BACK_INTENT,0)==1&&data != null){
                Toast.makeText(this, "Vacinated", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onActivityResult: "+pending.toString());



                t.saveEventually();
                pending.deleteEventually();
                finish();

            }
            if (resultCode == RESULT_CANCELED)
            {

            }
        }
    }
}
