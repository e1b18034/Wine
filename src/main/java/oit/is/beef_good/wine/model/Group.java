package oit.is.beef_good.wine.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Group {
  private String group_id;
  private String group_name;
  private String group_pwd;

  public String getGroup_id() {
    return group_id;
  }

  public String getGroup_name() {
    return group_name;
  }

  public String getGroup_pwd() {
    return group_pwd;
  }

  public void setGroup_id(String group_id) {
    this.group_id = group_id;
  }

  public void setGroup_name(String group_name) {
    this.group_name = group_name;
  }

  public void setGroup_pwd(String group_pwd) {
    this.group_pwd = group_pwd;
  }

  public void setCryptedGroupPwd(String group_pwd) {
    this.group_pwd = new BCryptPasswordEncoder().encode(group_pwd);
  }
}
