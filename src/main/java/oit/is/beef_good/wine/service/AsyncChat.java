package oit.is.beef_good.wine.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.beef_good.wine.model.ChatData;
import oit.is.beef_good.wine.model.FriendChatMapper;
import oit.is.beef_good.wine.model.GroupChatMapper;

@Service
public class AsyncChat {
  @Autowired
  private FriendChatMapper friendChatMapper;

  @Autowired
  private GroupChatMapper groupChatMapper;

  private final Logger logger = LoggerFactory.getLogger(AsyncChat.class);

  @Transactional
  public List<ChatData> getFriendChatList(String user_id, String friend_id) {
    return this.friendChatMapper.getChatData(user_id, friend_id);
  }

  @Async
  public void updateFriendChat(SseEmitter emitter, String user_id, String friend_id) {
    List<ChatData> chatList = this.getFriendChatList(user_id, friend_id);

    try {
      emitter.send(SseEmitter.event().data(chatList));
      logger.warn("send data length: " + chatList.size());
    } catch (Exception e) {
      logger.warn("send exception: " + e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

  @Transactional
  public void insertFriendChat(ChatData chatData) {
    this.friendChatMapper.insertChatData(chatData);
  }

  @Async
  public void sendFriendChat(String user_id, String friend_id, int data_type, String chat_data) {
    String date_time = LocalDateTime.now().toString();

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
    List<ChatData> chatList = this.getGroupChatList(user_id, group_id);

    try {
      emitter.send(SseEmitter.event().data(chatList));
      logger.warn("send data length: " + chatList.size());
    } catch (Exception e) {
      logger.warn("send exception: " + e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

  @Transactional
  public void insertGroupChat(ChatData chatData) {
    this.groupChatMapper.insertChatData(chatData);
  }

  @Async
  public void sendGroupChat(String user_id, String group_id, int data_type, String chat_data) {
    String date_time = LocalDateTime.now().toString();

    ChatData chatData = new ChatData();
    chatData.setSender(user_id);
    chatData.setReceiver(group_id);
    chatData.setDate_time(date_time);
    chatData.setData_type(data_type);
    chatData.setChat_data(chat_data);

    this.insertGroupChat(chatData);
  }

  @Async
  public void error(SseEmitter emitter) {
    emitter.complete();
  }
}
