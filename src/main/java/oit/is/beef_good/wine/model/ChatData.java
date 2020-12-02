package oit.is.beef_good.wine.model;

public class ChatData {
  private String user_id;
  private String message;

  public ChatData(String user_id, String message) {
    this.user_id = user_id;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getUser_id() {
    return user_id;
  }
}
