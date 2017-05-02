package activity.NFC_package;

/**
 * Created by tristanokeefe on 23/04/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Database.DatabaseAccessBG;


/**
 * The type Nfc parser.
 */
public class NfcParser  {

    private SharedPreferences sharedPreferences;
    private Activity activity;
    private static final String TAG = "NfcParser";
    private DatabaseAccessBG databaseAccess;
    /**
     * The Current reading.
     */
    public double currentReading;

    /**
     * Instantiates a new Nfc parser.
     *
     * @param activity the activity
     */
    public NfcParser(Activity activity){
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.activity = activity;
    }



    private static double linearConversion(int val) {
        int bitmask = 0x0FFF;
        return ((val & bitmask) / 153);
    }

    /**
     * Parse nfc double.
     *
     * @param result the result
     * @return the double
     */
    public double parseNfc(String result){
        Calendar now = Calendar.getInstance();

        //userModel.addRawData(now, result);

        //Get relevant pointers
        int glucosePointer = Integer.parseInt(result.substring(4, 6), 16);
        int elapsedMinutes = Integer.parseInt(result.substring(586,588) + result.substring(584,586),16);
        int historyPointer = Integer.parseInt(result.substring(6, 8), 16);
        int readings[] = new int[16];
        int historicalReadings[] = new int[32];
        Map<Calendar, Double> historyMap = new HashMap<>();

        for (int i = 8, j = 0; i < 8 + (16 * 12); i += 12, j++) {
            final String g = result.substring(i + 2, i + 4) + result.substring(i, i + 2);
            readings[j] = Integer.parseInt(g, 16);
            Log.d(TAG, "readings "+ readings[j]);
        }

        for(int i = 200, j = 0; i <= 200 + (31 * 12); i +=12, j++){
            final String g = result.substring(i + 2, i + 4) + result.substring(i, i + 2);
            historicalReadings[j] = Integer.parseInt(g, 16);
            Log.d(TAG, "historicalReadings "+ historicalReadings[j]);
        }
        Log.d(TAG, "historicalReadings" + historicalReadings.length);

        double currentReading = linearConversion(readings[((glucosePointer+15)%16)]);
        //userModel.addCurrentReading(now, currentReading);
        double historicReading = linearConversion(readings[((historyPointer+15)%16)]);
        Log.d(TAG, "CR: "+ currentReading);
        //BloodGlucoseModel bloodglucosemodel = new BloodGlucoseModel();
        //bloodglucosemodel.setBG(currentReading);
        Log.d(TAG, "HR: "+ historicReading);


        return currentReading;
    }

}