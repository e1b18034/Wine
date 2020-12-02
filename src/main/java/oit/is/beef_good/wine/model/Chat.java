package oit.is.beef_good.wine.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Chat {
  private List<ChatData> chatList = new ArrayList<>();

  public List<ChatData> getAllChatData() {
    return chatList;
  }

  public void addChatData(String user_id, String message) {
    ChatData chatData = new ChatData(user_id, message);
    chatList.add(chatData);
  }
}
