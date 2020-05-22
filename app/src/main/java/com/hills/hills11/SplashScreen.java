package com.hills.hills11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
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
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d(TAG, "Extras received at onCreate:  Key: " + key + " Value: " + value);
            }
            Log.d ( TAG , "onCreate: Message: " + extras.getString ( "body" ) );
        }


        startActivity ( new Intent ( this , MainActivity.class ) );
        finish ( );


    }



}
