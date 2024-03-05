package dataAccessTests;


import dataAccess.Clear;
import dataAccess.DataAccessException;
import dataAccess.Game;
import dataAccess.MySQLGameAccess;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CleardataAccessTest extends MySQLGameAccess {
    public CleardataAccessTest() throws DataAccessException {
    }

    @Test
    void clearTest() throws DataAccessException {
        Clear clear = new Clear();
        clear.clearData();
        assertTrue(listGames().isEmpty());
    }
}
