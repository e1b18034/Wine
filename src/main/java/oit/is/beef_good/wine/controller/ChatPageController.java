package oit.is.beef_good.wine.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.model.Chat;
import oit.is.beef_good.wine.model.ChatData;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/chat_page")
public class ChatPageController {
  @Autowired
  private Chat chat;

  @Autowired
  private BelongMapper belongMapper;

  private void commonProcess(ModelMap model, HttpSession session) {
    String user_id = new WineAuthentication(session).getUserId();
    List<String> groupList = belongMapper.getBelongingGroupId(user_id);
    model.addAttribute("group_list", groupList);

    List<ChatData> chatList = chat.getAllChatData();
    model.addAttribute("chat_list", chatList);
  }

  @GetMapping("")
  public String chatPage(ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page");
    }

    this.commonProcess(model, session);

    return "chat_page.html";
  }

  @GetMapping("/group_chat")
  public String groupChat(@RequestParam String group_id, ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page");
    }

    this.commonProcess(model, session);

    return "chat_page.html";
  }

  @PostMapping("/send")
  public String sendMessage(@RequestParam String chat, ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page");
    }

    String user_id = new WineAuthentication(session).getUserId();
    this.chat.addChatData(user_id, chat);

    this.commonProcess(model, session);

    return "chat_page.html";
  }
}
