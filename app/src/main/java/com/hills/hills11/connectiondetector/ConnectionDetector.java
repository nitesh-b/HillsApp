package com.hills.hills11.connectiondetector;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

public class ConnectionDetector {

    private Context context;
    private boolean status;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService ( Service.CONNECTIVITY_SERVICE );
        if ( connectivityManager != null ) {
            Network net = connectivityManager.getActiveNetwork ( );
            if ( net != null ) {
                status = true;
            } else status = false;


        }
        return status;
    }
}
