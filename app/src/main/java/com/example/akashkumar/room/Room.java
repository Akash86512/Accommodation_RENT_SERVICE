package com.example.akashkumar.room;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;



public class Room extends Fragment implements GoogleApiClient.OnConnectionFailedListener,AdapterView.OnItemSelectedListener {
    ActionBar bar;

    String[] roomtype = { "Single", "Double", "Triple", "Quad", "Queen"};
    String[] floorno = { "Ground","1", "2", "3", "4","5", "6", "8", "9","10"};



    EditText fulladdress;

    EditText OwnerName;
    CheckBox all,family,men,women;
    RadioGroup Bachelors,Bathroomes;
    RadioButton yesBt,nobt,sharingbath,separatebath;
    EditText lenghtDime,wightDime,TotalPrice,Securitymoney,Description;
    String roomtyprrent,floornorent,allrent,familyrent,menrent,ownwerent,ownername,ownerlocation,womenrent,yesrent,norent,sharingrent,separatebathrent,lenghtrent,wightrent,totalpricerent,securitymoneyrent,desriptionrent;


    String fulladressrent1,yes="",bathroom="";
    String RentoutTo;
    String rentall,rentfamily,rentmen,rentwomen;

    Button contact;
    String place1,address11;
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef,mDatabaseRef1;
    DatabaseReference myRef ;
    List<FireModal> list;
    RecyclerView recycle;
    private static final int PICK_IMAGE_REQUEST = 234;
    RecyclerAdapter recyclerAdapter;


    private static final int GOOGLE_API_CLIENT_ID = 0;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;

    FirebaseUser user;
    FirebaseAuth auth;
    //view objects
    private Button buttonChoose;
    private Button buttonUpload;
    private EditText editTextName;
    private TextView textViewShow;
    private ImageView imageView;


    //uri to store file
    private Uri filePath;
    int k=0;
String uploadId,latitude1,longitude1;
    //firebase objects
    private StorageReference storageReference;

    int f;
    private AutoCompleteTextView mAutocompleteView;
    int i;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(28.6388999, 77.2223797), new LatLng(28.6548425, 77.1572671));
    public static final String MyPREFERENCES = "MyPrefs" ;
String number;
    public Room() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_room,container,false); {


            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();

            auth= FirebaseAuth.getInstance();
            user=auth.getCurrentUser();
            number=user.getPhoneNumber();

            database = FirebaseDatabase.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserCoordinate");
            mDatabaseRef1 = FirebaseDatabase.getInstance().getReference("userid").child(number);

            location();
            Spinner spin = (Spinner)view.findViewById(R.id.roomtype);
            spin.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,roomtype);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spin.setAdapter(aa);

            Spinner floor = (Spinner)view.findViewById(R.id.floorno);
           floor.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter aa1 = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,floorno);
            aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            floor.setAdapter(aa1);


            fulladdress=view.findViewById(R.id.fulladdress);


            mAutocompleteView = (AutoCompleteTextView)
                    view.findViewById(R.id.autocomplete_places);
            mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
            mAdapter = new PlaceAutocompleteAdapter(getContext(), android.R.layout.simple_list_item_1,
                    mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
            mAutocompleteView.setAdapter(mAdapter);
            // Inflate the layout for this fragment

            buttonChoose = (Button) view.findViewById(R.id.buttonChoose);
            buttonUpload = (Button) view.findViewById(R.id.buttonUpload);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            OwnerName=view.findViewById(R.id.ownername);
            all=view.findViewById(R.id.all);
            family=view.findViewById(R.id.family);
            men=view.findViewById(R.id.men);
            women=view.findViewById(R.id.women);

            Bachelors=view.findViewById(R.id.bacholer);
            Bathroomes=view.findViewById(R.id.bathroom);
            yesBt=view.findViewById(R.id.yesbutton);
            nobt=view.findViewById(R.id.nobutton);
            sharingbath=view.findViewById(R.id.sharingbathroom);

            separatebath=view.findViewById(R.id.sapratebathroom);

            lenghtDime=view.findViewById(R.id.dimentionlength);
            wightDime=view.findViewById(R.id.dimenstionwitgh);
            TotalPrice=view.findViewById(R.id.totalprice);
            Securitymoney=view.findViewById(R.id.securitymoney);
            Description=view.findViewById(R.id.description);

            storageReference = FirebaseStorage.getInstance().getReference();
           // mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

            buttonChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showFileChooser();
                }
            });

            buttonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 //   CheakString();

                    lenghtrent=lenghtDime.getText().toString();
                    wightrent=wightDime.getText().toString();
                    ownwerent=OwnerName.getText().toString();
                    totalpricerent=TotalPrice.getText().toString();
                    securitymoneyrent=Securitymoney.getText().toString();
                    fulladressrent1  =fulladdress.getText().toString();
                    desriptionrent=Description.getText().toString();
                    ownerlocation=mAutocompleteView.getText().toString();
                  //  ownername=OwnerName.getText().toString();

                    CheakString();
                    if(ownerlocation.isEmpty()||ownwerent.isEmpty()||roomtyprrent.isEmpty()||RentoutTo.equals("")||yes.equals("")||bathroom.equals("")||floornorent.isEmpty()||lenghtrent.isEmpty()||wightrent.isEmpty()||totalpricerent.isEmpty()||securitymoneyrent.isEmpty()||fulladressrent1.isEmpty()||desriptionrent.isEmpty())
                    {

                        CheakString();

                         //RentoutTo="";
                        Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();

                    }
                    else {


                        if(k==1)
                        {
                             uploadFile();
                            Toast.makeText(getActivity(),"uploading Please Wait...",Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getActivity(),"Select Photo",Toast.LENGTH_LONG).show();
                        }



                    }



                }
            });




            return view;

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        roomtyprrent=roomtype[position];
        floornorent=floorno[position];

      //  Toast.makeText(getActivity(),roomtype[position]+"  "+ , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            //  Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };

    double lat,log;

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
             latitude1 = String.format("%s", places.get(0).getLatLng().latitude);
            longitude1 = String.format("%s", places.get(0).getLatLng().longitude);

             place1 = String.format("%s", places.get(0).getName());
             address11 = String.format("%s", places.get(0).getAddress());


                zip();


            //Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    Bitmap bitmap;

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                k=1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String diamention1;


    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        //checking if file is available

        if (filePath != null) {
            //displaying progress dialog while image is uploading

            final SpotsDialog   spotsDialog = new SpotsDialog(getActivity());
            spotsDialog.show();
            spotsDialog.setMessage("Uploading");

            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            sRef.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    Uri downloadUrl = uriTask.getResult();

                        uploadId = mDatabaseRef.push().getKey();

                        diamention1 = lenghtrent + "*" + wightrent;
                        UploadData uploadData = new UploadData(uploadId, latitude1, longitude1, downloadUrl.toString(), ownerlocation, ownwerent, roomtyprrent, RentoutTo, yes, bathroom, floornorent, diamention1, totalpricerent, securitymoneyrent, fulladressrent1, desriptionrent, number);
                        mDatabaseRef.child(uploadId).setValue(uploadData);

                        // mDatabaseRef1.child(number).child(uploadId).child(number).setValue(number);
                        UserId userId = new UserId(uploadId);

                        mDatabaseRef1.child(uploadId).setValue(userId);

                    spotsDialog.dismiss();

                    Toast.makeText(getActivity(),"file upload",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    spotsDialog.dismiss();
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            spotsDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

        } else {

            Toast.makeText(getActivity()," file not upload",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    String placeName1,placeAttributuion1;
    void location() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
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
                placeName1 = String.format("%s", likelyPlaces.get(0).getPlace().getName());
                placeAttributuion1 = String.format("%s", likelyPlaces.get(0).getPlace().getAddress());

                latitude1 = String.format("%s", likelyPlaces.get(0).getPlace().getLatLng().latitude);
                longitude1 = String.format("%s", likelyPlaces.get(0).getPlace().getLatLng().longitude);

                mAutocompleteView.setText(placeAttributuion1);
                zip();
                likelyPlaces.release();
            }
        });


    }


    Address address1=null;
    String addr="";
    String zipcode="";
    String city="";
    String state="";
    void  zip()
    {
   Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude1), Double.parseDouble(longitude1), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0){

            addr=addresses.get(0).getAddressLine(0);
            city=addresses.get(0).getLocality();
            state=addresses.get(0).getAdminArea();

            for(int i=0 ;i<addresses.size();i++){
                address1 = addresses.get(i);
                if(address1.getPostalCode()!=null){
                    zipcode=address1.getPostalCode();


                    break;
                }

            }

            fulladdress.setText(addr);


        }



    }
    @Override
    public void onStart() {
        super.onStart();


        mGoogleApiClient.connect();
    }


    void CheakString()
    {


       if(mAutocompleteView.getText().toString().isEmpty())
       {
            mAutocompleteView.setError("enter locaion");
        }
       if(fulladdress.getText().toString().isEmpty())
        {
            fulladdress.setError("enter full address");

        }



        if(OwnerName.getText().toString().isEmpty())
        {
            OwnerName.setError("enter name");
        }
        if(lenghtDime.getText().toString().isEmpty())
        {
            lenghtDime.setError("enter Lenght");

        }
        if(wightDime.getText().toString().isEmpty())
        {
            wightDime.setError("enter widht");
        }
        if(TotalPrice.getText().toString().isEmpty())
        {
            TotalPrice.setError("enter Total Price");
        }
        if(Securitymoney.getText().toString().isEmpty())
        {
            Securitymoney.setError("enter Security Money");
        }
        if(Description.getText().toString().isEmpty())
        {
            Description.setError("enter Description");
        }



        if(Bachelors.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getActivity(),"select Bachelors allowed",Toast.LENGTH_LONG).show();
        }else {


            if(yesBt.isChecked())
            {
                yes="Yes";

            }
            if(nobt.isChecked())
            {
                yes="No";
            }

        }
        if(Bathroomes.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getActivity(),"select Bathroomes",Toast.LENGTH_LONG).show();
        }else {

            if(sharingbath.isChecked())
            {
                bathroom="sharing";

            }
            if(separatebath.isChecked())
            {
                bathroom="Separate";
            }



        }


        if(all.isChecked()||family.isChecked()||men.isChecked()||women.isChecked())
        {

            if(all.isChecked())
            {
                rentall="all ";

            }else {
                rentall="";
            }
            if(family.isChecked())
            {
                rentfamily="Family ";

            }else {
                rentfamily="";
            }
            if(men.isChecked())
            {
                rentmen="Men ";

            }else {
                rentmen="";

            }
            if(women.isChecked())
            {
                rentwomen="Women ";

            }else {

                rentwomen="";
            }


            RentoutTo=rentall+rentfamily+rentmen+rentwomen;


        }
        else {
            Toast.makeText(getActivity(),"select rent out to",Toast.LENGTH_LONG).show();

            RentoutTo="";


        }

    }




}