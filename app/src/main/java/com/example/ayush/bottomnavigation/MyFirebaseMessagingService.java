package com.example.ayush.bottomnavigation;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Context mContext;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Map<String,String> mymap=remoteMessage.getData();
        String url=mymap.get("link");
        Log.d(TAG,"DATA"+remoteMessage.getNotification().toString());
        Log.d(TAG,url);

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody(),url);
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody,String url) {
        mContext=this;
        new generatePictureStyleNotification(this,"TEDxDTU", messageBody, url).execute();

    }

    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl;

        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
        }

        public  float getImageFactor(Resources r){
            DisplayMetrics metrics = r.getDisplayMetrics();
            float multiplier=metrics.density/3f;
            return multiplier;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            float multiplier= getImageFactor(getResources());
            result=Bitmap.createScaledBitmap(result, (int)(result.getWidth()*multiplier), (int)(result.getHeight()*multiplier), false);

            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notif = new Notification.Builder(mContext)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(result)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                    .build();
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1, notif);
        }

    }
}