package id.bakode.local;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by A. A. Sumitro on 6/27/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */
class PostViewHolder extends RecyclerView.ViewHolder {

    private TextView mPostBody;
    private LinearLayout mPostContainer;
    private ImageButton mDeletePost;

    PostViewHolder(@NonNull View itemView) {
        super(itemView);
        mPostContainer = itemView.findViewById(R.id.post_container);
        mPostBody = itemView.findViewById(R.id.post_body);
        mDeletePost = itemView.findViewById(R.id.post_delete);
    }

    void onBind(final Post post, final PostListener listener) {
       mPostBody.setText(post.getBody());
       mPostContainer.setOnClickListener(v ->  listener.onClickPost(post));
       mDeletePost.setOnClickListener(v -> listener.onDeletePost(post));
    }

}
