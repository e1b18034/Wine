package oit.is.beef_good.wine.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.model.ChatData;
import oit.is.beef_good.wine.model.FriendChatMapper;
import oit.is.beef_good.wine.model.GroupChatMapper;
import oit.is.beef_good.wine.model.UserMapper;

@Service
public class AsyncChat {
  @Autowired
  private FriendChatMapper friendChatMapper;

  @Autowired
  private GroupChatMapper groupChatMapper;

  @Autowired
  private BelongMapper belongMapper;

  @Autowired
  private UserMapper userMapper;

  private final Logger logger = LoggerFactory.getLogger(AsyncChat.class);

  @Transactional
  public List<ChatData> getFriendChatList(String user_id, String friend_id) {
    return this.friendChatMapper.getChatData(user_id, friend_id);
  }

  @Async
  public void updateFriendChat(SseEmitter emitter, String user_id, String friend_id) {
    List<ChatData> beforeChatList = null;

    while (true) {
      List<ChatData> chatList = this.getFriendChatList(user_id, friend_id);
      if (!equals(beforeChatList, chatList)) {
        beforeChatList = chatList;
        try {
          List<ChatData> result = cloneChatList(chatList);
          convertSender(result);

          emitter.send(SseEmitter.event().data(result));
          logger.warn("send data length: " + result.size());
        } catch (Exception e) {
          logger.warn("send exception: " + e.getClass().getName() + ": " + e.getMessage());
          emitter.complete();
          break;
        }
      }
    }
  }

  @Transactional
  public void insertFriendChat(ChatData chatData) {
    this.friendChatMapper.insertChatData(chatData);
  }

  @Async
  public void sendFriendChat(String user_id, String friend_id, int data_type, String chat_data) {
    String date_time = getDateTime();

    ChatData chatData = new ChatData();
    chatData.setSender(user_id);
    chatData.setReceiver(friend_id);
    chatData.setDate_time(date_time);
    chatData.setData_type(data_type);
    chatData.setChat_data(chat_data);

    this.insertFriendChat(chatData);
  }

  @Transactional
  public List<ChatData> getGroupChatList(String user_id, String group_id) {
    return this.groupChatMapper.getChatData(group_id);
  }

  @Async
  public void updateGroupChat(SseEmitter emitter, String user_id, String group_id) {
    List<ChatData> beforeChatList = null;

    while (true) {
      List<ChatData> chatList = this.getGroupChatList(user_id, group_id);
      if (!equals(beforeChatList, chatList)) {
        beforeChatList = chatList;
        try {
          List<ChatData> result = cloneChatList(chatList);
          convertSender(result);

          emitter.send(SseEmitter.event().data(result));
          logger.warn("send data length: " + result.size());
        } catch (Exception e) {
          logger.warn("send exception: " + e.getClass().getName() + ": " + e.getMessage());
          emitter.complete();
          break;
        }
      }
    }
  }

  @Transactional
  public void insertGroupChat(ChatData chatData) {
    this.groupChatMapper.insertChatData(chatData);
  }

  @Async
  public void sendGroupChat(String user_id, String group_id, int data_type, String chat_data) {
    String date_time = getDateTime();

    ChatData chatData = new ChatData();
    chatData.setSender(user_id);
    chatData.setReceiver(group_id);
    chatData.setDate_time(date_time);
    chatData.setData_type(data_type);
    chatData.setChat_data(chat_data);

    this.insertGroupChat(chatData);
  }

  @Transactional
  public List<String> getStampNameList() {
    File dir = new File("./src/main/resources/static/img/stamp/");
    File[] list = dir.listFiles();

    List<String> stampNameList = new ArrayList<>();
    for (File file : list) {
      stampNameList.add(file.getName());
    }

    return stampNameList;
  }

  @Async
  public void asyncGetStampList(SseEmitter emitter) {
    List<String> stampNameList = this.getStampNameList();

    try {
      emitter.send(SseEmitter.event().data(stampNameList));
    } catch (Exception e) {
      logger.warn(e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

  @Transactional
  public List<String> getGroupMemberList(String group_id) {
    return this.belongMapper.getUserIdByGroupId(group_id);
  }

  @Async
  public void asyncGetGroupMember(SseEmitter emitter, String group_id) {
    List<String> memberList = this.getGroupMemberList(group_id);

    try {
      emitter.send(SseEmitter.event().data(memberList));
    } catch (Exception e) {
      logger.warn(e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

  @Async
  public void error(SseEmitter emitter) {
    emitter.complete();
  }

  @Transactional
  public static boolean equals(List<ChatData> obj1, List<ChatData> obj2) {
    if (obj1 == null || obj2 == null) {
      return false;
    }

    if (obj1.size() != obj2.size()) {
      return false;
    }

    for (int i = 0; i < obj1.size() && i < obj2.size(); i++) {
      if (!obj1.get(i).equals(obj2.get(i))) {
        return false;
      }
    }

    return true;
  }

  @Transactional
  public String getDateTime() {
    Date date = new Date();
    DateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    String dateTime = dateTimeFormat.format(date);

    return dateTime;
  }

  @Transactional
  public List<ChatData> cloneChatList(List<ChatData> chatData) {
    ArrayList<ChatData> result = new ArrayList<>();
    for (int i = 0; i < chatData.size(); i++) {
      try {
        result.add(chatData.get(i).clone());
      } catch (Exception e) {
        logger.error(e.getClass().getName() + ":" + e.getMessage());
      }
    }
    return result;
  }

  @Transactional
  public void convertSender(List<ChatData> senderChat) {
    for (int i = 0; i < senderChat.size(); i++) {
      String user_id = senderChat.get(i).getSender();
      String user_name = userMapper.getUserName(user_id);
      senderChat.get(i).setSender(user_name);
    }
  }
}
