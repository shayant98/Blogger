package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import sr.unasat.blogger.Entity.User;
import sr.unasat.blogger.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Splashscreen";
    private static int SPLASH_SCREEN_DURATION = 2000;

    //Animations
    Animation topAnimation, bottemAnimation;
    ImageView logo;
    TextView name, slogan;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loadAnimtions
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation);
        bottemAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);

        //get fields
        logo = findViewById(R.id.logo);
        name = findViewById(R.id.name);
        slogan = findViewById(R.id.slogan);

        //set animation
        logo.setAnimation(topAnimation);
        name.setAnimation(bottemAnimation);
        slogan.setAnimation(bottemAnimation);
        databaseHelper = new DatabaseHelper(this);

         final User loggedInUser = databaseHelper.getLoggedInUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 if (loggedInUser != null){
                     goToFeed();
                 }else{
                     goToLogin();
                 }

            }
        }, SPLASH_SCREEN_DURATION);

    }

    private void goToLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(logo, "logo_transistion");
        pairs[1] = new Pair<View, String>(name, "name_transition");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
        startActivity(intent, options.toBundle());
        finish();
    }

    private void goToFeed(){
        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
