package Database;

/**
 * Created by 788340 on 30/04/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The type Database open helper bg.
 */
public class  DatabaseOpenHelperBG extends SQLiteOpenHelper {
    /**
     * The constant DATABASE.
     */
    public static final String DATABASE = "BGs.db";
    /**
     * The constant TABLE.
     */
    public static final String TABLE = "BG";
    /**
     * The constant VERSION.
     */
    public static final int VERSION = 1;

    /**
     * Instantiates a new Database open helper bg.
     *
     * @param context the context
     */
    public DatabaseOpenHelperBG(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BG(date TEXT PRIMARY KEY, bloodglucose INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}