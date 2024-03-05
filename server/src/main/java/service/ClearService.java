package service;
import dataAccess.Clear;
import dataAccess.DataAccessException;

public class ClearService{
    public void clear() throws DataAccessException {
        Clear clearAll = new Clear();
        clearAll.clearData();
    }

}
