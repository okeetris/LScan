package Database;

/**
 * Created by 788340 on 30/04/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BloodGlucoseModel;


/**
 * The type Database access bg.
 */
public class DatabaseAccessBG {
    private SQLiteDatabase database;
    /**
     * The Sqlite database.
     */
    SQLiteDatabase sqliteDatabase;
    private DatabaseOpenHelperBG openHelper;
    private static volatile DatabaseAccessBG instance;
    private List<BloodGlucoseModel> BGs;
    private List<BloodGlucoseModel> BGs1;


    /**
     * Instantiates a new Database access bg.
     *
     * @param context the context
     */
    public DatabaseAccessBG(Context context) {
        this.openHelper = new DatabaseOpenHelperBG(context);
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static synchronized DatabaseAccessBG getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessBG(context);
        }
        return instance;
    }

    /**
     * Open.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Save.
     *
     * @param bg the bg
     */
    public void save(BloodGlucoseModel bg) {
        ContentValues values = new ContentValues();
        values.put("date", bg.getTime());
        values.put("bloodGlucose", bg.getBG());
        database.insert(DatabaseOpenHelperBG.TABLE, null, values);
    }

    /**
     * Update.
     *
     * @param bg the bg
     */
    public void update(BloodGlucoseModel bg) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("bloodGlucose", bg.getBG());
        String date = Long.toString(bg.getTime());
        database.update(DatabaseOpenHelperBG.TABLE, values, "date = ?", new String[]{date});
    }

    /**
     * Delete.
     *
     * @param bg the bg
     */
    public void delete(BloodGlucoseModel bg) {
        String date = Long.toString(bg.getTime());
        database.delete(DatabaseOpenHelperBG.TABLE, "date = ?", new String[]{date});
    }

    /**
     * Gets all b gs.
     *
     * @return the all b gs
     */
    public List getAllBGs() {
        database=openHelper.getReadableDatabase();
        List bgs = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From BG ORDER BY date DESC", null);
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

    /**
     * Get data data point [ ].
     *
     * @return the data point [ ]
     */
    /*
    public DataPoint[] getData() {
        //read data from database
        //this.BGs = getAllBGs();
        /*
        sqliteDatabase=openHelper.getReadableDatabase();
        this.BGs = getAllBGs();
        Cursor cursor = sqliteDatabase.rawQuery("SELECT * From BG ORDER BY date DESC", null);
        cursor.moveToFirst();
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        final String[] xLabels = new String[] {};
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            Integer BG = cursor.getInt(1);
            BGs.add(new BloodGlucoseModel(time, BG));
            cursor.moveToNext();
            Log.d(TAG, "getData: " + time + "  " + BG);
        }
        for (int i = 0; i < BGs.size(); i++ ){
            final BloodGlucoseModel BG = BGs.get(i);
            double bg = Double.parseDouble(BG.getDate());
            dp[i] = new DataPoint(BG.getDate(), BG.getBG());
        }
        cursor.close();

        sqliteDatabase=openHelper.getReadableDatabase();
        String [] columns ={"date", "bloodglucose"};
        Cursor cursor = sqliteDatabase.query("BG", columns, null, null, null, null, null, null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        //cursor.moveToFirst();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

        });
        for (int i = 0; i < cursor.getCount(); i++) {
            //final BloodGlucoseModel BG = BGs.get(i);
            cursor.moveToNext();
            String dateString = cursor.getString(0);
            Log.d(TAG, "getData: " + dateString);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //Date date = format.parse("2017-08-21 12:45:30");
                Log.d(TAG, "getData: " + dateString);

                Date date = format.parse(dateString);
                series[i] = new DataPoint(date, cursor.getInt(1));


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        return dp;
    }*/
    /*
    public DataPoint[] getData() {
        //read data from database
        String [] columns ={"date", "bloodglucose"};
        Cursor cursor = database.rawQuery("SELECT * From BG ORDER BY date DESC", null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        Log.d(TAG, "getData: " + dp);
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            dp[i]=new DataPoint(cursor.getInt(0), cursor.getInt(1));
        }
        return dp;

    }
    */
    /*
    public List getAllDates() {
        List Dates = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From BG ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            Dates.add
            cursor.moveToNext();
        }
    }*/

}
