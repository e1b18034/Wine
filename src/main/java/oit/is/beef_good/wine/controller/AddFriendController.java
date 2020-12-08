package oit.is.beef_good.wine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.Friend;
import oit.is.beef_good.wine.model.FriendMapper;

@Controller
@RequestMapping("/add_friend")
public class AddFriendController {
  @Autowired
  private FriendMapper friendMapper;

  @GetMapping("")
  public String page(ModelMap model) {
    List<Friend> friendList = friendMapper.getAllFriends();
    model.addAttribute("friends", friendList);
    return "add_friend_form.html";
  }

}
