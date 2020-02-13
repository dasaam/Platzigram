package com.example.dakane.platzigram;

import android.app.Application;

import com.example.dakane.platzigram.utils.Constants;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by dakane on 05/02/20.
 */

public class PlatzigramApplication  extends Application{
    StorageReference storageReference;
    DatabaseReference postReference;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl(Constants.FIREBASE_STORAGE_URL);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        postReference = firebaseDatabase.getReference(Constants.FIREBASE_DATABASE_LOCATION_POST);
    }

    public StorageReference getStorageReference(){
        return storageReference;
    }

    public DatabaseReference getPostReference(){
        return postReference;
    }
}
