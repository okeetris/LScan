package model;

/**
 * Created by 788340 on 30/04/2017.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Blood glucose model.
 */
public class BloodGlucoseModel implements Serializable {
    private static Double BG;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    private Date date;

    /**
     * Instantiates a new Blood glucose model.
     */
    public BloodGlucoseModel() {
        this.date = new Date();
    }

    /**
     * Instantiates a new Blood glucose model.
     *
     * @param time the time
     * @param BG   the bg
     */
    public BloodGlucoseModel(long time, double BG) {
        this.date = new Date(time);
        BloodGlucoseModel.BG = BG;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return dateFormat.format(date);
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public long getTime() {
        return date.getTime();
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(long time) {
        this.date = new Date(time);
    }

    /**
     * Gets bg.
     *
     * @return the bg
     */
    public Double getBG() {
        return BG;
    }

    /**
     * Sets bg.
     *
     * @param BG the bg
     */
    public void setBG(double BG) {
        BloodGlucoseModel.BG = BG;
    }

    /**
     * To double double.
     *
     * @return the double
     */
//@Override
    public Double toDouble() {
        return BG;
    }
}
