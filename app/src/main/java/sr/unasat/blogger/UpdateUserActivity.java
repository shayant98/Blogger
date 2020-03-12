package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UpdateUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView updateBackBtn;
    Button updateUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen
        toolbar = findViewById(R.id.updateToolbar);
        updateBackBtn = findViewById(R.id.updateBackBtn);
        updateUserBtn = findViewById(R.id.UpdateUserBtn);

        updateBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();
            }
        });
        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });


    }

    private void goToAccount() {
        goToPreviousActivity();
    }

    private void updateUser(){
        AsyncUpdateUser updateUserTask = new AsyncUpdateUser();
        updateUserTask.execute(10);
    }

//   TODO: Receive ContentValues object iof Integer
    private class AsyncUpdateUser extends AsyncTask<Integer,Integer,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Integer... contentValues)
        {
//            TODO: Actually update User
            return "Updated!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            goToPreviousActivity();
        }
    }

    void goToPreviousActivity(){
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("fragmentLoader",1);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
