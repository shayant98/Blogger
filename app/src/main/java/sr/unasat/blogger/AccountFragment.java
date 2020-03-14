package sr.unasat.blogger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import sr.unasat.blogger.Entity.User;
import sr.unasat.blogger.database.DatabaseHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class AccountFragment extends Fragment {

    private static final String TAG = "Account";
    private Toolbar toolbar;
    private Button updateBtn, deleteBtn;
    private TextInputEditText dialogPassword, accountEmail, accountAddress, accountPhone;
    private TextInputLayout dialogPasswordLayout;
    private DatabaseHelper databaseHelper;
    private User user;
    private TextView accountName, accountStudNr;

    public AccountFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        databaseHelper = new DatabaseHelper(getContext());

        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateBtn = view.findViewById(R.id.updateBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        accountAddress = view.findViewById(R.id.accountAddress);
        accountEmail = view.findViewById(R.id.accountEmail);
        accountPhone = view.findViewById(R.id.accountPhone);

        accountName = view.findViewById(R.id.AccountName);
        accountStudNr = view.findViewById(R.id.accountStudNr);



        TextView fragmentTitle = getActivity().findViewById(R.id.fragmentTitle);
        fragmentTitle.setText(getResources().getString(R.string.nav_title_account));

        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


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

    @Override
    public void onStart() {
        Intent intent = getActivity().getIntent();
        user = intent.getParcelableExtra("loggedInUser");


        Log.d(TAG, String.valueOf(intent.getExtras()));
        User currentUser = databaseHelper.getUser(user.getId());

        accountStudNr.setText(currentUser.getUsername());
        accountName.setText(String.format("%s %s", currentUser.getFirstName(), currentUser.getName()));
        accountAddress.setText(currentUser.getAdress());
        accountEmail.setText(currentUser.getEmail());
        accountPhone.setText(currentUser.getPhoneNumber());

        super.onStart();
    }

    private void goToUpdate() {
        Intent intent = new Intent(getActivity(), UpdateUserActivity.class);
        startActivityForResult(intent, 1);
    }

    private void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == -1){
                Snackbar.make(getView(), data.getStringExtra("snackbarMessage"),BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }

    private void deleteAccount(String password){
        boolean deleteRequest = databaseHelper.deleteUser(user.getId(),password);
            if (deleteRequest){
                goToLogin();
            }else{
                Toast.makeText(getContext(), "Unable to delete user", Toast.LENGTH_SHORT).show();
            }
    }


    private void showDeleteUserMessage(){

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dialog,null);
        dialogPassword = view.findViewById(R.id.dialogPassword);
        dialogPasswordLayout = view.findViewById(R.id.dialogPasswordLayout);
        final AlertDialog builder = new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
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
                if(Objects.requireNonNull(dialogPassword.getText()).toString().length() == 0){
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
