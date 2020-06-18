package com.hills.hills11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.messaging.FirebaseMessagingService;

import static com.android.volley.VolleyLog.TAG;
import static com.hills.hills11.notification.Notificationchannel.NOTIFICATION_CHANNEL_ID;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_splash_screen );
        Intent intent = getIntent ();
        Bundle extras = intent.getExtras ();
       // System.out.println ("deepcopy: " + intent.getExtras ().getBundle ( "data" ));
        if ((extras != null)) {
            if (extras.containsKey ( "url" )){
                System.out.println (extras.get ( "url" ) );
                System.out.println ("URL detected" );
                startActivity (new Intent(Intent.ACTION_VIEW, Uri.parse( (String) extras.get ( "url" ) )));
            } else if(extras.getInt ( "profile" ) == 0){

            }

            else{
                System.out.println ("EXTRA: " + extras );
                System.out.println ("URL not detected" );
                startActivity ( new Intent ( this , LatestUpdatesActivity.class ) );
            }

        }else{
            System.out.println ("Starting Main Branch" );
            startActivity ( new Intent ( this , MainActivity.class ) );
            finish ( );
        }

    }



}
