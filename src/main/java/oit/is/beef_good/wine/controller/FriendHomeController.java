package oit.is.beef_good.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/friend_home")
public class FriendHomeController {
  @GetMapping("")
  public String page() {
    return "friend_home.html";
  }
}
