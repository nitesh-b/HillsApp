package com.hills.hills11.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hills.hills11.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NotificationUpdates extends MainActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance ( );
    private Context mContext;
    private List<NotificationDetails> notificationDetailsList;

    public NotificationUpdates(Context mContext) {
        this.mContext = mContext;
        retriveNotificationUpdates ( );
    }

    public void retriveNotificationUpdates() {
        // Log.d(TAG, "FCM token: " + FirebaseInstanceId.getInstance().getToken());

        Thread thread = new Thread ( ) {
            @SuppressLint("HardwareIds")
            @Override
            public void run() {
                notificationDetailsList = new ArrayList<> ( );
                db.collection ( "notification" )
                        .document ( "history" )
                        .collection ( Settings.Secure.getString ( mContext.getContentResolver ( ) ,
                                Settings.Secure.ANDROID_ID ) )
                        .get ( ).addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if ( task.isSuccessful ( ) ) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult ( )) {
                                if( documentSnapshot.getData (  ).containsKey ( "url" )) {
                                    NotificationDetails details = new NotificationDetails (
                                            documentSnapshot.getData ( ).get ( "title" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "time" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "description" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "icon" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "url" ).toString ( ) );
                                    Log.d ( TAG , "onComplete: I am seeing this. NOtification Added" );
                                    notificationDetailsList.add ( details );
                                }else{
                                    NotificationDetails details = new NotificationDetails (
                                            documentSnapshot.getData ( ).get ( "title" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "time" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "description" ).toString ( ) ,
                                            documentSnapshot.getData ( ).get ( "icon" ).toString ( )  );
                                    Log.d ( TAG , "onComplete: I am seeing this. NOtification Added" );
                                    notificationDetailsList.add ( details );

                                }
                            }
                        } else
                            Toast.makeText ( mContext , "Data Retrieving Unsuccessful" , Toast.LENGTH_SHORT ).show ( );
                    }
                } );
            }
        };
        thread.start ( );
        try {
            Thread.sleep ( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
    }


    public List<NotificationDetails> getDetails() {
        return notificationDetailsList;
    }

}
