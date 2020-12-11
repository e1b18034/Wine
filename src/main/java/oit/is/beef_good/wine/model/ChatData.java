package oit.is.beef_good.wine.model;

public class ChatData {
  private int id;
  private String sender;
  private String receiver;
  private String date_time;
  private int data_type;
  private String chat_data;

  public int getId() {
    return id;
  }

  public String getSender() {
    return sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getDate_time() {
    return date_time;
  }

  public int getData_type() {
    return data_type;
  }

  public String getChat_data() {
    return chat_data;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public void setDate_time(String date_time) {
    this.date_time = date_time;
  }

  public void setData_type(int data_type) {
    this.data_type = data_type;
  }

  public void setChat_data(String chat_data) {
    this.chat_data = chat_data;
  }
}
