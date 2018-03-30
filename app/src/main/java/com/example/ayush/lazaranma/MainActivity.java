package com.example.ayush.lazaranma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import javax.sql.ConnectionPoolDataSource;

public class MainActivity extends AppCompatActivity {

    Button bt_hospital, bt_anma;
    final Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_anma=findViewById(R.id.bt_anma);
        bt_hospital=findViewById(R.id.bt_hospital);

        bt_anma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.alert_dialog_login, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final TextInputEditText et_id=mView.findViewById(R.id.et_id);
                final TextInputEditText et_pass=mView.findViewById(R.id.et_pass);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here

                                ConnectivityManager cm =
                                        (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                boolean isConnected = activeNetwork != null &&
                                        activeNetwork.isConnectedOrConnecting();

                                if(!isConnected){
                                    loginOffline(et_id.getText().toString(),et_pass.getText().toString());
                                }else {
                                    CustomProgressDialog.showCustomDialog(MainActivity.this);

                                    ParseUser.logInInBackground(et_id.getText().toString(), et_pass.getText().toString(), new LogInCallback() {
                                        @Override
                                        public void done(ParseUser user, ParseException e) {
                                            CustomProgressDialog.dismissCustomDialog();
                                            if (e == null) {
                                                login(et_id.getText().toString(), et_pass.getText().toString());
                                            } else {
                                                Toast.makeText(c, "Login Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        bt_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.alert_dialog_login, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final TextInputEditText et_id=mView.findViewById(R.id.et_id);
                final TextInputEditText et_pass=mView.findViewById(R.id.et_pass);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                ConnectivityManager cm =
                                        (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                boolean isConnected = activeNetwork != null &&
                                        activeNetwork.isConnectedOrConnecting();

                                if(!isConnected){
                                    loginOffline(et_id.getText().toString(),et_pass.getText().toString());
                                }else {

                                    CustomProgressDialog.showCustomDialog(MainActivity.this);

                                    ParseUser.logInInBackground(et_id.getText().toString(), et_pass.getText().toString(), new LogInCallback() {
                                        @Override
                                        public void done(ParseUser user, ParseException e) {
                                            CustomProgressDialog.dismissCustomDialog();
                                            if (e == null) {
                                                login(et_id.getText().toString(), et_pass.getText().toString());
                                            } else {
                                                Toast.makeText(c, "Login Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });
    }

    void login(String u,String p){
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString(
                Constants.SharedPreferences.ANMA_ID,u
        ).putString(Constants.SharedPreferences.PASSWORD,p).apply();
        Intent intent=new Intent(getApplicationContext(),RegistrationMother.class);
        startActivity(intent);
    }

    void loginOffline(String u,String p){
        if(u.equals(Utils.readSharedPreferences(MainActivity.this,Constants.SharedPreferences.ANMA_ID))
                &&p.equals(Utils.readSharedPreferences(MainActivity.this, Constants.SharedPreferences.PASSWORD))){
            login(u,p);
        }else{
            Toast.makeText(c, "Offline Login Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
