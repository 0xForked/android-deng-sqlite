package id.bakode.local;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by A. A. Sumitro on 6/21/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */
public class PostAdapter extends
        RecyclerView.Adapter<PostViewHolder> {

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
            int position
    ) {
        holder.onBind(mPosts.get(position), mListener);
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

}
