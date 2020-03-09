package sr.unasat.blogger;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sr.unasat.blogger.Adapter.PostAdapter;
import sr.unasat.blogger.Entity.Post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FeedFragment extends Fragment {
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private ArrayList<Post> postArrayList = new ArrayList<Post>();
    private ProgressBar postProgress;

    public FeedFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);


        queue = Volley.newRequestQueue(getActivity());

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.postFeed);
        postProgress = view.findViewById(R.id.postProgress);

        toolbar.setBackgroundColor(getResources().getColor(R.color.textColorDark));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        getPosts();

        return view;
    }

//    TODO: Request to REST Service
private void getPosts(){
    final String URL = "http://rest.wayangrentalservices.com/wp-json/wp/v2/posts/";
    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                for (int i = 0; i < response.length(); i++){
                    JSONObject postObject = response.getJSONObject(i);

                    Post post = new Post(
                            postObject.getInt("id"),
                            postObject.getJSONObject("title").getString("rendered"),
                            postObject.getString("date"),
                            postObject.getJSONObject("content").getString("rendered")
                    );

                    postArrayList.add(post);
                    Log.i("debug", "onResponse: "+ post.toString());
                }

                PostAdapter postAdapter = new PostAdapter(postArrayList, getContext());
                recyclerView.setAdapter(postAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                postProgress.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("error", "onErrorResponse: "+ error.getMessage() );
        }

    });



    queue.add(request);
}

//    TODO: Setup Adapter for recyclerview with data from REST call
}
