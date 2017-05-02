package Database;

/**
 * Created by 788340 on 29/03/2017.
 */

//import packages
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The type Database open helper.
 */
//start database open helper class
class DatabaseOpenHelper extends SQLiteOpenHelper {
    /**
     * The constant DATABASE.
     */
//set variables
    public static final String DATABASE = "memos.db";
    /**
     * The constant TABLE.
     */
    public static final String TABLE = "memo";
    /**
     * The constant VERSION.
     */
    public static final int VERSION = 1;

    /**
     * Instantiates a new Database open helper.
     *
     * @param context the context
     */
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }
    //set onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memo(date INTEGER PRIMARY KEY, memo TEXT);");
    }
    //set on upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

