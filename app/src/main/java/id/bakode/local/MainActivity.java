package id.bakode.local;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends
        AppCompatActivity implements PostListener {

    private PostHelper mPostHelper;

    private PostAdapter mAdapter;

    private FloatingActionButton mCreatePost;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
        mPostHelper = new PostHelper(mDatabaseHelper);

        initView();
        initRecyclerView();
        onClickListener();
    }

    private void initView() {
         mCreatePost = findViewById(R.id.fab);
         mRecyclerView = findViewById(R.id.recycler_view_post);
    }

    private void initRecyclerView() {
        mAdapter = new PostAdapter(mPostHelper.getPosts(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void onClickListener() {
        mCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(false, null);
                Snackbar.make(view, "Add new post", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClickPost(Post post) {
        showAlertDialog(true, post);
        String mEditMessage = "post: " + post.getBody() + " edit!";
        showToast(mEditMessage);
    }

    @Override
    public void onDeletePost(Post post) {
        mPostHelper.destroyData(post);
        refreshList();
        String mDeleteMessage = "post: " + post.getBody() + " delete!";
        showToast(mDeleteMessage);
    }

    private void showAlertDialog(
            final Boolean isUpdate,
            final Post post
    ) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final EditText mInputBody = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 0, 20, 0);
        mInputBody.setLayoutParams(lp);

        alertDialog.setTitle((isUpdate) ? "Update post" : "Create new post");

        if (isUpdate) mInputBody.setText(post.getBody());

        alertDialog.setView(mInputBody);
        alertDialog.setPositiveButton((isUpdate) ? "UPDATE" : "CREATE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String mBody = mInputBody.getText().toString();

                        if (isUpdate) {
                            mPostHelper.updateData(post.getId(), mBody);
                        } else {
                            mPostHelper.storeData(mBody);
                        }

                        refreshList();

                        String mStoreMessage = "new post created with body: ";
                        String mUpdateMessage = "update post";
                        showToast((isUpdate) ? mUpdateMessage : mStoreMessage);
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void refreshList() {
        mAdapter.clearData();
        mAdapter.recreateData(mPostHelper.getPosts());
    }

    private void showToast(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

}
