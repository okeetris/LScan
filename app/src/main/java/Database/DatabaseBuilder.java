package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Database.bloodGlucoseRecord.BGRecord;
import Database.bloodGlucoseRecord.CurrentBGContract;
import Database.bloodGlucoseRecord.CurrentBGModel;
import Database.bloodGlucoseRecord.HistoryBGContract;
import Database.bloodGlucoseRecord.HistoryBGModel;
import Database.bloodGlucoseRecord.RawBGDatabase;
import Database.bloodGlucoseRecord.RawBGRecord;
import Database.bloodGlucoseRecord.RawDataContract;

/**
 * Created by kbb12 on 24/03/2017.
 */

public class DatabaseBuilder extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="BackingStorage";
    private static final int versionNumber=14;

    private RawBGRecord rawBGRecord;
    private BGRecord historyBGRecord;
    private BGRecord currentBGRecord;

    public RawBGRecord getRawBGRecord(){
        return rawBGRecord;
    }

    public BGRecord getHistoryBGRecord(){
        return historyBGRecord;
    }

    public BGRecord getCurrentBGRecord(){
        return currentBGRecord;
    }


    public DatabaseBuilder(Context context){
        super(context,DATABASE_NAME,null,versionNumber);
        rawBGRecord = new RawBGDatabase(getWritableDatabase());
        historyBGRecord = new HistoryBGModel(getWritableDatabase());
        currentBGRecord = new CurrentBGModel(getWritableDatabase());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(RawDataContract.SQL_CREATE_ENTRIES);
        db.execSQL(HistoryBGContract.SQL_CREATE_TABLE);
        db.execSQL(CurrentBGContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RawDataContract.SQL_DELETE_ENTRIES);
        db.execSQL(HistoryBGContract.SQL_DELETE_ENTRIES);
        db.execSQL(CurrentBGContract.SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
