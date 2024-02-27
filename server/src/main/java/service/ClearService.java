package service;
import dataAccess.Clear;

public class ClearService{
    public void clear(){
        Clear clearAll = new Clear();
        clearAll.clearData();
    }

}
