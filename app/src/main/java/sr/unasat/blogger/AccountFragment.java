package sr.unasat.blogger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AccountFragment extends Fragment {

    Button updateBtn, deleteBtn;
    TextInputEditText dialogPassword;
    TextInputLayout dialogPasswordLayout;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
//    TODO: Fill fields with actual user data


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
                showDeleteUserMessage();
            }
        });
    }

    private void goToUpdate() {
        Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }




    private void deleteAccount(String password){

//        TODO: Password vergelijken en duidelijke errors geven
        goToLogin();
    }


    private void showDeleteUserMessage(){

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dialog,null);
        dialogPassword = view.findViewById(R.id.dialogPassword);
        dialogPasswordLayout = view.findViewById(R.id.dialogPasswordLayout);
        final AlertDialog builder = new MaterialAlertDialogBuilder(getActivity())
                .setTitle("We gaan u missen...")
                .setView(view)
                .setPositiveButton("Ja", null)
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

        Button positiveBtn = builder.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setTextColor(getResources().getColor(R.color.colorError));
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogPassword.getText().toString().length() == 0){
                    dialogPasswordLayout.setErrorEnabled(true);
                    dialogPasswordLayout.setError("Vul aub uw wachtwoord in");
                }else{
                    dialogPasswordLayout.setErrorEnabled(false);
                    deleteAccount(dialogPassword.getText().toString());
                }
            }
        });







    }

}
