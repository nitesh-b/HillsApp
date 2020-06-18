package com.hills.hills11.notification;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hills.hills11.LatestUpdatesActivity;
import com.hills.hills11.MainActivity;
import com.hills.hills11.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.hills.hills11.notification.Notificationchannel.NOTIFICATION_CHANNEL_ID;


public class NotificationSettings extends FirebaseMessagingService {

    private FirebaseFirestore db;
    private Map<String, String> backup = new HashMap<> ( );
    private Map<String, String>  fromFCM;

    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {
        super.onMessageReceived ( remoteMessage );
        db = FirebaseFirestore.getInstance ( );
        fromFCM = remoteMessage.getData ();
        System.out.println ("The data sent was: " + fromFCM);
        if ( remoteMessage.getNotification ( ) != null ) {
            String title = remoteMessage.getNotification ( ).getTitle ( );
            String body = remoteMessage.getNotification ( ).getBody ( );
            String image = remoteMessage.getNotification ( ).getImageUrl ( ).toString ( );

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "dd-MM-yyyy hh:mm:ss" );
            final String currentTime = simpleDateFormat.format ( Calendar.getInstance ( ).getTimeInMillis ( ) );

            backup.put ( "title" , title );
            backup.put ( "time" , currentTime );
            backup.put ( "description" , body );
            if(!image.isEmpty ())
                backup.put ( "icon" , image );
            if(fromFCM.containsKey ( "url" )){
                backup.put ( "url", fromFCM.get ( "url" ) );
            }
            FirebaseInstanceId.getInstance ().getInstanceId ().addOnCompleteListener ( new OnCompleteListener<InstanceIdResult> ( ) {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful ()){
                            System.out.println ("Token generated: " + task.getResult ().getToken () );
                            saveNotificationToFirebase(task.getResult ().getToken (), currentTime);
                        }
                }
            } );

            showNotification ( getApplicationContext ( ) , title , body , image );

        }
    }

    public void saveNotificationToFirebase(String token, String currentTime){

        db.collection ( "notification" )
                .document ( "history" )
                .collection ( token )
                .document ( currentTime )
                .set ( backup )
                .addOnFailureListener ( new OnFailureListener ( ) {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( NotificationSettings.this , "Could not save your notification" , Toast.LENGTH_SHORT ).show ( );
                    }
                } );
    }

    public void showNotification(Context context , String title , String body , String image) {
        Intent notifyIntent = new Intent ( this , LatestUpdatesActivity.class );
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create ( this );
        taskStackBuilder.addNextIntentWithParentStack ( notifyIntent );


        String[] keySet = fromFCM.keySet ().toArray ( new String[0] );

        Log.d ( TAG , "showNotification: keSet: " + keySet  );
        // private String receivedChannelID;
        PendingIntent pendingIntent;
        if( Arrays.asList ( keySet ).contains ( "url" ) ){
            Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fromFCM.get ( "url" )));
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        }else{
            pendingIntent = taskStackBuilder.getPendingIntent ( 0, PendingIntent.FLAG_UPDATE_CURRENT );

        }
        if ( pendingIntent != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder ( context , NOTIFICATION_CHANNEL_ID );
            builder.setAutoCancel ( true )
                    .setSmallIcon ( R.drawable.hills_logo_black )
                    .setContentTitle ( title )
                    .setContentText ( body )
                    .setContentInfo ( "Info" )
                    .setPriority ( NotificationCompat.PRIORITY_HIGH )
                    .setContentIntent ( pendingIntent );

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from ( this );
            notificationManagerCompat.notify ( 0 , builder.build ( ) );
        }else
            Log.d ( TAG , "showNotification: empty intent: " );
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken ( s );
        // Log.d(TAG, "onNewToken: " + s);
    }


}
