package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Objects;

public class User implements UserInterface{
    private ArrayList<UserData> users;

    public void createUser(String username, String password, String email){
        UserData userDataTemp = new UserData(username,password,email);
        users.add(userDataTemp);
    }
    public UserData getUser(String username)throws DataAccessException{
        int i = 0;
        UserData user = null;
        while(i < users.size()){
            UserData userTemp = users.get(i);
            if(Objects.equals(userTemp.username(), username)){
                user = users.get(i);
            }
            i +=1;

        }
        return user;
    }
    public void clear(){
        users.clear();
    }


}
