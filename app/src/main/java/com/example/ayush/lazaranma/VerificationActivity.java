package com.example.ayush.lazaranma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.ArrayList;
import java.util.List;

public class VerificationActivity extends AppCompatActivity implements MyAdapter.ClickListener {


    private RecyclerView recyclerView;
    MyAdapter adapter;
    final Context c = this;
    TextView tv_msg;
    SmsVerifyCatcher smsVerifyCatcher;

    List<Information> data=new ArrayList<>();
        Button b;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verification);
            smsVerifyCatcher = new SmsVerifyCatcher(VerificationActivity.this, new OnSmsCatchListener<String>() {
                @Override
                public void onSmsCatch(String message) {
                    tv_msg.setText(message);
                }
            });
            b=findViewById(R.id.offline);
            recyclerView= (RecyclerView) findViewById(R.id.recycler);
            int[]icons={R.drawable.folder,R.drawable.dropper,R.drawable.masculine,R.drawable.stethoscope};
            for(int i=0; i<icons.length; i++)
            {
                Information current=new Information();
                current.iconid=icons[i];
                current.stringid=0;
                data.add(current);
            }
            adapter= new MyAdapter(this,data);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                    View mView = layoutInflaterAndroid.inflate(R.layout.alert_dialog_fetch, null);
                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                    alertDialogBuilderUserInput.setView(mView);
                    final TextInputEditText et_adhrnumber=mView.findViewById(R.id.et_adhrnumber);
                    final Button send=mView.findViewById(R.id.bt_send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String msg=et_adhrnumber.getText().toString();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("+919891127886", null, msg, null, null);

                        }
                    });
                    tv_msg=mView.findViewById(R.id.tv_message);
                    alertDialogBuilderUserInput
                            .setCancelable(false)
                            .setNeutralButton("Verify", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(c, "Yoyo", Toast.LENGTH_SHORT).show();
//                                    dialogInterface.cancel();
                                    Toast.makeText(c, "Data Stored", Toast.LENGTH_SHORT).show();
                                    //Save Data into transaction table
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                    alertDialogAndroid.show();
                }

            });



        }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void itemClicked(View v, int position) {
            if(position==0) {
                Intent intent = new Intent(getApplication(), RegistrationMother.class);
                intent.putExtra("name", position);
                startActivity(intent);
            }
            else if(position==2)
            {
                Intent intent = new Intent(getApplication(), RegisterationChildrenActivity.class);
                intent.putExtra("name", position);
                startActivity(intent);
            }
            else if(position==1)
            {
                //Query Mother Table
                Toast.makeText(this, "Banana Hai abhi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), VaccinationActivity.class);
                intent.putExtra("name", position);
                startActivity(intent);
            }
            else
            {
                //Query Child Table
                Toast.makeText(this, "Banana Hai abhi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), VaccinationActivity.class);
                intent.putExtra("name", position);
                startActivity(intent);
            }
    }

}
