package com.munjzservice.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.munjzservice.R;
import com.munjzservice.balance.BalanceActivity;
import com.munjzservice.servicerequest.ViewServiceRequestActivity;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.MainActivity;


import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.content.ContentValues.TAG;

/**
 * Created by mac on 1/5/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String serviceRequestId=null;
    String category="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getData());
                if (jsonObject.has("serviceRequestId")){
                    serviceRequestId=jsonObject.getString("serviceRequestId");
                    category=jsonObject.getString("category");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),serviceRequestId,category);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        pushNotificationBadgeCount();

    }

    private void pushNotificationBadgeCount(){
        try {
            int badgeCount = AppSession.getInstance(this).getBadgeCount() + 1;
            AppSession.getInstance(this).saveBadgeCount(badgeCount);
            ShortcutBadger.applyCount(this, badgeCount);
        }catch (Exception e){
        }
    }

    private void sendNotification(String messageBody,String serviceRequestId,String category) {
        Intent intent;
        if (category.trim().toLowerCase().equals("balance")){
            intent = new Intent(this, BalanceActivity.class);

        }else {
            intent = new Intent(this, ViewServiceRequestActivity.class);
        }
        intent.putExtra("serviceRequestId",serviceRequestId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"my_channel_id_01")
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_notification_transprant_launcher);
            notificationBuilder.setColor(getResources().getColor(R.color.notification_bg));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_notification_launcher);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
