package sr.unasat.blogger;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AccountFragment extends Fragment {

    Toolbar toolbar;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.parseColor("#fece2f"));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);



    }


}
