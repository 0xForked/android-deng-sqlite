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
        AppCompatActivity implements PostAdapter.PostListener {

    private DatabaseHelper mDatabaseHelper;

    private PostAdapter mAdapter;

    private FloatingActionButton mCreatePost;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = new DatabaseHelper(this);

        initView();
        initRecyclerView();
        onClickListener();
    }

    private void initView() {
         mCreatePost = findViewById(R.id.fab);
         mRecyclerView = findViewById(R.id.recycler_view_post);
    }

    private void initRecyclerView() {
        mAdapter = new PostAdapter(mDatabaseHelper.getPosts(), this);
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
                alertDialogNewAction();
                Snackbar.make(view, "Add new post", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClickPost(Post post) {
        alertDialogUpdateAction(post);
        Toast.makeText(
                this,
                "post: " + post.getBody() + " edit!",
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onDeletePost(Post post) {
        mDatabaseHelper.destroyData(post);
        mAdapter.clearData();
        mAdapter.recreateData(mDatabaseHelper.getPosts());
        Toast.makeText(
                this,
                "post: " + post.getBody() + " delete!",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void alertDialogNewAction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final EditText mInputBody = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 0, 20, 0);
        mInputBody.setLayoutParams(lp);
        alertDialog.setTitle("Create new post");
        alertDialog.setView(mInputBody);

        alertDialog.setPositiveButton("CREATE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String mBody = mInputBody.getText().toString();
                        mDatabaseHelper.storeData(mBody);
                        mAdapter.clearData();
                        mAdapter.recreateData(mDatabaseHelper.getPosts());
                        Toast.makeText(
                                getApplicationContext(),
                                "new post created with body: " + mBody,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void alertDialogUpdateAction(final Post post) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        final EditText mInputBody = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 0, 20, 0);
        mInputBody.setLayoutParams(lp);
        alertDialog.setTitle("Update post");
        alertDialog.setView(mInputBody);
        mInputBody.setText(post.getBody());

        alertDialog.setPositiveButton("UPDATE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String mBody = mInputBody.getText().toString();
                        mDatabaseHelper.updateData(post.getId(), mBody);
                        mAdapter.clearData();
                        mAdapter.recreateData(mDatabaseHelper.getPosts());
                        Toast.makeText(
                                getApplicationContext(),
                                "update post: " + mBody,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
