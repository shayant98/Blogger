package sr.unasat.blogger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sr.unasat.blogger.Entity.Post;
import sr.unasat.blogger.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {
    private ArrayList<Post> posts;
    private Context context;
    private onItemClickInterface listener;


    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public interface onItemClickInterface{
        void onItemClick(int position);
        void onItemShare(int position);
    }

    public void setOnItemClickListener(onItemClickInterface listener){
        this.listener = listener;
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder  {

        public TextView postTitle, postAuthor, postDate, postBody;
        public ImageView postImage;
        public MaterialCardView postCard;
        public ImageButton shareBtn;

        PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            postAuthor = itemView.findViewById(R.id.post_author);
            postTitle = itemView.findViewById(R.id.post_title);
            postDate = itemView.findViewById(R.id.post_date);
            postBody = itemView.findViewById(R.id.post_body);
            postCard = itemView.findViewById(R.id.postCard);
            postImage = itemView.findViewById(R.id.post_image);
            shareBtn = itemView.findViewById(R.id.shareBtn);

            postCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });


            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemShare(position);
                    }
                }
            });

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


        Picasso
                .get()
                .load(currentPost.getImage())
                .placeholder(R.drawable.unasat_logo)
                .into(holder.postImage);
        holder.postTitle.setText(currentPost.getTitle());
        holder.postBody.setText(currentPost.getBody());
        holder.postDate.setText(currentPost.getDate());
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }
}
