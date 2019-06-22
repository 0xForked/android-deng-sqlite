package id.bakode.local;

/**
 * Created by A. A. Sumitro on 6/21/2019
 * aasumitro@merahputih.id
 * https://aasumitro.id
 */

class DatabaseConstant {

    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "bakode";

    static final String TABLE_NAME = "post";

    static final String COLUMN_NAME_ID = "id";
    static final String COLUMN_NAME_BODY = "note";

     static final String SQL_QUERY_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_BODY + " TEXT)";

     static final String SQL_QUERY_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

     static final String SQL_QUERY_SELECT_QUERY = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
            COLUMN_NAME_ID+ " DESC";

}
