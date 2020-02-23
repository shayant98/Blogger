package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    Button SignUpBtn, logIn;
    ImageView logo;
    TextView logoText, sloganText;
    TextInputEditText studentenNummer, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen


        SignUpBtn = findViewById(R.id.SignUp);
        logIn = findViewById(R.id.goToLogIn);

        logo = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoTextRegister);
        sloganText = findViewById(R.id.sloganTextRegister);
        studentenNummer = findViewById(R.id.studentNrRegister);
        passwordInput = findViewById(R.id.passwordRegister);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
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
        pairs[5] = new Pair<View, String>(SignUpBtn, "btn_transistion");
        pairs[6] = new Pair<View, String>(logIn, "login_signup_transistion");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        startActivity(intent, options.toBundle());
        //zodat de gebruiker niet terug kan gaan (verwijderd het van de act list)
    }


    private Boolean validateStudentNr() {
        String value = studentenNummer.getText().toString();

        if (value.isEmpty()) {
            studentenNummer.setError("Field cannot be empty");
            return false;
        } else {
            studentenNummer.setError(null);
            return true;
        }
    }


}
