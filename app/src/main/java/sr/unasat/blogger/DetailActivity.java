package sr.unasat.blogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import sr.unasat.blogger.Entity.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
//    TextView postTitle;
    ImageButton detailBackBtn;
    TextView postBody;
    Toolbar detailToolbar;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //set app als fullscreen


        detailToolbar = findViewById(R.id.detailToolbar);
        queue = Volley.newRequestQueue(this);


        Intent intent = getIntent();
        String title = intent.getStringExtra("PostTitle");
        int id  = intent.getIntExtra("PostId", 0);

        detailToolbar.setTitle(title);

//        postTitle = findViewById(R.id.postTitle);
        detailBackBtn = findViewById(R.id.detailBackBtn);
        postBody = findViewById(R.id.postBody);
        getPost(id);


//
//
        detailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousActivity();
            }
        });
    }


    void goToPreviousActivity(){
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("fragmentLoader",0);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    private void getPost(int id){
        final String URL = "http://rest.wayangrentalservices.com/wp-json/wp/v2/posts/"+ id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject postObject) {
                try {
                    Post post = new Post(
                            postObject.getInt("id"),
                            postObject.getJSONObject("title").getString("rendered"),
                            postObject.getString("date"),
                            postObject.getJSONObject("content").getString("rendered")
                    );
                    postBody.setText(post.getBody());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        queue.add(request);
    }
}
