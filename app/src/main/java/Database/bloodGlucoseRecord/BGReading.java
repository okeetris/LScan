package Database.bloodGlucoseRecord;

import java.util.Calendar;

/**
 * Created by lidda on 25/03/2017.
 */

public class BGReading {

    public Calendar time;
    private double reading;

    public BGReading (Calendar time, double reading){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time.getTime());
        this.time = cal;
        this.reading = reading;
    }

    public Calendar getTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time.getTime());
        return cal;
    }

    public double getReading(){
        return reading;
    }

}
