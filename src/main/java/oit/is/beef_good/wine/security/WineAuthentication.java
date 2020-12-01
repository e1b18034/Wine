package oit.is.beef_good.wine.security;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import oit.is.beef_good.wine.model.User;
import oit.is.beef_good.wine.model.UserMapper;

public class WineAuthentication {
  private UserMapper userMapper;
  private static String loggedinUserId = null;

  public WineAuthentication(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public static boolean isAuthenticated() {
    if (loggedinUserId == null) {
      return false;
    }
    return true;
  }

  public boolean authenticateUser(String user_id, String user_pwd) {
    if (isAuthenticated()) {
      return true;
    }

    List<User> users = userMapper.getAllUsers();
    for (User user : users) {
      if (user_id.equals(user.getUser_id())) {
        if (new BCryptPasswordEncoder().matches(user_pwd, user.getUser_pwd())) {
          loggedinUserId = user_id;
          return true;
        }
        break;
      }
    }

    return false;
  }

  public static void logout() {
    WineAuthentication.loggedinUserId = null;
  }

  public static String getLoggedinUserId() {
    return loggedinUserId;
  }

  public static String authenticate(String authenticatedRequest) {
    return "redirect:/login_user?request=" + authenticatedRequest;
  }
}
