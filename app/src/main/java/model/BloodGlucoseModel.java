package model;

/**
 * Created by 788340 on 30/04/2017.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodGlucoseModel implements Serializable {
    private Date date;
    private static Double BG;
    private boolean fullDisplayed;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");

    public BloodGlucoseModel() {
        this.date = new Date();
    }

    public BloodGlucoseModel(long time, double BG) {
        this.date = new Date(time);
        this.BG = BG;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public long getTime() {
        return date.getTime();
    }

    public void setTime(long time) {
        this.date = new Date(time);
    }

    public void setBG (double BG) {
        this.BG = BG;
    }

    public Double getBG() {
        return this.BG;
    }


    //@Override
    public Double toDouble() {
        return this.BG;
    }
}
