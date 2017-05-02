package model;

/**
 * Created by 788340 on 29/03/2017.
 */

//import packages

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Memo.
 */
//set class for displaying memo
public class Memo implements Serializable {
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    //set variables
    private Date date;
    private String text;
    private boolean fullDisplayed;

    /**
     * Instantiates a new Memo.
     */
//memo date
    public Memo() {
        this.date = new Date();
    }

    /**
     * Instantiates a new Memo.
     *
     * @param time the time
     * @param text the text
     */
//create memo
    public Memo(long time, String text) {
        this.date = new Date(time);
        this.text = text;
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
//get time
    public long getTime() {
        return date.getTime();
    }

    /**
     * Sets time.
     */
    public void setTime() {
    }

    /**
     * Gets text.
     *
     * @return the text
     */
//get text
    public String getText() {
        return this.text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
//set text
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets short text.
     *
     * @return the short text
     */
//get shortened version of text for not full displayed
    public String getShortText() {
        String temp = text.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }

    /**
     * Is full displayed boolean.
     *
     * @return the boolean
     */
//set flag for if is full displayed
    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }

    /**
     * Sets full displayed.
     *
     * @param fullDisplayed the full displayed
     */
//set full displayed
    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }

    //set a toString method for returning text.
    @Override
    public String toString() {
        return this.text;
    }
}