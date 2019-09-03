package com.example.akashkumar.room;

/**
 * Created by Amisha on 22-02-2019.
 */
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder>{

    List<UserData> list;
    Context context;

    public RecyclerAdapter(List<UserData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chatuser,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        final UserData mylist = list.get(position);

        holder.address1.setText(mylist.sortDistance.uploadData.getOwnerlocation());
         holder.price.setText(mylist.sortDistance.uploadData.getTotalprice());
         holder.distance.setText(String.valueOf(mylist.sortDistance.getDistance())+" Km");

         holder.roomdetails.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,RoomRentDetails.class);



                 intent.putExtra("userid",mylist.sortDistance.uploadData.getUserid());
                 intent.putExtra("mobilenumber",mylist.sortDistance.uploadData.getMobilenumber());
                 intent.putExtra("latitude",mylist.sortDistance.uploadData.getLatitude());
                 intent.putExtra("longitude",mylist.sortDistance.uploadData.getLongitude());
                 intent.putExtra("ownerlocation",mylist.sortDistance.uploadData.getOwnerlocation());

                 intent.putExtra("url",mylist.sortDistance.uploadData.getImageurl());
                 intent.putExtra("ownername",mylist.sortDistance.uploadData.getOwnername());
                 intent.putExtra("roomtype",mylist.sortDistance.uploadData.getRoomtype());
                 intent.putExtra("bentoutTo",mylist.sortDistance.uploadData.getBentoutTo());
                 intent.putExtra("bacheloer",mylist.sortDistance.uploadData.getBacheloer());

                 intent.putExtra("bathroom",mylist.sortDistance.uploadData.getBathroom());
                 intent.putExtra("floornumber",mylist.sortDistance.uploadData.getFloornumber());
                 intent.putExtra("bentoutTo",mylist.sortDistance.uploadData.getBentoutTo());
                 intent.putExtra("diamention",mylist.sortDistance.uploadData.getDiamention());

                 intent.putExtra("totalprice",mylist.sortDistance.uploadData.getTotalprice());
                 intent.putExtra("securitymoney",mylist.sortDistance.uploadData.getSecuritymoney());
                 intent.putExtra("fulladress",mylist.sortDistance.uploadData.getFulladress());
                 intent.putExtra("rentdesription",mylist.sortDistance.uploadData.getRentdesription());
                 context.startActivity(intent);
             }
         });

         holder.roomtype.setText(mylist.sortDistance.uploadData.getRoomtype());
        Glide.with(context).load(mylist.sortDistance.uploadData.getImageurl()).into(holder.imageView);
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
        TextView distance,price,roomtype,address1;
        ImageView imageView;
        CardView roomdetails;


        public MyHoder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imageView);
            address1= (TextView) itemView.findViewById(R.id.address);
            distance= (TextView) itemView.findViewById(R.id.distance);
            price= (TextView) itemView.findViewById(R.id.price);
            roomtype= (TextView) itemView.findViewById(R.id.roomtype1);
            roomdetails=  itemView.findViewById(R.id.roomdetails);

        }
    }

}
