package oit.is.beef_good.wine.controller;

import java.util.List;

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

  @GetMapping("")
  public String chatPage(ModelMap model) {
    if (!WineAuthentication.isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page");
    }

    String user_id = WineAuthentication.getLoggedinUserId();
    List<String> groupList = belongMapper.getBelongingGroupId(user_id);
    model.addAttribute("group_list", groupList);

    List<ChatData> chatList = chat.getAllChatData();
    model.addAttribute("chat_list", chatList);

    return "chat_page.html";
  }

  @PostMapping("/send")
  public String sendMessage(@RequestParam String chat, ModelMap model) {
    if (!WineAuthentication.isAuthenticated()) {
      return WineAuthentication.authenticate("/chat_page");
    }

    String user_id = WineAuthentication.getLoggedinUserId();
    this.chat.addChatData(user_id, chat);

    List<String> groupList = belongMapper.getBelongingGroupId(user_id);
    model.addAttribute("group_list", groupList);

    List<ChatData> chatList = this.chat.getAllChatData();
    model.addAttribute("chat_list", chatList);

    return "chat_page.html";
  }
}
