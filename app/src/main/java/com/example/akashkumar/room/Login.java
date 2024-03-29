package com.example.akashkumar.room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ActionBar bar;
    TextInputLayout tilname,tilphone;
    Button butcontinue;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onStart() {
        super.onStart();
        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }else {
            if (user != null) {
                Intent intent = new Intent(Login.this, MainActivity
                        .class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        tilname=findViewById(R.id.tilname);
        tilphone=findViewById(R.id.tilmobile);
        butcontinue=findViewById(R.id.butcontinue);



        butcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tilname.getEditText().getText().toString().trim().equals("")) {
                    if(!tilphone.getEditText().getText().toString().trim().equals("")){
                        if (tilphone.getEditText().getText().toString().length()==10){

                            Intent intent=new Intent(Login.this,Otp.class);
                            intent.putExtra("name",tilname.getEditText().getText().toString().trim());
                            intent.putExtra("mobno",tilphone.getEditText().getText().toString().trim());
                            startActivity(intent);

                        }else {
                            tilphone.setError("Invalid Phone number");
                        }
                    }else {
                        tilphone.setError("Can't be empty");
                    }
                }else{
                    tilname.setError("Can't be empty");
                }
            }
        });


    }



}
