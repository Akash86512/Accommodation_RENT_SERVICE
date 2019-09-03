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
import android.widget.TextView;

import java.util.List;

/**
 * Created by csa on 3/7/2017.
 */

public class RecyclerAdapterMyasUserChat extends RecyclerView.Adapter<RecyclerAdapterMyasUserChat.MyHoder>{

    List<MyAdUserChat> list;
    Context context;

    public RecyclerAdapterMyasUserChat(List<MyAdUserChat> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.myaduserchat,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        final MyAdUserChat mylist = list.get(position);

        holder.chatname.setText(mylist.getName());
        holder.chatnumber.setText(mylist.getMobilenumber());
        holder.chatcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,Userchat.class);

                intent.putExtra("itemid",mylist.getId());
                intent.putExtra("ok","ok");
                intent.putExtra("chatname",mylist.getName());
                intent.putExtra("chatnumber",mylist.getMobilenumber());
                intent.putExtra("ownermobilenumber",mylist.getOwnermobilenumber());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {



        return list.size() ;

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView chatname,chatnumber;
        CardView chatcard;




        public MyHoder(View itemView) {
            super(itemView);

            chatname= (TextView) itemView.findViewById(R.id.chatname);
            chatnumber= (TextView) itemView.findViewById(R.id.chatnumber);
            chatcard=itemView.findViewById(R.id.chatcard);


        }
    }

}
