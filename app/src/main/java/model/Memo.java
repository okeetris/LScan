package model;

/**
 * Created by 788340 on 29/03/2017.
 */

//import packages
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//set class for displaying memo
public class Memo implements Serializable {
    //set variables
    private Date date;
    private String text;
    private boolean fullDisplayed;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    //memo date
    public Memo() {
        this.date = new Date();
    }
    //create memo
    public Memo(long time, String text) {
        this.date = new Date(time);
        this.text = text;
    }
    public String getDate() {
        return dateFormat.format(date);
    }

    //get time
    public long getTime() {
        return date.getTime();
    }
    public void setTime(){}
    //set text
    public void setText(String text) {
        this.text = text;
    }
    //get text
    public String getText() {
        return this.text;
    }

    //get shortened version of text for not full displayed
    public String getShortText() {
        String temp = text.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }
    //set full displayed
    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }
    //set flag for if is full displayed
    public boolean isFullDisplayed() {
        return this.fullDisplayed;
    }
    //set a toString method for returning text.
    @Override
    public String toString() {
        return this.text;
    }
}