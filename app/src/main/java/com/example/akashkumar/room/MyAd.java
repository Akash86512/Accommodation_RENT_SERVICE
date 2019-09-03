package com.example.akashkumar.room;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Belal on 1/23/2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class MyAd extends Fragment {

    ArrayList<UserId> item=new ArrayList<UserId>();
    ArrayList<UploadData> uersdata=new ArrayList<UploadData>();
    List<UserItemDetails> userItemDetails=new ArrayList<UserItemDetails>();
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    RecyclerView recycle1;
    RecyclerAdapterUserId recyclerAdapterUserId1;
     DatabaseReference mDatabaseRef,mDatabaseRef1;
    public MyAd() {
        // Required empty public constructor
    }
    String s1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_ad,container,false); {


            auth= FirebaseAuth.getInstance();
            user=auth.getCurrentUser();
            s1=user.getPhoneNumber();
            database = FirebaseDatabase.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserCoordinate");
            mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("userid").child(s1);


            calculate();

            recycle1 = (RecyclerView) view.findViewById(R.id.recycle1);


            //You set the layout manage, default item animator and dividers for recyclerview here, you were doing it in on click.
            RecyclerView.LayoutManager recyce = new GridLayoutManager(getActivity(),1);
            /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
            // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recycle1.setLayoutManager(recyce);
            recycle1.setItemAnimator( new DefaultItemAnimator());
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("loading");
            progressDialog.show();



            // Inflate the layout for this fragment
            return view;

        }

    }

    void userid()
    {

        mDatabaseRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    UserId userId=postSnapshot.getValue(UserId.class);

                    item.add(userId);

                }

                editdata();




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    String id1;
    String value;
    void editdata()
    {

        for(UserId userId:item){

            id1=userId.getUserid();
            for (UploadData uploadData:uersdata)
            {
                id1=userId.getUserid();
                if(id1.equals(uploadData.getUserid()))
                {
                    UserItemDetails userItemDetails1=new UserItemDetails(uploadData.getImageurl(),uploadData.getOwnername(),uploadData.getTotalprice(),uploadData.getRentdesription(),userId);

                    userItemDetails.add(userItemDetails1);
                }




            }

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    value = (String) dataSnapshot.child(id1).child("ownername").getValue();
                    Toast.makeText(getActivity(),value+"   user",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });



        }





        show123();





    }


    void show123()
    {
        progressDialog.dismiss();
              recyclerAdapterUserId1 = new RecyclerAdapterUserId(userItemDetails,getActivity());
                recycle1.setAdapter(recyclerAdapterUserId1);

    }




    void calculate()
    {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    UploadData uploadData=postSnapshot.getValue(UploadData.class);

                    uersdata.add(uploadData);

                }








                userid();

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }

}