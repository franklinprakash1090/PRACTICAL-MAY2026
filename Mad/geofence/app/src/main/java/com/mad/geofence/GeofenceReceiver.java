package com.mad.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event.hasError()) return;

        int transition = event.getGeofenceTransition();

        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(context, "Entered Geofence", Toast.LENGTH_LONG).show();
        } else if (transition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Toast.makeText(context, "Exited Geofence", Toast.LENGTH_LONG).show();
        }
    }
}
