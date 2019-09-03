package com.example.akashkumar.room;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdChat extends AppCompatActivity {


    RecyclerView recyclechat;
    DatabaseReference myRef;
    String Myadid;
    List<MyAdUserChat> myAdUserChats1=new ArrayList<MyAdUserChat>();

    RecyclerAdapterMyasUserChat recyclerAdapterMyasUserChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad_chat);
        Intent intent=getIntent();
        Myadid=intent.getStringExtra("id");
        recyclechat = (RecyclerView) findViewById(R.id.recyclechat1);
        myRef = FirebaseDatabase.getInstance().getReference().child("UserChatTrack").child(Myadid);




        //You set the layout manage, default item animator and dividers for recyclerview here, you were doing it in on click.
        RecyclerView.LayoutManager recyce = new GridLayoutManager(getApplicationContext(),1);
        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclechat.setLayoutManager(recyce);
        recyclechat.setItemAnimator( new DefaultItemAnimator());
        showuserchat();


    }



    void showuserchat()
    {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    MyAdUserChat myAdUserChat =postSnapshot.getValue(MyAdUserChat.class);
                    myAdUserChats1.add(myAdUserChat);


                }

                recyclerAdapterMyasUserChat = new RecyclerAdapterMyasUserChat(myAdUserChats1,getApplicationContext());
                recyclechat.setAdapter(recyclerAdapterMyasUserChat);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
