package com.example.ayush.lazaranma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class VerificationActivity extends AppCompatActivity implements MyAdapter.ClickListener {


        private RecyclerView recyclerView;
        MyAdapter adapter;
        List<Information> data=new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verification);
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



        }


    @Override
    public void itemClicked(View v, int position) {
        Intent intent = new Intent(getApplication(),RegistrationMother.class);
        intent.putExtra("name",position);
        startActivity(intent);
    }

}
