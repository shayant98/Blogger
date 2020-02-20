package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Animations
    Animation topAnimation, bottemAnimation;
    ImageView logo;
    TextView name, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen
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
    }
}
