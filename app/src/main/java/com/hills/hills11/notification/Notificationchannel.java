package com.hills.hills11.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.android.volley.VolleyLog.TAG;

public class Notificationchannel {
    public static final String NOTIFICATION_CHANNEL_ID = "simple";
    private Context mContext;

    public Notificationchannel(Context mContext) {
        this.mContext = mContext;
    }

    public void createNotificationChannel() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            NotificationChannel channel = new NotificationChannel ( NOTIFICATION_CHANNEL_ID , "Notification" , NotificationManager.IMPORTANCE_HIGH );
            channel.setDescription ( "CloudMessaging" );
            channel.enableLights ( true );
            channel.setLightColor ( Color.BLUE );
            NotificationManager notificationManager = getSystemService ( mContext , NotificationManager.class );
            assert notificationManager != null;
            notificationManager.createNotificationChannel ( channel );
        }


    }
}
