package dataAccess;

public class clear {
    public void clearData(){
        User user = new User();
        user.clear();
        Auth auth = new Auth();
        auth.clear();
        Game game = new Game();
        game.clear();
    }

}
