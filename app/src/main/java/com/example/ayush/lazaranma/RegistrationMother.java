package com.example.ayush.lazaranma;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.parse.ParseObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationMother extends AppCompatActivity {
    TextInputEditText et_mn, et_fn, et_madhar, et_fadhar, et_locality, et_house, et_city, et_state, et_localityid, et_mobile, et_email;
   FloatingActionButton fb;
    HashMap<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_mother);

        map.put("ROHINI","1001");
        map.put("PITAMPURA","1231");
        map.put("CHOWK","1002");
        et_mn=findViewById(R.id.et_mn);
        et_fn=findViewById(R.id.et_fn);
        et_madhar=findViewById(R.id.et_maadhar);
        et_fadhar=findViewById(R.id.et_faadhar);
        et_locality=findViewById(R.id.et_locality);
        et_house=findViewById(R.id.et_street);
        et_localityid=findViewById(R.id.et_lid);
        et_localityid.setEnabled(false);
        et_city=findViewById(R.id.et_city);
        et_state=findViewById(R.id.et_state);
        et_mobile=findViewById(R.id.et_phone);
        et_email=findViewById(R.id.et_email);
        fb=findViewById(R.id.fb_submit);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_mn.getText().toString()))
                {
                    et_mn.setError("This Field Cannot be Empty");
                    return;
                }
                if(TextUtils.isEmpty(et_madhar.getText().toString()))
                {
                    et_madhar.setError("This Field Cannot be Empty");
                    return;
                }
                if(TextUtils.isEmpty(et_locality.getText().toString()))
                {
                    et_locality.setError("This Field Cannot be Empty");
                    return;
                }
                if(TextUtils.isEmpty(et_state.getText().toString()))
                {
                    et_state.setError("This Field Cannot be Empty");
                    return;
                }
                if(TextUtils.isEmpty(et_city.getText().toString()))
                {
                    et_city.setError("This Field Cannot be Empty");
                    return;
                }
                if(TextUtils.isEmpty(et_mobile.getText().toString()))
                {
                    et_mobile.setError("This Field Cannot be Empty");
                    return;
                }
                String LID="";
                LID=LID+et_state.getText().toString().substring(0,3);
                String str=map.get(et_locality.getText().toString());
                if(str==null)
                    str="1202";
                LID+=str;
                et_localityid.setText(LID);

                ParseObject po=new ParseObject(Constants.Parse.Mother.TABLE_NAME);
                po.put(Constants.Parse.Mother.AADHAR_MOTHER,et_madhar.getText().toString());
                po.put(Constants.Parse.Mother.AADHAR_FATHER,et_fadhar.getText().toString());
                po.put(Constants.Parse.Mother.ADDRESS,et_house.getText().toString()+","+et_locality.getText().toString()
                        +","+et_city.getText().toString()+","+et_state.getText().toString());
                po.put(Constants.Parse.Mother.EMAIL,et_email.getText().toString());
                po.put(Constants.Parse.Mother.LOCATION_ID,et_localityid.getText().toString());
                po.put(Constants.Parse.Mother.NAME_FATHER,et_fn.getText().toString());
                po.put(Constants.Parse.Mother.NAME_MOTHER,et_mn.getText().toString());
                po.put(Constants.Parse.Mother.PHONE,et_mobile.getText().toString());

                po.saveEventually();




            }
        });
    }
}
