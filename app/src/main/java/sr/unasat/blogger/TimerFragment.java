
package sr.unasat.blogger;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import sr.unasat.blogger.services.TimerService;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Time;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import android.content.pm.PackageManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class TimerFragment extends Fragment {

    TextInputEditText customTime;
    TextInputLayout customTimeLayout;
    TextView timerClock;
    Button timerStart, timerStop;
    Toolbar toolbar;


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

        customTime = view.findViewById(R.id.customTime);
        customTimeLayout = view.findViewById(R.id.customTimeLayout);
        timerClock = view.findViewById(R.id.timerClock);

        TextView fragmentTitle = getActivity().findViewById(R.id.fragmentTitle);
        fragmentTitle.setText(getResources().getString(R.string.nav_title_timer));

        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.textColorDark));


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onReceive(Context context, Intent intent) {
                Integer integerTime = intent.getIntExtra("TimeRemaining", 0);
                timerClock.setText(parseTime(integerTime));

                timerStart.setVisibility(View.GONE);
                timerStop.setVisibility(View.VISIBLE);
                customTime.setVisibility(View.GONE);
                customTimeLayout.setVisibility(View.GONE);


            }
        };
        getActivity().registerReceiver(broadcastReceiver, intentFilter);



        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getActivity(), TimerService.class);

                String customTimeValue = customTime.getText().toString();
                int integerTimeSet = 1500;

                if (!customTimeValue.equals("") && !customTimeValue.equals("0")){
                    integerTimeSet = Integer.parseInt(customTimeValue);
                    integerTimeSet *= 60;
                }




                intentService.putExtra("TimeValue", integerTimeSet);
                getActivity().startService(intentService);

                timerStart.setVisibility(View.GONE);
                timerStop.setVisibility(View.VISIBLE);
                customTime.setVisibility(View.GONE);
                customTimeLayout.setVisibility(View.GONE);
            }
        });

        timerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getActivity(), TimerService.class);
                getActivity().stopService(intentService);

                timerStart.setVisibility(View.VISIBLE);
                timerStop.setVisibility(View.GONE);
                customTime.setVisibility(View.VISIBLE);
                customTimeLayout.setVisibility(View.VISIBLE);

            }
        });



        return view;
    }

    @SuppressLint("DefaultLocale")
    private String parseTime(Integer integerTime) {


        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(integerTime *1000),
                TimeUnit.MILLISECONDS.toSeconds(integerTime *1000) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(integerTime *1000))
        );
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}