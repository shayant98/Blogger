package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import sr.unasat.blogger.Entity.User;
import sr.unasat.blogger.database.DatabaseHelper;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button forgetPassword, logIn, register;
    ImageView logo;
    TextView logoText, sloganText;
    TextInputEditText studentenNummer, passwordInput;
    TextInputLayout studentNrLayout, passwordLayout;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen

        setContentView(R.layout.activity_login);

        forgetPassword = findViewById(R.id.forgetPassword);
        logIn = findViewById(R.id.logIn);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);

        logo = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        studentenNummer = findViewById(R.id.studNr);
        passwordInput = findViewById(R.id.password);

        studentNrLayout = findViewById(R.id.studentNrLayout);
        passwordLayout = findViewById(R.id.passwordLayout);

        databaseHelper = new DatabaseHelper(this);


        Intent intent = getIntent();
        if (intent.hasExtra("snackbarMessage")){
            Snackbar.make(this.findViewById(android.R.id.content),intent.getStringExtra("snackbarMessage"), BaseTransientBottomBar.LENGTH_SHORT).show();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser(studentenNummer.getText().toString(), passwordInput.getText().toString());
            }
        });



    }


    private void LoginUser(String username, String password){
        validateStudentNr();
        validatePassword();



        if (validateStudentNr() && validatePassword()){

            User userExists = databaseHelper.logInUser(username,password);
            toggleLoader();
            if (userExists != null){
                goToFeed(userExists);
            }else{
                toggleLoader();
                studentNrLayout.setError("Invalid login");
            }
        }
    }

    private void toggleLoader() {
        logIn.setVisibility((logIn.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
        progressBar.setVisibility((progressBar.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
    }

    private void goToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(logo, "logo_transistion");
        pairs[1] = new Pair<View, String>(logoText, "name_transistion");
        pairs[2] = new Pair<View, String>(sloganText, "slogan_transistion");
        pairs[3] = new Pair<View, String>(studentenNummer, "studnr_transistion");
        pairs[4] = new Pair<View, String>(passwordInput, "password_transistion");
        pairs[5] = new Pair<View, String>(logIn, "btn_transistion");
        pairs[6] = new Pair<View, String>(register, "login_signup_transistion");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
        startActivity(intent, options.toBundle());
         //zodat de gebruiker niet terug kan gaan (verwijderd het van de act list)
    }


    private void goToFeed(User loggedInUser){
        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        intent.putExtra("loggedInUser", loggedInUser);
        intent.putExtra("snackbarMessage", "Welkom "+ loggedInUser.getFirstName() + " " + loggedInUser.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }


    private Boolean validateStudentNr(){
        studentNrLayout.setErrorEnabled(true);
        String value = studentenNummer.getText().toString().trim();


        if (value.isEmpty()){
            studentNrLayout.setError("Aub je studentnummer invoeren");
            return false;
        }else{
            studentNrLayout.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        passwordLayout.setErrorEnabled(true);
        String value = passwordInput.getText().toString().trim();


        if (value.isEmpty()){
            passwordLayout.setError("Aub een wachtwoord invoeren");
            return false;
        }else{
            passwordLayout.setErrorEnabled(false);
            return true;
        }
    }
}
