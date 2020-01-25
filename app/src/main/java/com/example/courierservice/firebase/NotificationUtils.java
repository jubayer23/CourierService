package com.example.courierservice.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.example.courierservice.HomeActivity;
import com.example.courierservice.LoginActivity;
import com.example.courierservice.R;
import com.example.courierservice.Utility.CommonMethods;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.model.PendingOrder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 200;
    private static final String PUSH_NOTIFICATION = "pushNotification";
    private static final String CHANNEL_ID = "myChannel";
    private static final String CHANNEL_NAME = "myChannelName";
    private static final String URL = "url";
    private static final String ACTIVITY = "activity";
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        //Populate activity map
    }

    /**
     * Displays notification based on parameters
     *
     * @param pendingOrder
     */
    public void displayNotification(PendingOrder pendingOrder) {

        String message = "Pickup Address:m" + pendingOrder.getPickUpAddress() + "\nDropOff Address:" + pendingOrder.getDropOffAddress();
        String title = "A new order assigned to you.";
        String iconUrl = null; //notificationVO.getIconUrl();


        Bitmap iconBitMap = null;
        if (iconUrl != null) {
            iconBitMap = getBitmapFromURL(iconUrl);
        }


        PendingIntent resultPendingIntent;


       // resultIntent = new Intent(mContext, HomeActivity.class);
        Intent resultIntent;
        if(MydApplication.getInstance().getPrefManger().getUser() != null){
             resultIntent = new Intent(mContext, HomeActivity.class);
        }else{
             resultIntent = new Intent(mContext, LoginActivity.class);
        }

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        resultIntent.putExtra(GlobalAppAccess.KEY_ORDER_NOTIFICATION, pendingOrder);


        resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT
                );

    sendNotification(title, message, iconBitMap, resultPendingIntent);


    }

    public void displayNotification(String title, String message){
        PendingIntent resultPendingIntent;


        // resultIntent = new Intent(mContext, HomeActivity.class);
        Intent resultIntent;
        if(MydApplication.getInstance().getPrefManger().getUser() != null){
            resultIntent = new Intent(mContext, HomeActivity.class);
        }else{
            resultIntent = new Intent(mContext, LoginActivity.class);
        }
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        //resultIntent.putExtra(GlobalAppAccess.KEY_ORDER_NOTIFICATION, pendingOrder);


        resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT
                );

        sendNotification(title, message, null, resultPendingIntent);
    }

    private void sendNotification(String title, String message, Bitmap iconBitMap, PendingIntent resultPendingIntent) {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext, CHANNEL_ID);

        final int icon = R.mipmap.ic_launcher;

        Notification notification;

        if (iconBitMap == null) {
            //When Inbox Style is applied, user can expand the notification
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            inboxStyle.addLine(message);
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(inboxStyle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();

        } else {
            //If Bitmap is created from URL, show big icon
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
            bigPictureStyle.bigPicture(iconBitMap);
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setStyle(bigPictureStyle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();
        }

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //All notifications should go through NotificationChannel on Android 26 & above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }
        notificationManager.notify(CommonMethods.getID(), notification);
    }

    /**
     * Downloads push notification image before displaying it in
     * the notification tray
     *
     * @param strURL : URL of the notification Image
     * @return : BitMap representation of notification Image
     */
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            java.net.URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Playing notification sound
     */
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}