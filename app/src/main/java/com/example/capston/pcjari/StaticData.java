package com.example.capston.pcjari;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-11-02.
 */

public class StaticData {
    public static PCListItem pcItems[] = new PCListItem[7];
    public static double GPS_X=0;
    public static double GPS_Y=0;
    public static GPSTracker gps = null;

    public static void gpsGetLocation(Context context) {
        if(gps == null) {
            gps = new GPSTracker(context);
        }else{
            gps.Update();
        }

        // check if GPS enabled
        if(gps.canGetLocation()){
            StaticData.GPS_X = gps.getLatitude();
            StaticData.GPS_Y = gps.getLongitude();
            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + StaticData.GPS_X + "\nLong: " + StaticData.GPS_Y, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}
