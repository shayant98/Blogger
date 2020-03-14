package sr.unasat.blogger.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

public class TimerService extends Service {

    public TimerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
