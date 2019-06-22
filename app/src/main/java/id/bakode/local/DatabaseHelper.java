package id.bakode.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_BODY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_SELECT_QUERY = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
            COLUMN_NAME_ID+ " DESC";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    void storeData(String body) {
        mDatabase = this.getWritableDatabase();
        mContentValue = new ContentValues();
        mContentValue.put(COLUMN_NAME_BODY, body);
        mDatabase.insert(TABLE_NAME, null, mContentValue);
        mDatabase.close();
    }

    ArrayList<Post> getPosts() {
        ArrayList<Post> mPostData = new ArrayList<>();
        mDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor mCursor = mDatabase.rawQuery(SQL_SELECT_QUERY, null);
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
        mDatabase.update(
                TABLE_NAME,
                mContentValue,
                COLUMN_NAME_ID + "= ?",
                new String[]{String.valueOf(id)}
        );
        mDatabase.close();
    }

    void destroyData(Post post) {
        mDatabase = this.getWritableDatabase();
        mDatabase.delete(
                TABLE_NAME,
                COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(post.getId())}
        );
        mDatabase.close();
    }

}
