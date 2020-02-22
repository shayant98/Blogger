package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class UpdateUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView updateBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen
        toolbar = findViewById(R.id.updateToolbar);
        updateBackBtn = findViewById(R.id.updateBackBtn);

        updateBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();
            }
        });


    }

    private void goToAccount() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("fragmentLoader",1);
        startActivity(intent);
        finish();
    }
}
