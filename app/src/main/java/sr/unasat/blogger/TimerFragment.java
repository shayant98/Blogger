package sr.unasat.blogger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import sr.unasat.blogger.services.TimerService;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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
        View view =  inflater.inflate(R.layout.fragment_timer, container, false);

        timerStart = view.findViewById(R.id.timerBtnStart);
        timerStop = view.findViewById(R.id.timerBtnStop);

        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), TimerService.class));

            }
        });

        timerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), TimerService.class));
            }
        });

        return view;
    }
}
