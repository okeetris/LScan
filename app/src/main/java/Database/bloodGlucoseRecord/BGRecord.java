package Database.bloodGlucoseRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lidda on 25/03/2017.
 */

public interface BGRecord {
    List<BGReading> getReadingsBetween(Calendar from, Calendar to);
    BGReading getMostRecentReading();
    BGReading getMostRecentReadingBefore(Calendar before);
    void insertReading(Calendar time, double reading);
}
