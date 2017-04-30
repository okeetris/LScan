package model;

import Database.bloodGlucoseRecord.BGReading;

import java.util.Calendar;


public interface MainMenuReadWriteModel extends BaseReadWriteModel, MainMenuReadModel {
    void addRawData(Calendar c, String data);
    void addHistoryReading(Calendar c, double reading);
    void addCurrentReading(Calendar c, double reading);
    BGReading getMostRecentHistoryReading();
}
