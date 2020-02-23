package sr.unasat.blogger;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class AccountFragment extends Fragment {

    Toolbar toolbar;
    Button updateBtn, deleteBtn;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateBtn = view.findViewById(R.id.updateBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdate();
            };
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void goToUpdate() {
        Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
        startActivity(intent);
    }


    private void deleteAccount(){
        showDeleteUserMessage();
    }


    private void showDeleteUserMessage(){
        new MaterialAlertDialogBuilder(getActivity())
                .setView(R.layout.delete_dialog)
                .setTitle("We gaan u missen...")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}
