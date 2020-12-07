package oit.is.beef_good.wine.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.UserMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/login_user")
public class LoginUserController {
  @Autowired
  private UserMapper userMapper;

  private String request = "";

  @GetMapping("")
  public String page(@RequestParam String request, ModelMap model, HttpSession session) {
    if (new WineAuthentication(session).isAuthenticated()) {
      return "redirect:" + request;
    }

    this.request = request;

    String _csrf = new BCryptPasswordEncoder().encode(session.getId());
    model.addAttribute("_csrf", _csrf);

    return "login_user_form.html";
  }

  @PostMapping("/login")
  public String login(@RequestParam String user_id, @RequestParam String user_pwd, @RequestParam String _csrf,
      ModelMap model, HttpSession session) {
    if (!new BCryptPasswordEncoder().matches(session.getId(), _csrf)) {
      _csrf = new BCryptPasswordEncoder().encode(session.getId());
      model.addAttribute("_csrf", _csrf);
      model.addAttribute("message", "フォームのエラーです");
      return "login_user_form.html";
    }

    if (user_id.equals("") || user_pwd.equals("")) {
      model.addAttribute("message", "全ての情報を入力してください");
      model.addAttribute("_csrf", _csrf);
      return "login_user_form.html";
    }

    if (!new WineAuthentication(session).authenticateUser(this.userMapper, user_id, user_pwd)) {
      model.addAttribute("message", "ユーザ情報が間違っています");
      model.addAttribute("_csrf", _csrf);
      return "login_user_form.html";
    }

    if (this.request.equals("")) {
      return "redirect:/";
    }
    return "redirect:" + this.request;
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    new WineAuthentication(session).logout();
    this.request = "";
    return "redirect:/";
  }
}
