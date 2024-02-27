package service;

import dataAccess.*;

import model.AuthData;
import model.UserData;

import java.util.Objects;

public class UserService {
   public AuthData register(UserData user) throws DataAccessException {
      User userInterface = new User();
      UserData checkUser = userInterface.getUser(user.username());
      Auth authInterface = new Auth();
      if(checkUser == null){
         if(user.password()!= null) {
            userInterface.createUser(user.username(), user.password(), user.email());
         }
         else{
            throw new DataAccessException("bad request");
         }
      }
      else{
         throw new DataAccessException("already taken");
      }
      return authInterface.createAuth(user.username());
   }
   public AuthData login(UserData user) throws DataAccessException {
      User userInterface = new User();
      UserData checkUser = userInterface.getUser(user.username());

      if(checkUser == null){
         throw new DataAccessException("unauthorized");
      }
      if(!Objects.equals(user.password(), checkUser.password())){
         throw new DataAccessException("unauthorized");
      }
      Auth authInterface = new Auth();
      return authInterface.createAuth(user.username());

   }

   public void logout(String authToken) throws DataAccessException {
      Auth auth = new Auth();
      auth.deleteAuth(authToken);

   }

}
