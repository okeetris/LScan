package Database.bloodGlucoseRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lidda on 25/03/2017.
 */

public class RawBGDatabase implements RawBGRecord {

    private SQLiteDatabase write;
    private SimpleDateFormat dateFormat;


    public RawBGDatabase(SQLiteDatabase write){
        //TODO: Link this up, create other one.
        this.write = write;
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    public long addRawData(String rawData, Calendar timestamp) {

        ContentValues values = new ContentValues();
        Log.d("RAWDB", rawData);
        values.put(RawDataContract.ContentsDefinition.COLUMN_NAME_DATA, rawData);
        values.put(RawDataContract.ContentsDefinition.COLUMN_NAME_TIME, getDateTime(timestamp));

        // insert row
        long id = write.insert(RawDataContract.ContentsDefinition.TABLE_NAME, null, values);

        return id;
    }

    @Override
    public Map<Calendar, String> getAllBasicData(){
        Map<Calendar, String> basicData = new HashMap<Calendar, String>();

        String selectQuery = "SELECT  * FROM " + RawDataContract.ContentsDefinition.TABLE_NAME;

        Cursor c = write.rawQuery(selectQuery, null);

        // looping through all rows and adding to map
        if (c.moveToFirst()) {
            do {
                basicData.put(parseCalendar(c.getString(c.getColumnIndex(RawDataContract.ContentsDefinition.COLUMN_NAME_TIME))), c.getString(c.getColumnIndex(RawDataContract.ContentsDefinition.COLUMN_NAME_DATA)));
            } while (c.moveToNext());
        }

        return basicData;

    }

    private String getDateTime(Calendar timestamp) {
        return dateFormat.format(timestamp.getTime());
    }

    private Calendar parseCalendar(String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            return null;
        }
        return c;
    }
}
