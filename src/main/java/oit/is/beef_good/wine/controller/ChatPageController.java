package oit.is.beef_good.wine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.User;
import oit.is.beef_good.wine.model.UserMapper;

@Controller
@RequestMapping("/chat_page")
public class ChatPageController {
  @Autowired
  private UserMapper usermapper;

  @GetMapping("")
  public String chatPage(ModelMap model) {
    List<User> users = usermapper.getAllUsers();
    model.addAttribute("users", users);
    return "chat_page.html";
  }
}
