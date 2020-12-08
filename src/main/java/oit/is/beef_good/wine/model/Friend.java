package oit.is.beef_good.wine.model;

public class Friend {

  private String user_id;
  private String friend_id;
  private boolean status;

  public String getUser_id() {
    return user_id;
  }

  public String getFriend_id() {
    return friend_id;
  }

  public boolean getStatus() {
    return status;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public void setFriend_id(String friend_id) {
    this.friend_id = friend_id;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
