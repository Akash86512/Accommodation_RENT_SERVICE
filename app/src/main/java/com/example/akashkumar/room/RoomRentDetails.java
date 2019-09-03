package com.example.akashkumar.room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RoomRentDetails extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;

    ViewFlipper viewFlipper;
    Button call,chat;

    FirebaseUser user;
    FirebaseAuth auth;

    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef,mDatabaseRef1;
    String userid,mobilenumber, latitude, longitude,  imageurl,ownerlocation,ownername,roomtype,bentoutTo,bacheloer,bathroom,floornumber,
            diamention,totalprice,securitymoney,fulladress,rentdesription;
    private int currImage = 0;
    TextView Ownername1,Totalprice1,Securitymoney1,Roomtype1,RentoutTo1,Bacheloer1,Bathroom1,Floornumber1,Diamention1,Fulladress1,QonerDescription1;
    ImageView imageView;
    ActionBar bar;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_rent_details);
        bar=getSupportActionBar();
        bar.hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }




        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        number=user.getPhoneNumber();






        final Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        mobilenumber=intent.getStringExtra("mobilenumber");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        imageurl = intent.getStringExtra("url");
        ownerlocation = intent.getStringExtra("ownerlocation");
        ownername = intent.getStringExtra("ownername");
        roomtype = intent.getStringExtra("roomtype");
        bentoutTo = intent.getStringExtra("bentoutTo");
        bacheloer = intent.getStringExtra("bacheloer");
        bathroom = intent.getStringExtra("bathroom");
        floornumber = intent.getStringExtra("floornumber");
        diamention = intent.getStringExtra("diamention");
        totalprice = intent.getStringExtra("totalprice");
        fulladress = intent.getStringExtra("fulladress");
        securitymoney = intent.getStringExtra("securitymoney");
        rentdesription = intent.getStringExtra("rentdesription");


        imageView=findViewById(R.id.image);
        Ownername1=findViewById(R.id.ownername1);
        Totalprice1=findViewById(R.id.totalprice1);
        Securitymoney1=findViewById(R.id.securitymoney1);
        Roomtype1=findViewById(R.id.roomtype12);
        RentoutTo1=findViewById(R.id.rentout);
        Bacheloer1=findViewById(R.id.bacholer1);
        Bathroom1=findViewById(R.id.bathhroom1);
        Floornumber1=findViewById(R.id.floorno1);

        Diamention1=findViewById(R.id.roomdiamenntion);
        Fulladress1=findViewById(R.id.fulladdress1);
        QonerDescription1=findViewById(R.id.description1);

        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mobilenumber));
                startActivity(intent);
            }
        });

        chat=findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(number.equals(mobilenumber))
                {
                    Toast.makeText(getApplicationContext(),"do not chat with your product",Toast.LENGTH_LONG).show();
                }
                else {


                    TrackchatUser();
                    Intent intent1 = new Intent(getApplicationContext(), Userchat.class);

                    intent1.putExtra("userid", userid);
                    intent1.putExtra("number", number);
                    intent1.putExtra("mobilenumber", mobilenumber);
                    intent1.putExtra("ownername", ownername);
                    intent1.putExtra("imageurl", imageurl);


                    startActivity(intent1);
                }
            }
        });


        show();






    }




    void show()
    {



        Glide.with(this).load(imageurl).into(imageView);
        Ownername1.setText(ownername);
        Totalprice1.setText(totalprice);
        Securitymoney1.setText(securitymoney);
        Roomtype1.setText(roomtype);
        RentoutTo1.setText(bentoutTo);
        Bacheloer1.setText(bacheloer);
        Bathroom1.setText(bathroom);
        Floornumber1.setText(floornumber);

        Diamention1.setText(diamention);
        Fulladress1.setText(fulladress);
        QonerDescription1.setText(rentdesription);
    }


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }







    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        LatLng loc = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(loc).title(ownerlocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void  TrackchatUser()
    {


    }


}
