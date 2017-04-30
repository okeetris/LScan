package Database.bloodGlucoseRecord;

import android.provider.BaseColumns;

/**
 * Created by lidda on 25/03/2017.
 */

public class RawDataContract {

    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "BasicTable";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_DATA = "DataString";
    }

    // Table Create Statements
    // Basic table create statement for mini-test-app
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + ContentsDefinition.TABLE_NAME + " (" + ContentsDefinition._ID + " INTEGER PRIMARY KEY," + ContentsDefinition.COLUMN_NAME_DATA
            + " TEXT," + ContentsDefinition.COLUMN_NAME_TIME
            + " DATETIME" + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
