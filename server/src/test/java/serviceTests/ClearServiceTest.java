package serviceTests;

import dataAccess.Clear;
import dataAccess.DataAccessException;
import dataAccess.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest extends Game {

    @Test
    void clearTest() throws DataAccessException {
        Clear clear = new Clear();
        clear.clearData();
        assertTrue(listGames().isEmpty());
    }
}