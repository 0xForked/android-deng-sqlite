package id.bakode.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

import static id.bakode.local.DatabaseConstant.COLUMN_NAME_BODY;
import static id.bakode.local.DatabaseConstant.COLUMN_NAME_ID;
import static id.bakode.local.DatabaseConstant.SQL_QUERY_SELECT_TABLE;
import static id.bakode.local.DatabaseConstant.TABLE_NAME;

/**
 * Created by A. A. Sumitro on 6/27/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */
class PostHelper {

    private DatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;
    private ContentValues mContentValue;

    PostHelper(DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    void storeData(String body) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mContentValue = new ContentValues();
        mContentValue.put(COLUMN_NAME_BODY, body);
        try {
            mDatabase.insert(TABLE_NAME, null, mContentValue);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

    ArrayList<Post> fetchData() {
        mDatabase = mDatabaseHelper.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor mCursor = mDatabase.rawQuery(SQL_QUERY_SELECT_TABLE, null);
        ArrayList<Post> mPostsData = new ArrayList<>();
        try {
            if (mCursor.moveToFirst()) {
                do {
                    int id = mCursor.getInt(mCursor.getColumnIndex(COLUMN_NAME_ID));
                    String body = mCursor.getString(mCursor.getColumnIndex(COLUMN_NAME_BODY));

                    Post post = new Post();
                    post.setId(id);
                    post.setBody(body);

                    mPostsData.add(post);
                } while (mCursor.moveToNext());
            }
        } finally {
            mDatabase.close();
        }
        return mPostsData;
    }

    void updateData(int id, String body) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        mContentValue = new ContentValues();
        mContentValue.put(COLUMN_NAME_BODY, body);
        try {
            String[] mWhereArgs = new String[]{String.valueOf(id)};
            mDatabase.update(
                    TABLE_NAME,
                    mContentValue,
                    COLUMN_NAME_ID + "= ?",
                    mWhereArgs
            );
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

    void destroyData(Post post) {
        mDatabase = mDatabaseHelper.getWritableDatabase();
        try {
            String[] mWhereArgs = new String[]{String.valueOf(post.getId())};
            mDatabase.delete(
                    TABLE_NAME,
                    COLUMN_NAME_ID + " = ?",
                    mWhereArgs
            );
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

}
