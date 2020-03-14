package sr.unasat.blogger.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerService extends Service {




    public TimerService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
}
