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

public class LoginActivity extends AppCompatActivity {

    Button forgetPassword, logIn, register;
    ImageView logo;
    TextView logoText, sloganText;
    TextInputEditText studentenNummer, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen

        setContentView(R.layout.activity_login);

        forgetPassword = findViewById(R.id.forgetPassword);
        logIn = findViewById(R.id.logIn);
        register = findViewById(R.id.register);

        logo = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        studentenNummer = findViewById(R.id.studNr);
        passwordInput = findViewById(R.id.password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFeed();
            }
        });


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


    private void goToFeed(){
        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent);
        finish();
    }
}
