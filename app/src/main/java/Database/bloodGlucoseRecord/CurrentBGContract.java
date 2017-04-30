package Database.bloodGlucoseRecord;

import android.provider.BaseColumns;

/**
 * Created by lidda on 25/03/2017.
 */

public class CurrentBGContract {

    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "CurrentTable";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_READING = "Reading";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + ContentsDefinition.TABLE_NAME + " (" + ContentsDefinition._ID + " INTEGER PRIMARY KEY," + ContentsDefinition.COLUMN_NAME_READING
            + " DECIMAL," + ContentsDefinition.COLUMN_NAME_TIME
            + " DATETIME" + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
