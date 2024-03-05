package dataAccess;

public class Clear {
    public void clearData() throws DataAccessException {
        MySQLAuthAccess auth = new MySQLAuthAccess();
        MySQLUserAccess user = new MySQLUserAccess();
        MySQLGameAccess game = new MySQLGameAccess();

        user.clear();
        auth.clear();
        game.clear();
    }

}
