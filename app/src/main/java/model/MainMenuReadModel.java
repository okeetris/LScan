package model;

import Database.bloodGlucoseRecord.BGReading;
import model.BaseReadModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/04/2017.
 */

public interface MainMenuReadModel extends BaseReadModel {
    List<BGReading> getHistoryBetween(Calendar from, Calendar to);
}
