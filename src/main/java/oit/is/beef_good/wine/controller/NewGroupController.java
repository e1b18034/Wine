package oit.is.beef_good.wine.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.Group;
import oit.is.beef_good.wine.model.GroupMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/new_group")
public class NewGroupController {
  @Autowired
  private GroupMapper groupMapper;

  @GetMapping("")
  public String page(HttpSession httpSession) {
    if (!new WineAuthentication(httpSession).isAuthenticated()) {
      return WineAuthentication.authenticate("/new_group");
    }
    return "new_group_page.html";
  }

  @PostMapping("/regist")
  public String regist(@RequestParam String group_id, @RequestParam String group_name, @RequestParam String group_pwd_1,
      @RequestParam String group_pwd_2, ModelMap model, HttpSession httpSession) {
    if (!new WineAuthentication(httpSession).isAuthenticated()) {
      return WineAuthentication.authenticate("/new_group");
    }

    if (group_id.equals("") || group_name.equals("") || group_pwd_1.equals("") || group_pwd_2.equals("")) {
      model.addAttribute("message", "全ての情報を入力してください");
      return "new_group_page.html";
    }
    if (group_pwd_1.equals(group_pwd_2) == false) {
      model.addAttribute("message", "パスワードが一致しません");
      return "new_group_page.html";
    }

    if (groupMapper.isExist(group_id) != 0) {
      model.addAttribute("message", "グループIDが重複しています");
      return "new_group_page.html";
    }
    Group group = new Group();
    group.setGroup_id(group_id);
    group.setGroup_name(group_name);
    group.setCryptedGroupPwd(group_pwd_1);

    groupMapper.insertGroup(group);
    return "group_registered.html";
  }
}
