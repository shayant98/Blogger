package sr.unasat.blogger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sr.unasat.blogger.Entity.Post;
import sr.unasat.blogger.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {
    private ArrayList<Post> posts;
    private Context context;

    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView postTitle, postAuthor, postDate, postBody;

        public PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            postAuthor = itemView.findViewById(R.id.post_author);
            postTitle = itemView.findViewById(R.id.post_title);
            postDate = itemView.findViewById(R.id.post_date);
            postBody = itemView.findViewById(R.id.post_body);

        }
    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item,parent,false);
        return new PostAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {
        Post currentPost = posts.get(position);



        holder.postTitle.setText(currentPost.getTitle());
        holder.postBody.setText(currentPost.getBody());
        holder.postDate.setText(currentPost.getDate());
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }
}
