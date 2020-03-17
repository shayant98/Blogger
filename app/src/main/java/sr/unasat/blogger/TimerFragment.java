
package sr.unasat.blogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import sr.unasat.blogger.services.TimerService;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Locale;
import java.util.Timer;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import android.content.pm.PackageManager;


public class TimerFragment extends Fragment {

    TextView timerClock;
    Button timerStart, timerStop;


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActivityCompat.requestPermissions(getActivity(), new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);




        View view =  inflater.inflate(R.layout.fragment_timer, container, false);

        timerStart = view.findViewById(R.id.timerBtnStart);
        timerStop = view.findViewById(R.id.timerBtnStop);

        timerClock = view.findViewById(R.id.editText);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                timerClock.setText(integerTime.toString());
            }
        };
        getActivity().registerReceiver(broadcastReceiver, intentFilter);



        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getActivity(), TimerService.class);
                Integer integerTimeSet = Integer.parseInt(timerClock.getText().toString());
                intentService.putExtra("TimeValue", integerTimeSet);
                getActivity().startService(intentService);
            }
        });

        timerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getActivity(), TimerService.class);
                getActivity().stopService(intentService);
            }
        });



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}