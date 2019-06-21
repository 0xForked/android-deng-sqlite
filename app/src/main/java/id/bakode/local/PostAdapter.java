package id.bakode.local;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by A. A. Sumitro on 6/21/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */
public class PostAdapter extends
        RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> mPosts;
    private PostListener mListener;

    PostAdapter(
            ArrayList<Post> posts,
            PostListener listener
    ) {
        this.mPosts = posts;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(
                R.layout.item_post,
                parent,
                false
        );
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PostViewHolder holder,
            final int position
    ) {
        holder.mPostBody.setText(mPosts.get(position).getBody());
        holder.mPostContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickPost(mPosts.get(position));
            }
        });
        holder.mDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeletePost(mPosts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mPosts != null) ? mPosts.size() : 0;
    }

    void clearData() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    void recreateData(ArrayList<Post> posts) {
        this.mPosts.addAll(0, posts);
        notifyDataSetChanged();
    }

    class PostViewHolder extends
            RecyclerView.ViewHolder {
        private TextView mPostBody;
        private LinearLayout mPostContainer;
        private ImageButton mDeletePost;
        PostViewHolder(View itemView) {
            super(itemView);
            mPostContainer = itemView.findViewById(R.id.post_container);
            mPostBody = itemView.findViewById(R.id.post_body);
            mDeletePost = itemView.findViewById(R.id.post_delete);
        }
    }

    interface PostListener {
        void onClickPost(Post post);
        void onDeletePost(Post post);
    }

}
