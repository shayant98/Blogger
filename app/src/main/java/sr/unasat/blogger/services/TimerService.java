package sr.unasat.blogger.services;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import sr.unasat.blogger.R;
import sr.unasat.blogger.TimerFragment;


public class TimerService extends Service {

    private static final String CHANNEL_ID = "NotificationChannelID";
    private MediaPlayer player;
    private Timer timer;
    @Override
    public void onCreate(){
        player = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
        timer = new Timer();
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "sr.unasat.blogger";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.account_icon)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Integer[] timeRemaining = {intent.getIntExtra("TimeValue", 0)};
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Intent intent1local = new Intent();
                intent1local.setAction("Counter");
                timeRemaining[0]--;
                NotificationUpdate(timeRemaining[0]);
                if (timeRemaining[0] <= 0){
                    player.setLooping(true);
                    player.start();
                    timer.cancel();
                }
                intent1local.putExtra("TimeRemaining", timeRemaining[0]);
                sendBroadcast(intent1local);
            }
        }, 0,1000);
        return super.onStartCommand(intent, flags, startId);
    }



    public void NotificationUpdate(Integer timeLeft){
        try {
            Intent notificationIntent = new Intent(this, TimerFragment.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            final Notification[] notification = {new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("timer")
                    .setContentText("Time Remaing : " + parseTime(timeLeft))
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setVibrate(null)
                    .build()};
            startForeground(1, notification[0]);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Counter Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {

        if (player.isPlaying()){
            player.stop();
        }
        timer.cancel();
        stopForeground(true);

    }
    @SuppressLint("DefaultLocale")
    private String parseTime(Integer integerTime) {


        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(integerTime *1000),
                TimeUnit.MILLISECONDS.toSeconds(integerTime *1000) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(integerTime *1000))
        );
    }


}