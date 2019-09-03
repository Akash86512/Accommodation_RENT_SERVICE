package com.example.akashkumar.room;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.awt.font.TextAttribute;
import java.io.IOException;

public class UpdateUserValue extends AppCompatActivity {

    TextView useriditem;
    EditText owneritem,priceitem,des;
    Button choose,update;
    ImageView imageView1;
    private StorageReference storageReference;
    FirebaseDatabase database;
    private DatabaseReference mDatabaseRef,mDatabaseRef1;
    String id1,price1,des1,owner,url;
    Intent intent;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_value);

        useriditem=findViewById(R.id.uerid1);
        owneritem=findViewById(R.id.ownernameitem);
        priceitem=findViewById(R.id.priceitem);
        des=findViewById(R.id.desitem);
        choose=findViewById(R.id.chooes);
        update=findViewById(R.id.update);
        imageView1=findViewById(R.id.imageitem);

        database = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserCoordinate");
        storageReference = FirebaseStorage.getInstance().getReference();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(k==1)
                {
                    uploadFile();
                    Toast.makeText(getApplicationContext(),"please Wait",Toast.LENGTH_LONG).show();
                }
                else {
                    update();
                    Toast.makeText(getApplicationContext(),"data update",Toast.LENGTH_LONG).show();
                }


            }
        });

show1();

    }

    void show1()
    {
      intent = getIntent();
        id1 = intent.getStringExtra("id");
        price1 = intent.getStringExtra("price");
        des1 = intent.getStringExtra("des");
        owner = intent.getStringExtra("owner");
        url = intent.getStringExtra("url");

        owneritem.setText(owner);
        priceitem.setText(price1);
        des.setText(des1);
        useriditem.setText(id1);

        Glide.with(this).load(url).into(imageView1);
    }


    Bitmap bitmap;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;

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
                k=1;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                imageView1.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    Uri downloadUrl;
    private void uploadFile() {
        //checking if file is available



        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            sRef.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while(!uriTask.isComplete());
                    downloadUrl = uriTask.getResult();
                    url=downloadUrl.toString();
                    update();
                    progressDialog.dismiss();


                    Toast.makeText(getApplicationContext(),"data update",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });


        } else {

            Toast.makeText(getApplicationContext()," data not update",Toast.LENGTH_LONG).show();
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    void update()
    {

        mDatabaseRef.child(id1).child("imageurl").setValue(url);
        mDatabaseRef.child(id1).child("ownername").setValue(owneritem.getText().toString());
        mDatabaseRef.child(id1).child("totalprice").setValue(priceitem.getText().toString());
        mDatabaseRef.child(id1).child("rentdesription").setValue(des.getText().toString());


    }
}
