package service;
import dataAccess.Auth;
import dataAccess.DataAccessException;
import dataAccess.Game;
import model.AuthData;
import model.GameData;
import dataAccess.clear;

public class ClearService{
    public void clear(){
        clear clearAll = new clear();
        clearAll.clearData();
    }

}
