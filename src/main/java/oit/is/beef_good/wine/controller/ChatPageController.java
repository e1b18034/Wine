package oit.is.beef_good.wine.controller;

import java.io.File;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.model.ChatData;
import oit.is.beef_good.wine.model.FriendMapper;
import oit.is.beef_good.wine.security.WineAuthentication;
import oit.is.beef_good.wine.service.AsyncChat;

@Controller
@RequestMapping("/chat_page")
public class ChatPageController {
  @Autowired
  private BelongMapper belongMapper;

  @Autowired
  private AsyncChat asyncChat;

  @Autowired
  private FriendMapper friendMapper;

  @GetMapping("/group_chat")
  public String groupChat(@RequestParam String receiver, ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page/group_chat?receiver=" + receiver);
    }

    model.addAttribute("receiver", receiver);
    model.addAttribute("chat_type", "/group_chat");
    model.addAttribute("chat_home", "/group_home");

    return "chat_page.html";
  }

  @GetMapping("/group_chat/update")
  public SseEmitter groupChatUpdate(@RequestParam String receiver, HttpSession session) {
    WineAuthentication auth = new WineAuthentication(session);
    final SseEmitter emitter = new SseEmitter();

    if (auth.isAuthenticated()) {
      String user_id = auth.getUserId();
      if (belongMapper.isExist(receiver, user_id) == 1) {
        asyncChat.updateGroupChat(emitter, user_id, receiver);
      } else {
        asyncChat.error(emitter);
      }
    }

    return emitter;
  }

  @PostMapping("/group_chat/send")
  public SseEmitter sendGroupChat(@RequestParam String chat_data, @RequestParam Integer data_type,
      @RequestParam String receiver, ModelMap model, HttpSession session) {
    final SseEmitter emitter = new SseEmitter();
    WineAuthentication auth = new WineAuthentication(session);

    if (auth.isAuthenticated()) {
      String user_id = auth.getUserId();
      if (belongMapper.isExist(receiver, user_id) == 1) {
        this.asyncChat.sendGroupChat(user_id, receiver, data_type, chat_data);
      }
    }

    return emitter;
  }

  @GetMapping("/friend_chat")
  public String friendChat(@RequestParam String receiver, HttpSession session, ModelMap model) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page/friend_chat?receiver=" + receiver);
    }

    model.addAttribute("receiver", receiver);
    model.addAttribute("chat_type", "/friend_chat");
    model.addAttribute("chat_home", "/friend_home");

    return "chat_page.html";
  }

  @GetMapping("/friend_chat/update")
  public SseEmitter friendChatUpdate(@RequestParam String receiver, HttpSession session) {
    final SseEmitter emitter = new SseEmitter();

    WineAuthentication auth = new WineAuthentication(session);
    if (auth.isAuthenticated()) {
      String user_id = auth.getUserId();
      if (this.friendMapper.isExist(user_id, receiver) == 1) {
        this.asyncChat.updateFriendChat(emitter, user_id, receiver);
      } else {
        this.asyncChat.error(emitter);
      }
    }

    return emitter;
  }

  @PostMapping("/friend_chat/send")
  public SseEmitter sendFriendChat(@RequestParam String chat_data, @RequestParam Integer data_type,
      @RequestParam String receiver, ModelMap model, HttpSession session) {
    final SseEmitter emitter = new SseEmitter();
    WineAuthentication auth = new WineAuthentication(session);

    if (auth.isAuthenticated()) {
      String user_id = auth.getUserId();
      if (friendMapper.isExist(user_id, receiver) == 1) {
        this.asyncChat.sendFriendChat(user_id, receiver, data_type, chat_data);
      }
    }

    return emitter;
  }

  @GetMapping("stamp_list")
  public SseEmitter getStampList(HttpSession session) {
    final SseEmitter emitter = new SseEmitter();
    WineAuthentication auth = new WineAuthentication(session);

    if (auth.isAuthenticated()) {
      asyncChat.asyncGetStampList(emitter);
    }

    return emitter;
  }

  @GetMapping("/group_chat/member")
  public SseEmitter getGroupMember(@RequestParam String group_id, HttpSession session) {
    final SseEmitter emitter = new SseEmitter();
    WineAuthentication auth = new WineAuthentication(session);

    if (auth.isAuthenticated()) {
      String user_id = auth.getUserId();
      if (this.belongMapper.isExist(group_id, user_id) == 1) {
        this.asyncChat.asyncGetGroupMember(emitter, group_id);
      }
    }

    return emitter;
  }
}
