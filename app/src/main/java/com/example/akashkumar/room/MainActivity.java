package com.example.akashkumar.room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


//implement the interface OnNavigationItemSelectedListener in your activity class
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    int a=1,b=1,c=1,d=1,e=1;
    ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar=getSupportActionBar();
        bar.hide();

       updateMyLocation();


        loadFragment(new Home());


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                if(a==1)
                {
                    fragment = new Home();
                    b=1;
                    c=1;
                    d=1;
                    e=1;
                    a=0;
                }


                break;

            case R.id.chat:


                if(b==1)
                {
                    fragment = new Chat();
                    a=1;
                    c=1;
                    d=1;
                    e=1;
                   b=0;
                }
                break;

            case R.id.rent:


                if(c==1)
                {
                    fragment = new Rent();
                    a=1;
                    b=1;
                    d=1;
                    e=1;
                    c=0;
                }
                break;


            case R.id.myad:



                if(d==1)
                {
                    fragment = new MyAd();
                    a=1;
                    b=1;
                    d=0;
                    e=1;
                    c=1;
                }
                break;


            case R.id.profile:

                if(e==1)
                {
                    fragment = new Profile();

                    a=1;
                    b=1;
                    d=1;
                    e=0;
                    c=1;
                }
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private void updateMyLocation() {



        // Enable the location layer. Request the location permission if needed.
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);
        } else {
            // Uncheck the box until the layer has been enabled and request missing permission.

            PermissionUtils.requestPermission(MainActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //mMap.setMyLocationEnabled(true);

        }
    }
}