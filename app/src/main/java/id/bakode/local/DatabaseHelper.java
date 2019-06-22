package id.bakode.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static id.bakode.local.DatabaseConstant.*;

/**
 * Created by A. A. Sumitro on 6/21/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDatabase;
    private ContentValues mContentValue;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_QUERY_CREATE_ENTRIES);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_QUERY_DELETE_ENTRIES);
            onCreate(db);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onUpgrade(db, oldVersion, newVersion);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    void storeData(String body) {
        mDatabase = this.getWritableDatabase();
        mContentValue = new ContentValues();
        mContentValue.put(COLUMN_NAME_BODY, body);
        try {
            mDatabase.insert(TABLE_NAME, null, mContentValue);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

    ArrayList<Post> getPosts() {
        ArrayList<Post> mPostData = new ArrayList<>();
        mDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor mCursor = mDatabase.rawQuery(SQL_QUERY_SELECT_QUERY, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    Post post = new Post();
                    post.setId(mCursor.getInt(mCursor.getColumnIndex(COLUMN_NAME_ID)));
                    post.setBody(mCursor.getString(mCursor.getColumnIndex(COLUMN_NAME_BODY)));
                    mPostData.add(post);
                } while (mCursor.moveToNext());
            }
        } finally {
            mDatabase.close();
        }
        return mPostData;
    }

    void updateData(int id, String body) {
        mDatabase = this.getWritableDatabase();
        mContentValue = new ContentValues();
        mContentValue.put(COLUMN_NAME_BODY, body);
        try {
            mDatabase.update(
                    TABLE_NAME,
                    mContentValue,
                    COLUMN_NAME_ID + "= ?",
                    new String[]{String.valueOf(id)}
            );
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

    void destroyData(Post post) {
        mDatabase = this.getWritableDatabase();
        try {
            mDatabase.delete(
                    TABLE_NAME,
                    COLUMN_NAME_ID + " = ?",
                    new String[]{String.valueOf(post.getId())}
            );
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        mDatabase.close();
    }

}
