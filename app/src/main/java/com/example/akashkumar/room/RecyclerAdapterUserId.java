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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapterUserId extends RecyclerView.Adapter<RecyclerAdapterUserId.MyHoder>{

    List<UserItemDetails> list;
    Context context;

    public RecyclerAdapterUserId(List<UserItemDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.useritem,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        final UserItemDetails mylist = list.get(position);




        holder.userid.setText(mylist.userId.getUserid());
        holder.price123.setText(mylist.getTotalprice());
        holder.des123.setText(mylist.getRentdesription());


        holder.Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MyAdChat.class);

                intent.putExtra("id",mylist.userId.getUserid());
                context.startActivity(intent);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent=new Intent(context,Userchat.class);
//                intent.putExtra("id",mylist.userId.getUserid());
//                intent.putExtra("price",mylist.getTotalprice());
//                intent.putExtra("des",mylist.getRentdesription());
//                intent.putExtra("owner",mylist.getOwnername());
//                intent.putExtra("url",mylist.getImageurl());
//
//
//                context.startActivity(intent);
//                Toast.makeText(context,"alkash kumar ",Toast.LENGTH_LONG).show();
            }
        });

        Glide.with(context).load(mylist.getImageurl()).into(holder.imageView1);

    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView userid,price123,des123,ownername;
        ImageView imageView1;
        CardView cardView;
        Button Message;



        public MyHoder(View itemView) {
            super(itemView);
            imageView1 =  itemView.findViewById(R.id.imageView);
            userid= (TextView) itemView.findViewById(R.id.uerid);
            price123= (TextView) itemView.findViewById(R.id.price123);
            des123= (TextView) itemView.findViewById(R.id.description123);
            Message=itemView.findViewById(R.id.messagebutton);

            cardView=itemView.findViewById(R.id.cartview);


        }
    }

}
