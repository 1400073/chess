package service;

import dataAccess.*;

import model.AuthData;
import model.UserData;

import java.util.Objects;

public class UserService {
   public AuthData register(UserData user) throws DataAccessException {
      MySQLUserAccess userInterface = new MySQLUserAccess();
      UserData checkUser = userInterface.getUser(user.username());
      MySQLAuthAccess authInterface = new MySQLAuthAccess();
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
      MySQLUserAccess userInterface = new MySQLUserAccess();
      UserData checkUser = userInterface.getUser(user.username());

      if(checkUser == null){
         throw new DataAccessException("unauthorized");
      }
      if(!userInterface.verifyUser(user.username(), user.password())){
         throw new DataAccessException("unauthorized");
      }
      MySQLAuthAccess authInterface = new MySQLAuthAccess();
      return authInterface.createAuth(user.username());

   }

   public void logout(String authToken) throws DataAccessException {
      MySQLAuthAccess auth = new MySQLAuthAccess();
      auth.deleteAuth(authToken);

   }

}
