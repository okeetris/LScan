package model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;


import Database.bloodGlucoseRecord.BGReading;
import Database.bloodGlucoseRecord.BGRecord;
import Database.bloodGlucoseRecord.RawBGRecord;
import Database.DatabaseBuilder;


import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 17/01/2017.
 * The global model used throughout the application.
 */
public class UserModel implements IBloodGlucoseModel {


    private RawBGRecord rawBGRecord;

    private BGRecord historyBGRecord;

    private BGRecord currentBGRecord;

    private boolean usingImprovements = true;

    private SharedPreferences sharPref;


    public UserModel(DatabaseBuilder db, SharedPreferences sharPref) {

        rawBGRecord = db.getRawBGRecord();
        historyBGRecord = db.getHistoryBGRecord();
        currentBGRecord = db.getCurrentBGRecord();
        for (Calendar c : rawBGRecord.getAllBasicData().keySet()) {
            Log.d("Record", rawBGRecord.getAllBasicData().get(c));
            Log.d("EM: ", rawBGRecord.getAllBasicData().get(c).substring(586, 588) + rawBGRecord.getAllBasicData().get(c).substring(584, 586));
        }
        this.sharPref = sharPref;


    }


    @Override
    public void addRawData(Calendar c, String data) {
        rawBGRecord.addRawData(data, c);
    }

    @Override
    public void addHistoryReading(Calendar c, double reading) {
        historyBGRecord.insertReading(c, reading);
    }

    @Override
    public void addCurrentReading(Calendar c, double reading) {
        currentBGRecord.insertReading(c, reading);
    }

    @Override
    public BGReading getMostRecentHistoryReading() {
        return historyBGRecord.getMostRecentReading();
    }


    //@Override
    public Double getCurrentBG() {
        BGReading reading = currentBGRecord.getMostRecentReading();
        Calendar fifteenMinutesAgo = Calendar.getInstance();
        fifteenMinutesAgo.add(Calendar.MINUTE, -15);
        if (reading == null || reading.getTime().before(fifteenMinutesAgo)) {
            return null;
        }
        return reading.getReading();
    }


    private boolean sameDay(Calendar one, Calendar two) {
        return (one.get(Calendar.YEAR) == two.get(Calendar.YEAR)) && (one.get(Calendar.MONTH) == two.get(Calendar.MONTH)) && (one.get(Calendar.DAY_OF_MONTH) == two.get(Calendar.DAY_OF_MONTH));
    }


    private boolean timeLater(Calendar one, Calendar two) {
        return (one.get(Calendar.HOUR) > two.get(Calendar.HOUR)) || ((one.get(Calendar.HOUR) == two.get(Calendar.HOUR)) && (one.get(Calendar.MINUTE) > two.get(Calendar.MINUTE)));
    }


    public List<BGReading> getHistoryBetween(Calendar from, Calendar to) {
        return historyBGRecord.getReadingsBetween(from, to);
    }
}
