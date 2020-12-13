package oit.is.beef_good.wine.service;

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

  @Async
  public void error(SseEmitter emitter) {
    emitter.complete();
  }
}
