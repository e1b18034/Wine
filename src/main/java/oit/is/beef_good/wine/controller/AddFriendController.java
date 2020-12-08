package oit.is.beef_good.wine.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.Friend;
import oit.is.beef_good.wine.model.FriendMapper;

@Controller
@RequestMapping("/add_friend")
public class AddFriendController {
  @Autowired
  private FriendMapper friendMapper;

  @GetMapping("/form")
  public String form(HttpSession session, ModelMap model) {
    String _csrf = new BCryptPasswordEncoder().encode(session.getId());
    model.addAttribute("_csrf", _csrf);
    return "add_friend_form.html";
  }

}
