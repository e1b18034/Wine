package oit.is.beef_good.wine.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.beef_good.wine.model.Chat;
import oit.is.beef_good.wine.model.ChatData;

@Service
public class AsyncChat {
  @Autowired
  private Chat chat;

  private final Logger logger = LoggerFactory.getLogger(AsyncChat.class);

  @Transactional
  private List<ChatData> getChatList(String group_id) {
    return this.chat.getAllChatData(group_id);
  }

  @Async
  public void update(SseEmitter emitter, String group_id) {
    List<ChatData> chatList = this.getChatList(group_id);

    try {
      emitter.send(chatList);
      logger.warn("send data length: " + chatList.size());
    } catch (Exception e) {
      logger.warn("send exception: " + e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }
}
