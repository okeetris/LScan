package model;

import model.BaseModel;
import model.IBloodGlucoseModel;
import Database.bloodGlucoseRecord.BGReading;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/04/2017.
 */

public class MainMenuModel extends BaseModel implements MainMenuReadWriteModel {
    private IBloodGlucoseModel model;

    public MainMenuModel(IBloodGlucoseModel model){
        this.model=model;
    }


    @Override
    public void addRawData(Calendar c, String data) {
        model.addRawData(c,data);
        notifyObserver();
    }

    @Override
    public void addHistoryReading(Calendar c, double reading) {
        model.addHistoryReading(c,reading);
        notifyObserver();
    }

    @Override
    public void addCurrentReading(Calendar c, double reading) {
        model.addCurrentReading(c,reading);
        notifyObserver();
    }

    @Override
    public BGReading getMostRecentHistoryReading() {
        return model.getMostRecentHistoryReading();
    }

    @Override
    public List<BGReading> getHistoryBetween(Calendar from, Calendar to) {
        return null; //model.getHistoryBetween(from,to);
    }
}
