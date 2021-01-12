package oit.is.beef_good.wine.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.model.GroupMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/group_home")
public class GroupHomeController {
  @Autowired
  private BelongMapper belongMapper;

  @Autowired
  private GroupMapper groupMapper;

  @GetMapping("")
  public String page(ModelMap model, HttpSession session) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/group_home");
    }

    String user_id = new WineAuthentication(session).getUserId();
    List<String> groups = belongMapper.getBelongingGroupId(user_id);
    Map<String, String> groupIdNameMap = new HashMap<>();
    for (int i = 0; i < groups.size(); i++) {
      String key = groups.get(i);
      String value = this.groupMapper.getGroupName(key);

      groupIdNameMap.put(key, value);
    }

    model.addAttribute("group_map", groupIdNameMap);
    return "group_home.html";
  }
}
