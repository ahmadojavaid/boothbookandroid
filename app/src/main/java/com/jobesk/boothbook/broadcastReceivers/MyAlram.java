package com.jobesk.boothbook.broadcastReceivers;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.activities.SplashActivity;
import com.jobesk.boothbook.models.LocationReceiveModel;
import com.jobesk.boothbook.utils.ApiClient;
import com.jobesk.boothbook.utils.ApiInterface;
import com.jobesk.boothbook.utils.GlobalClass;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAlram extends BroadcastReceiver {

    private Context context;
    private LocationManager mLocationManager;
    private  String bookingID;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        Log.d("MyAlarmBelal", "Alarm just fired");

        String message = intent.getStringExtra("status");
         bookingID = intent.getStringExtra("bookingID");
        Log.d("messageHere", message);


        if (message.equals("start")) {
            sendNotification(context, "Event Started!");
            apiCallToUpdateLocation();
        } else if (message.equalsIgnoreCase("end")) {
            sendNotification(context, message);
            apiCallToUpdateLocation();
        } else if (message.equalsIgnoreCase("half")) {
            apiCallToUpdateLocation();

        }


    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void sendNotification(Context context, String messageBody) {


        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getResources().getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(55 /* ID of notification */, notificationBuilder.build());
    }


    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


    private void apiCallToUpdateLocation() {

        double lati;
        double longi;
        try {
            Location myLocation = getLastKnownLocation();
            lati = myLocation.getLatitude();
            longi = myLocation.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }


        String key = GlobalClass.getPref("user_client_key", Objects.requireNonNull(context));
        String secret = GlobalClass.getPref("user_client_secret", context);
        String base_url = GlobalClass.getPref("user_base_url", context);

        long time = System.currentTimeMillis()/1000;
        String userID = GlobalClass.getPref("userID", context);

        ApiInterface apiInterface = ApiClient.getApiClientWithoutUrl(base_url).create(ApiInterface.class);
        Call<LocationReceiveModel> eventCall = apiInterface.uploadUserLatLong(key, secret, lati, longi, time, userID);


        eventCall.enqueue(new Callback<LocationReceiveModel>() {
            @Override
            public void onResponse(Call<LocationReceiveModel> call, Response<LocationReceiveModel> response) {
                String urlHit = call.request().url().toString();

                Log.d("ResponseRetro", "response 33: " + new Gson().toJson(response.body()));
                Log.d("urlHit", urlHit);

                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<LocationReceiveModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


}