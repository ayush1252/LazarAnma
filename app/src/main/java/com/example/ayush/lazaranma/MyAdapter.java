package com.example.ayush.lazaranma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by akc on 6/22/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    LayoutInflater inflater;
    Context context;
    List<Information> data= Collections.emptyList();
    private ClickListener clickListener;

    public MyAdapter(Context context, List<Information> data)
    {
        inflater= LayoutInflater.from(context);
        this.context=context;
        this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_layout,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.imageView.setImageResource(current.iconid);
        String[] value=context.getResources().getStringArray(R.array.Heading);
       holder.textView1.setText(value[position]);
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }
    public int getItemCount() {
        return 4;
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView1;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView= (ImageView) itemView.findViewById(R.id.image);
            textView1= (TextView) itemView.findViewById(R.id.textcustom);


        }

        @Override
        public void onClick(View v) {
           if(clickListener!=null)
           {
               clickListener.itemClicked(v,getPosition());
           }
        }
    }
public interface ClickListener{
    public void itemClicked(View v, int position);
}
}


