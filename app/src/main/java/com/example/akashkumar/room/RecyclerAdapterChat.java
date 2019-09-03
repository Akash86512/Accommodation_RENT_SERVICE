package com.example.akashkumar.room;

/**
 * Created by Amisha on 22-02-2019.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapterChat extends RecyclerView.Adapter<RecyclerAdapterChat.MyHoder>{

    List<Textleftright> list;
    Context context;

    public RecyclerAdapterChat(List<Textleftright> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        Textleftright mylist = list.get(position);

        if(mylist.getId1()==1)
        {

            holder.righttext.setText(mylist.getText());
            holder.lefttext.setText("");



        }else
        {

        }


        if(mylist.getId1()==2)
        {

            holder.lefttext.setText(mylist.getText());
            holder.righttext.setText("");

        }else
        {

        }


        // holder.lefttext.setText(mylist.getText());
    }

    @Override
    public int getItemCount() {



        return list.size() ;

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView lefttext,righttext;




        public MyHoder(View itemView) {
            super(itemView);

            lefttext= (TextView) itemView.findViewById(R.id.lefttext);
            righttext= (TextView) itemView.findViewById(R.id.righttext);


        }
    }

}
