package oit.is.beef_good.wine.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/group_home")
public class GroupHomeController {
  @Autowired
  private BelongMapper belongMapper;

  @GetMapping("")
  public String page(ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/group_home");
    }

    String user_id = new WineAuthentication(session).getUserId();
    List<String> groups = belongMapper.getBelongingGroupId(user_id);

    model.addAttribute("group_list", groups);
    return "group_home.html";
  }
}
