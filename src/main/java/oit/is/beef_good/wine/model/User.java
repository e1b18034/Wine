package oit.is.beef_good.wine.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
  private String user_id;
  private String user_name;
  private String user_pwd;

  public String getUser_id() {
    return user_id;
  }

  public String getUser_name() {
    return user_name;
  }

  public String getUser_pwd() {
    return user_pwd;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public void setUser_pwd(String user_pwd) {
    this.user_pwd = user_pwd;
  }

  public void setCryptedUserPwd(String user_pwd) {
    this.user_pwd = new BCryptPasswordEncoder().encode(user_pwd);
  }

}
