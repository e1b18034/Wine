package oit.is.beef_good.wine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.FriendMapper;

@Controller
@RequestMapping("/friend_home")
public class FriendHomeController {
  @Autowired
  private FriendMapper friendMapper;
  @GetMapping("")
  public String page(ModelMap model) {
    List<String> friendList = this.friendMapper.getFriendListById("user2");
    model.addAttribute("friend_list", friendList);
    return "friend_home.html";
  }
}
