package oit.is.beef_good.wine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Chat {
  private Map<String, List<ChatData>> chatListMap = new HashMap<>();

  public List<ChatData> getAllChatData(String group_id) {
    if (chatListMap.get(group_id) == null) {
      chatListMap.put(group_id, new ArrayList<>());
    }

    return chatListMap.get(group_id);
  }

  public void addChatData(String user_id, String message, String group_id) {
  }
}
