package Database;

/**
 * Created by 788340 on 30/04/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.BloodGlucoseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseAccessBG {
    private SQLiteDatabase database;
    private DatabaseOpenHelperBG openHelper;
    private static volatile DatabaseAccessBG instance;

    private DatabaseAccessBG(Context context) {
        this.openHelper = new DatabaseOpenHelperBG(context);
    }

    public static synchronized DatabaseAccessBG getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessBG(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(BloodGlucoseModel bg) {
        ContentValues values = new ContentValues();
        values.put("date", bg.getTime());
        values.put("bloodGlucose", bg.getBG());
        database.insert(DatabaseOpenHelper.TABLE, null, values);
    }

    public void update(BloodGlucoseModel bg) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", bg.getBG());
        String date = Long.toString(bg.getTime());
        database.update(DatabaseOpenHelper.TABLE, values, "date = ?", new String[]{date});
    }

    public void delete(BloodGlucoseModel bg) {
        String date = Long.toString(bg.getTime());
        database.delete(DatabaseOpenHelper.TABLE, "date = ?", new String[]{date});
    }

    public List getAllMemos() {
        List bgs = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From memo ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            Integer BG = cursor.getInt(1);
            bgs.add(new BloodGlucoseModel(time, BG));
            cursor.moveToNext();
        }
        cursor.close();
        return bgs;
    }
}
