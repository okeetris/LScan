package Database;

/**
 * Created by 788340 on 30/04/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseOpenHelperBG extends SQLiteOpenHelper {
    public static final String DATABASE = "BGs.db";
    public static final String TABLE = "BG";
    public static final int VERSION = 1;

    public DatabaseOpenHelperBG(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BG(date INTEGER PRIMARY KEY, bloodglucose INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}