package sr.unasat.blogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import sr.unasat.blogger.services.TimerService;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class TimerFragment extends Fragment {

    TextView timerClock;
    Button timerStart, timerStop;

    private static final long START_TIME_IN_MILLIS = 10000;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


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
        View view =  inflater.inflate(R.layout.fragment_timer, container, false);

        timerStart = view.findViewById(R.id.timerBtnStart);
        timerStop = view.findViewById(R.id.timerBtnStop);

        timerClock = view.findViewById(R.id.timerClock);

        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (mTimerRunning) {
                        pauseTimer();
                    } else {
                        startTimer();
                    }


            }
        });

        timerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetTimer();
                    getActivity().stopService(new Intent(getActivity(), TimerService.class));
            }
        });

        updateCountDownText();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                getActivity().startService(new Intent(getActivity(), TimerService.class));
                mTimerRunning = false;
                timerStart.setText("Start");
            }
        }.start();

        mTimerRunning = true;
        timerStart.setText("pause");
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        timerStart.setText("Start");
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timerClock.setText(timeLeftFormatted);
    }
}