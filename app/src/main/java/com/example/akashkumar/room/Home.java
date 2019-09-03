package com.example.akashkumar.room;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class Home extends Fragment implements GoogleApiClient.OnConnectionFailedListener,AdapterView.OnItemSelectedListener{


    ActionBar bar;
    Button contact;
    String details;






     ProgressDialog progressDialog1;

    List<FireModal> list1;
    RecyclerView recycle;
    RecyclerAdapter recyclerAdapter;
    String[] country = { "Filter", "Room", "Flate", "Shop", "Office"};

    private static final int GOOGLE_API_CLIENT_ID = 0;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;


    int f;
    private AutoCompleteTextView mAutocompleteView;
    int i;
    ArrayList<SortDistance> al=new ArrayList<SortDistance>();
    ArrayList<UploadData> data=new ArrayList<UploadData>();

    ArrayList<UserDeatils> datauser=new ArrayList<UserDeatils>();


    List<UserData>userDeatils11 =new ArrayList<UserData>();
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef,mDatabaseRef1;

    String value="4";

    public String getValue() {
        return value;
    }




    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(28.6388999, 77.2223797), new LatLng(28.6548425, 77.1572671));
    public static final String MyPREFERENCES = "MyPrefs" ;


     SpotsDialog spotsDialog;

    public Home() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false); {

            database = FirebaseDatabase.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("UserCoordinate");
            mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("UserLocation");

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();



            Spinner spin = (Spinner)view.findViewById(R.id.spinner);
            spin.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(aa);



       //   updateMyLocation();

            mAutocompleteView = (AutoCompleteTextView)
                    view.findViewById(R.id.autocomplete_places);
            mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
            mAdapter = new PlaceAutocompleteAdapter(getContext(), android.R.layout.simple_list_item_1,
                    mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
            mAutocompleteView.setAdapter(mAdapter);


            recycle = (RecyclerView) view.findViewById(R.id.recycle);


            //You set the layout manage, default item animator and dividers for recyclerview here, you were doing it in on click.
            RecyclerView.LayoutManager recyce = new GridLayoutManager(getActivity(),2);
            /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
            // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recycle.setLayoutManager(recyce);
            recycle.setItemAnimator( new DefaultItemAnimator());








            calculate();

        //  location();




             spotsDialog = new SpotsDialog(getActivity());
            spotsDialog.show();
            spotsDialog.setMessage("Loading");

            // Inflate the layout for this fragment
            return view;

        }

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            //  Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */

            userDeatils11.clear();
            al.clear();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            //  Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };


    String latitude ;
    String longitude;

    String UserId;
    int total;
    String Latitude;
    String Longitude;
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                // Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
             latitude = String.format("%s", places.get(0).getLatLng().latitude);
             longitude = String.format("%s", places.get(0).getLatLng().longitude);

             sortDistance();
             sortData();

           //  userDeatils11.removeAll(userDeatils11);

            al.clear();




           // Toast.makeText(getActivity()," lenght "+String.valueOf(al.size())+"   "+String.valueOf(userDeatils11.size()),Toast.LENGTH_LONG).show();


            //Log.i(TAG, "Place details received: " + place.getName());


            places.release();
        }

    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),country[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    String s2;

    void sortData()
    {


         for(SortDistance sortDistance:al){




                    UserData userData=new UserData(sortDistance);

                    userDeatils11.add(userData);


        }
        spotsDialog.dismiss();


       recyclerAdapter = new RecyclerAdapter(userDeatils11,getActivity());
        recycle.setAdapter(recyclerAdapter);
        //Toast.makeText(getActivity()," User "+s2,Toast.LENGTH_LONG).show();


    }





    String s;

    void calculate()
    {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    UploadData uploadData=postSnapshot.getValue(UploadData.class);

                    data.add(uploadData);

                }

               location();




//                 Toast.makeText(getActivity(),"user Id "+s,Toast.LENGTH_LONG).show();


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }


    String address,url,roomd;

    void sortDistance()
    {

        for (UploadData fetchData:data)
        {

          Latitude=fetchData.getLatitude();
          Longitude=fetchData.getLongitude();

          if(Latitude.equals(latitude)&&Longitude.equals(longitude))
          {
              total=0;
          }
          else {
              int Radius = 6371;
              double SourcePointlatitude = Double.parseDouble(latitude);
              double  DestinationPointlatitude = Double.parseDouble(Latitude);
              double Sourcepointlogtitude = Double.parseDouble(longitude);
              double Destinationlogtitute = Double.parseDouble(Longitude);
              double dLat = Math.toRadians(DestinationPointlatitude - SourcePointlatitude);
              double dLon = Math.toRadians(Destinationlogtitute - Sourcepointlogtitude);
              double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                      + Math.cos(Math.toRadians(SourcePointlatitude))
                      * Math.cos(Math.toRadians(DestinationPointlatitude)) * Math.sin(dLon / 2)
                      * Math.sin(dLon / 2);
              double c = 2 * Math.asin(Math.sqrt(a));
              double valueResult = Radius * c;
              double km = valueResult / 1;
              DecimalFormat newFormat = new DecimalFormat("####");
              int kmInDec = Integer.valueOf(newFormat.format(km));
              double meter = valueResult % 1000;
              int meterInDec = Integer.valueOf(newFormat.format(meter));
              total=(int)km;
          }



            SortDistance sortDistance=new SortDistance(total,fetchData);
            al.add(sortDistance);
        }
        Collections.sort(al);


    }


String placeName1,placeAttributuion1;

    void location() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }


        if (ActivityCompat.checkSelfPermission((getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }



        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {




                if (!likelyPlaces.getStatus().isSuccess()) {
                    // Request did not complete successfully
                    //  Log.e(TAG, "Place query did not complete. Error: " + likelyPlaces.getStatus().toString());
                    likelyPlaces.release();
                    return;
                }


                latitude = String.format("%s", likelyPlaces.get(0).getPlace().getLatLng().latitude);
                longitude = String.format("%s", likelyPlaces.get(0).getPlace().getLatLng().longitude);

                sortDistance();
                sortData();

                placeName1 = String.format("%s", likelyPlaces.get(0).getPlace().getName());
                placeAttributuion1 = String.format("%s", likelyPlaces.get(0).getPlace().getAddress());
                //al.clear();

               // mAutocompleteView.setText(placeAttributuion1);


                Toast.makeText(getActivity()," lenght "+String.valueOf(al.size())+"   "+String.valueOf(userDeatils11.size()),Toast.LENGTH_LONG).show();

                //  userDeatils11.removeAll(userDeatils11);


                likelyPlaces.release();
            }
        });


    }





}
