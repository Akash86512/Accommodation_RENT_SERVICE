package com.example.akashkumar.room;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Userchat extends AppCompatActivity {

    String owneruserid,ownermobilenumber,ownername,roomimageurl;

    TextView send,on,mn;
    EditText inputtext;
    FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView recycle123;
    DatabaseReference myRef,myRef1;
    RecyclerAdapterChat recyclerAdapterChat;
    int i=0,k;
    int f=0;
    int l=0;
    String valuefrommyadchat;
    String number,uploadId;
    FirebaseDatabase database;
    List<Textleftright> textleftrightList =new ArrayList<Textleftright>();
    private DatabaseReference mDatabaseRef;
    String usernumber;
    RecyclerView.SmoothScroller smoothScroller;
    String username1;
    String itemid,chatname,chatnumber,chatowner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchat);

        final Intent intent = getIntent();
        owneruserid=intent.getStringExtra("userid");
        ownermobilenumber=intent.getStringExtra("mobilenumber");
        ownername=intent.getStringExtra("ownername");
        usernumber=intent.getStringExtra("number");
        roomimageurl=intent.getStringExtra("imageurl");


        itemid=intent.getStringExtra("itemid");
        chatname=intent.getStringExtra("chatname");
        chatnumber=intent.getStringExtra("chatnumber");
        chatowner=intent.getStringExtra("ownermobilenumber");



        database = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserChat");







        send=findViewById(R.id.send);
        on=findViewById(R.id.on);
        mn=findViewById(R.id.mn);

        if(ownername==null)
        {
            mn.setText(chatowner);
            on.setText(chatname);
        }else{
            mn.setText(ownermobilenumber);
            on.setText(ownername);

        }




        inputtext=findViewById(R.id.inputtext);
        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        number=user.getPhoneNumber();



        textleftrightList.clear();

        myRef = FirebaseDatabase.getInstance().getReference().child("UserChatTrack");

        myRef1 = FirebaseDatabase.getInstance().getReference().child("Users");

        if(itemid==null)
        {
             show1();
             username();
        }
        else {
           show();
        }




        recycle123 = (RecyclerView)findViewById(R.id.recyclechat);


        //You set the layout manage, default item animator and dividers for recyclerview here, you were doing it in on click.
        RecyclerView.LayoutManager recyce = new GridLayoutManager(this,1);
        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycle123.setLayoutManager(recyce);



        recycle123.setItemAnimator( new DefaultItemAnimator());



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=inputtext.getText().toString();





                if(text.isEmpty())
                {

                }else {

                    l=1;

                    if(itemid==null)
                    {
                        myRef.child(owneruserid).child(usernumber).child("mobilenumber").setValue(usernumber);
                        myRef.child(owneruserid).child(usernumber).child("name").setValue(username1);
                        myRef.child(owneruserid).child(usernumber).child("id").setValue(owneruserid);
                        myRef.child(owneruserid).child(usernumber).child("ownermobilenumber").setValue(ownermobilenumber);

                    }
                    else {

                    }


                     uploadId = mDatabaseRef.push().getKey();
                     //Toast.makeText(getApplicationContext(),textleftrightList.size()+" lenght" +chatname+chatnumber+chatowner,Toast.LENGTH_LONG).show();


                        if (number.equals(ownermobilenumber)||number.equals(chatowner))
                       {

                        f=1;
                        i=1;
                        mDatabaseRef.child(chatowner).child(itemid).child(chatnumber).child(uploadId).child("text").setValue(text);
                        mDatabaseRef.child(chatowner).child(itemid).child(chatnumber).child(uploadId).child("id1").setValue(i);
                        mDatabaseRef.child(chatowner).child(itemid).child(chatnumber).child(uploadId).child("chatsize").setValue(textleftrightList.size());

                          show();



                    }else {

                            i=2;
                        mDatabaseRef.child(ownermobilenumber).child(owneruserid).child(usernumber).child(uploadId).child("text").setValue(text);
                        mDatabaseRef.child(ownermobilenumber).child(owneruserid).child(usernumber).child(uploadId).child("id1").setValue(i);
                        mDatabaseRef.child(ownermobilenumber).child(owneruserid).child(usernumber).child(uploadId).child("chatsize").setValue(textleftrightList.size());
                        show1();

                        }


                }

            }
        });





    }

    ArrayList<Userchatdata> userchatdataarray=new ArrayList<Userchatdata>();

    void show()
    {

        mDatabaseRef.child(chatowner).child(itemid).child(chatnumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userchatdataarray.clear();
                textleftrightList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Textleftright textleftright=postSnapshot.getValue(Textleftright.class);
                     Userchatdata userchatdata=postSnapshot.getValue(Userchatdata.class);

                     userchatdataarray.add(userchatdata);


                }

                Collections.sort(userchatdataarray);
                showchat();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }


    void show1()
    {

        mDatabaseRef.child(ownermobilenumber).child(owneruserid).child(usernumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userchatdataarray.clear();
                textleftrightList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Textleftright textleftright=postSnapshot.getValue(Textleftright.class);
                    Userchatdata userchatdata=postSnapshot.getValue(Userchatdata.class);

                    userchatdataarray.add(userchatdata);


                }

                Collections.sort(userchatdataarray);
                showchat();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }



    void showchat()
    {





          for(Userchatdata userchatdata:userchatdataarray){

              Textleftright textleftright=new Textleftright(userchatdata.getText(),userchatdata.getId1(),userchatdata.getChatsize());

            textleftrightList.add(textleftright);


        }


        recyclerAdapterChat = new RecyclerAdapterChat(textleftrightList,this);
        recycle123.setAdapter(recyclerAdapterChat);
        recycle123.smoothScrollToPosition(recycle123.getAdapter().getItemCount());




    }



    void username()
    {
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                username1 = (String) dataSnapshot.child(usernumber).child("name").getValue();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
