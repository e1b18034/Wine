package oit.is.beef_good.wine.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.FriendMapper;
import oit.is.beef_good.wine.model.UserMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/friend_home")
public class FriendHomeController {
  @Autowired
  private FriendMapper friendMapper;

  @Autowired
  private UserMapper userMapper;

  @GetMapping("")
  public String page(HttpSession session, ModelMap model) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/friend_home");
    }

    String user_id = new WineAuthentication(session).getUserId();

    List<String> friendList = this.friendMapper.getFriendListById(user_id);
    Map<String, String> friendIdNameMap = new HashMap<>();
    for (int i = 0; i < friendList.size(); i++) {
      String key = friendList.get(i);
      String value = this.userMapper.getUserName(key);

      friendIdNameMap.put(key, value);
    }

    model.addAttribute("friend_map", friendIdNameMap);
    List<String> requestList = this.friendMapper.getFriendRequestListById(user_id);
    model.addAttribute("request_list", requestList);

    return "friend_home.html";
  }

  @GetMapping("/friend_request")
  public String friendRequest(@RequestParam String friend_id, HttpSession session, ModelMap model) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/friend_home");
    }
    String user_id = new WineAuthentication(session).getUserId();
    this.friendMapper.insertFriend(user_id, friend_id);
    this.friendMapper.confirmFriendRequest(friend_id, user_id);
    return "redirect:/friend_home";
  }
}
