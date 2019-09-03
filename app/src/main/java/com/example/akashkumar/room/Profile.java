package com.example.akashkumar.room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;


public class Profile extends Fragment {
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference myRef;
    TextView name,phone;
    public Profile() {
        // Required empty public constructor
    }
    String s1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false); {


            name=view.findViewById(R.id.name);
            phone=view.findViewById(R.id.phone);


            auth= FirebaseAuth.getInstance();
            user=auth.getCurrentUser();
            s1=user.getPhoneNumber();

            phone.setText(s1);


       myRef = FirebaseDatabase.getInstance().getReference();

show();


            // Inflate the layout for this fragment
            return view;

        }

    }

    void show(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = (String) dataSnapshot.child("Users").child(s1).child("name").getValue();
                name.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}