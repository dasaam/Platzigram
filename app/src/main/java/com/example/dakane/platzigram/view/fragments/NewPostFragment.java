package com.example.dakane.platzigram.view.fragments;


import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dakane.platzigram.PlatzigramApplication;
import com.example.dakane.platzigram.R;
import com.example.dakane.platzigram.model.Post;
import com.facebook.appevents.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {
    ImageView ivPicture;
    Button btnPicture;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;

    String mCurrentAbsolutePhotoPath;
    PlatzigramApplication app;
    StorageReference storageReference;
    DatabaseReference postReference;

    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        btnPicture = (Button)view.findViewById(R.id.btnTakePicture);

        app = (PlatzigramApplication) getActivity().getApplicationContext();
        storageReference= app.getStorageReference();
        postReference = app.getPostReference();

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){

            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPicture.setImageBitmap(imageBitmap);*/
            Picasso.with(getActivity()).load(mCurrentPhotoPath).into(ivPicture);
            addPictureToGallery();
            //Toast.makeText(getActivity(), mCurrentPhotoPath, Toast.LENGTH_LONG).show();

            uploadFile();
        }
    }

    private void uploadFile() {
        File newFile = new File(mCurrentAbsolutePhotoPath);
        final Uri contentUri = Uri.fromFile(newFile);

        StorageReference imageReference = storageReference.child(com.example.dakane.platzigram.utils.Constants.FIREBASE_STORAGE_IMAGES
                + contentUri.getLastPathSegment());

        UploadTask uploadTask = imageReference.putFile(contentUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error subiendo la imagen", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_LONG).show();
                String imageUrl = taskSnapshot.getDownloadUrl().toString();
                createNewPost(imageUrl);
            }
        });
      }

    private void createNewPost(String imageUrl) {
        SharedPreferences prefs = getActivity().getSharedPreferences("USER", getActivity().MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String enCodedEmail = email.replace(".",",");

        HashMap<String, Object> timeStampCreated = new HashMap<>();
        timeStampCreated.put("timestamp", ServerValue.TIMESTAMP);

        Post post = new Post(enCodedEmail, imageUrl, timeStampCreated);

        //Toast.makeText(getContext(), imageUrl, Toast.LENGTH_LONG).show();

        postReference.push().setValue(post);

    }


    public void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null ){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.dakane.platzigram",
                        photoFile
                        );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }


        }
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG"+timeStamp+"_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
            imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:"+image.getAbsolutePath();
        mCurrentAbsolutePhotoPath = image.getAbsolutePath();

        return image;
    }

    private void addPictureToGallery(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File newFile = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(newFile);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }



}
