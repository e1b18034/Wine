package oit.is.beef_good.wine.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.User;
import oit.is.beef_good.wine.model.UserMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/user_home")
public class UserHomeController {
  @Autowired
  private UserMapper userMapper;

  @GetMapping("")
  public String userhome(ModelMap model, HttpSession session) {
    WineAuthentication auth = new WineAuthentication(session);

    if (!auth.isAuthenticated()) {
      return WineAuthentication.authenticate("/user_home");
    }
    String user_id = auth.getUserId();
    String user_name = this.userMapper.getUserName(user_id);

    model.addAttribute("user_name", user_name);
    return "user_home.html";
  }
}
