package model;

/**
 * Created by kbb12 on 03/04/2017.
 */

public class BaseModel implements BaseReadWriteModel {

    private String errorMessage;
    private ModelObserver observer;

    public void registerObserver(ModelObserver observer){
        this.observer=observer;
    }

    protected void notifyObserver(){
        if(observer!=null) {
            observer.update();
        }
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public void setError(String errorMessage) {
        this.errorMessage=errorMessage;
        notifyObserver();
    }
}
