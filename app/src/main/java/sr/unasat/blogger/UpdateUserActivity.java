package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import sr.unasat.blogger.database.DatabaseHelper;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class UpdateUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView updateBackBtn;
    Button updateUserBtn;
    TextInputEditText updatePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen
        toolbar = findViewById(R.id.updateToolbar);
        updateBackBtn = findViewById(R.id.updateBackBtn);
        updateUserBtn = findViewById(R.id.UpdateUserBtn);

        updatePhone = findViewById(R.id.phoneUpdate);

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

        ContentValues contentValues = new ContentValues();

        contentValues.put("phone_number", updatePhone.getText().toString());


        updateUserTask.execute(contentValues);
    }

//   TODO: Receive ContentValues object iof Integer
    private class AsyncUpdateUser extends AsyncTask<ContentValues,Integer,String>{
        DatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            databaseHelper = new DatabaseHelper(UpdateUserActivity.this);
        }


        @Override
        protected String doInBackground(ContentValues... contentValues)
        {

            databaseHelper.updateStudent(contentValues[0]);
            return "";
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
        intent.putExtra("snackbarMessage", "Gegevens successvol aangepast");
        setResult(RESULT_OK, intent);
        finish();
    }
}
