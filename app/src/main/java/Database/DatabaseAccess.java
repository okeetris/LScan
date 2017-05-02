package Database;

/**
 * Created by 788340 on 29/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Memo;


/**
 * The type Database access.
 */
//create database access class
public class DatabaseAccess {
    //set variables
    private SQLiteDatabase database;
    private DatabaseOpenHelper openHelper;
    private static volatile DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
//set a get instance method
    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open.
     */
//set method for opening database
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close.
     */
//set method for closing database
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Save.
     *
     * @param memo the memo
     */
//set method for saving a memo
    public void save(Memo memo) {
        ContentValues values = new ContentValues();
        values.put("date", memo.getTime());
        values.put("memo", memo.getText());
        database.insert(DatabaseOpenHelper.TABLE, null, values);
    }

    /**
     * Update.
     *
     * @param memo the memo
     */
// set method for updating method
    public void update(Memo memo) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", memo.getText());
        String date = Long.toString(memo.getTime());
        database.update(DatabaseOpenHelper.TABLE, values, "date = ?", new String[]{date});
    }

    /**
     * Delete.
     *
     * @param memo the memo
     */
//set method for deleting from database
    public void delete(Memo memo) {
        String date = Long.toString(memo.getTime());
        database.delete(DatabaseOpenHelper.TABLE, "date = ?", new String[]{date});
    }

    /**
     * Gets all memos.
     *
     * @return the all memos
     */
//set method for returning all memos in database
    public List getAllMemos() {
        List memos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From memo ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            memos.add(new Memo(time, text));
            cursor.moveToNext();
        }
        cursor.close();
        return memos;
    }

}