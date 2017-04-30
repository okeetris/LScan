package Database.bloodGlucoseRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by lidda on 25/03/2017.
 */

public class CurrentBGModel implements BGRecord {

    private SimpleDateFormat dateFormat;
    private SQLiteDatabase write;

    public CurrentBGModel(SQLiteDatabase write){
        this.write = write;
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    }

    /**
     * Gets a list of the readings between two specified times
     * @param from The lower limit time (should be before to)
     * @param to The upper limit time (should be after from)
     * @return A list containing all of the readings, in order, with the most recent
     * reading in position 0.
     */
    @Override
    public List<BGReading> getReadingsBetween(Calendar from, Calendar to) {
        String selectQuery = "SELECT * FROM " + CurrentBGContract.ContentsDefinition.TABLE_NAME + " WHERE " +
                CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME + ">= '" + getDateTime(from) +
                "' AND " +CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME + "<='" + getDateTime(to) +
                "' ORDER BY " + CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME + " DESC";
        List<BGReading> readings = new ArrayList<>();
        Cursor c = write.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                double r = c.getDouble(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_READING));
                Calendar time = parseCalendar(c.getString(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_TIME)));
                BGReading reading = new BGReading(time, r);

                readings.add(reading);
            } while (c.moveToNext());
        }
        c.close();
        return readings;
    }

    @Override
    public BGReading getMostRecentReadingBefore(Calendar before) {
        String selectQuery = "SELECT * FROM " + CurrentBGContract.ContentsDefinition.TABLE_NAME +
                "WHERE "+CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME+"<=?"+" ORDER BY " +
                CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME + " DESC LIMIT 1";

        Cursor c = write.rawQuery(selectQuery, new String[]{getDateTime(before)});

        if(c!=null) {
            c.moveToFirst();
        } else {
            return null;
        }
        if(c.getCount()==0){
            return null;
        }
        double r = c.getDouble(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_READING));
        Calendar time = parseCalendar(c.getString(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_TIME)));
        BGReading reading = new BGReading(time, r);
        c.close();
        return reading;

    }

    @Override
    public BGReading getMostRecentReading() {
        String selectQuery = "SELECT * FROM " + CurrentBGContract.ContentsDefinition.TABLE_NAME + " ORDER BY " +
                CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME + " DESC LIMIT 1";

        Cursor c = write.rawQuery(selectQuery, null);

        if(c!=null) {
            c.moveToFirst();
        } else {
            return null;
        }
        if(c.getCount()==0){
            return null;
        }
        double r = c.getDouble(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_READING));
        Calendar time = parseCalendar(c.getString(c.getColumnIndex(HistoryBGContract.ContentsDefinition.COLUMN_NAME_TIME)));
        BGReading reading = new BGReading(time, r);
        c.close();
        return reading;

    }

    @Override
    public void insertReading(Calendar time, double reading) {

        ContentValues values = new ContentValues();
        values.put(CurrentBGContract.ContentsDefinition.COLUMN_NAME_READING, reading);
        values.put(CurrentBGContract.ContentsDefinition.COLUMN_NAME_TIME, getDateTime(time));
        // insert row
        write.insert(CurrentBGContract.ContentsDefinition.TABLE_NAME, null, values);

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
