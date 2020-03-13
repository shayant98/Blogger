package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import sr.unasat.blogger.Entity.Student;
import sr.unasat.blogger.Entity.User;
import sr.unasat.blogger.database.DatabaseHelper;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register";
    Button signUpBtn, logIn;
    ImageView logo;
    TextView logoText, sloganText;
    TextInputEditText studentenNummer, passwordInput, passwordConfirm;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen

        databaseHelper = new DatabaseHelper(this);

        signUpBtn = findViewById(R.id.SignUp);
        logIn = findViewById(R.id.goToLogIn);

        logo = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoTextRegister);
        sloganText = findViewById(R.id.sloganTextRegister);
        studentenNummer = findViewById(R.id.studentNrRegister);
        passwordInput = findViewById(R.id.passwordRegister);
        passwordConfirm = findViewById(R.id.passwordConfirmRegister);

        studentenNummer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && studentenNummer.length() > 0){
                    validateStudentNr();
                }
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(studentenNummer.getText().toString(), passwordInput.getText().toString());
            }
        });

    }

    private void goToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(logo, "logo_transistion");
        pairs[1] = new Pair<View, String>(logoText, "name_transistion");
        pairs[2] = new Pair<View, String>(sloganText, "slogan_transistion");
        pairs[3] = new Pair<View, String>(studentenNummer, "studnr_transistion");
        pairs[4] = new Pair<View, String>(passwordInput, "password_transistion");
        pairs[5] = new Pair<View, String>(signUpBtn, "btn_transistion");
        pairs[6] = new Pair<View, String>(logIn, "login_signup_transistion");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        startActivity(intent, options.toBundle());
        finish();
    }


//    TODO: Build validation
//    TODO: Register User Function
    private boolean validateStudentNr()
    {
        String value = Objects.requireNonNull(studentenNummer.getText()).toString();

        if (value.isEmpty()) {
            studentenNummer.setError("Field cannot be empty");
            return false;
        } else {
            boolean userExists = databaseHelper.getUserByStudNr(value);
            if (!userExists){
                Student studentExists = databaseHelper.getStudentByStudNr(value);
                if (studentExists == null){
                    studentenNummer.setError("Studentnummer bestaat niet");
                    return false;
                }else {
                    studentenNummer.setError(null);
                    return true;
                }
            }else{
                studentenNummer.setError("Account met dit studentnummer bestaat al");
                return false;
            }
        }
    }


    private boolean validatePassword(){
        String passwordValue = passwordInput.getText().toString();
        String passwordConfirmValue = passwordConfirm.getText().toString();

        if (passwordValue.length() <  6){
            passwordInput.setError("Ongeldig");
            return false;
        }

        if (!passwordValue.equals(passwordConfirmValue)){
            passwordInput.setError("Ongeldig");
            passwordConfirm.setError("Ongeldig");
            return false;
        }

        passwordInput.setError(null);
        passwordConfirm.setError(null);
        return true;


    }

    private void registerUser(String studentnummer, String password){

        if (validatePassword() && validateStudentNr()){

            Student studentInfo = databaseHelper.getStudentByStudNr(studentnummer);
            Log.d(TAG, "registerUser: "+ studentInfo.toString());

            ContentValues newUser = new ContentValues();
            newUser.put("username", studentnummer);
            newUser.put("password", password);
            newUser.put("student_id", studentInfo.getId());
            newUser.put("email", studentInfo.getEmail());
            newUser.put("role", "student");
            newUser.put("logged_in", 0);

            Log.d(TAG, "registerUser: "+ newUser);

            if(databaseHelper.insertUser(newUser)){
                goToFeed(databaseHelper.logInUser(studentnummer,password));
            }
        }
    }

    private void goToFeed(User loggedInUser){
        Intent intent = new Intent(RegisterActivity.this, NavigationActivity.class);
        intent.putExtra("loggedInUser", loggedInUser);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }


}
