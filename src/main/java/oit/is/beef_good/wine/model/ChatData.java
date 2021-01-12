package oit.is.beef_good.wine.model;

public class ChatData implements Cloneable {
  private int id;
  private String sender;
  private String receiver;
  private String date_time;
  private int data_type;
  private String chat_data;

  public static final int TYPE_TEXT = 1;
  public static final int TYPE_STAMP = 2;
  public static final int TYPE_PHOTO = 3;

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

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ChatData) {
      ChatData tmp = (ChatData) obj;
      if (this.id == tmp.id && this.sender.equals(tmp.sender) && this.receiver.equals(tmp.receiver)
          && this.date_time.equals(tmp.date_time) && this.data_type == tmp.data_type
          && this.chat_data.equals(tmp.chat_data)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public ChatData clone() throws CloneNotSupportedException {
    ChatData chatdata = new ChatData();
    chatdata.id = this.id;
    chatdata.sender = this.sender;
    chatdata.receiver = this.receiver;
    chatdata.date_time = this.date_time;
    chatdata.data_type = this.data_type;
    chatdata.chat_data = this.chat_data;

    return chatdata;
  }
}
