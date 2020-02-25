package sr.unasat.blogger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FeedFragment extends Fragment {
    private Toolbar toolbar;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(getResources().getColor(R.color.textColorDark));

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

//    TODO: Request to REST Service

//    TODO: Setup Adapter for recyclerview with data from REST call
}
