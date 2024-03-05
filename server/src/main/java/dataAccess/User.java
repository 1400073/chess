package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Objects;

public class User{
    private final static ArrayList<UserData> users = new ArrayList<>();

    public void createUser(String username, String password, String email){
        UserData userDataTemp = new UserData(username,password,email);
        users.add(userDataTemp);
    }
    public UserData getUser(String username)throws DataAccessException{
        int i = 0;
        UserData user = null;
        if(!users.isEmpty()) {
            while (i < users.size()) {
                UserData userTemp = users.get(i);
                if (Objects.equals(userTemp.username(), username)) {
                    user = users.get(i);
                }
                i += 1;

            }
        }


        return user;
    }
    public void clear(){
        if(!users.isEmpty()) {
            users.clear();
        }
    }


}
