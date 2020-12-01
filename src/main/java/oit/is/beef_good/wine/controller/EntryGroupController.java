package oit.is.beef_good.wine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.BelongMapper;
import oit.is.beef_good.wine.model.GroupMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/entry_group")
public class EntryGroupController {
  @Autowired
  private GroupMapper groupMapper;

  @Autowired
  private BelongMapper belongMapper;

  @GetMapping("")
  public String page() {
    if (!WineAuthentication.isAuthenticated()) {
      return WineAuthentication.authenticate("/entry_group");
    }
    return "entry_group.html";
  }

  // テスト用グループ ID: group1, pwd: lec07trial
  @PostMapping("/entry")
  public String entry(@RequestParam String group_id, @RequestParam String group_pwd, ModelMap model) {
    if (group_id.equals("") || group_pwd.equals("")) {
      model.addAttribute("message", "全ての情報を入力してください");
      return "entry_group.html";
    }

    if (this.groupMapper.isExist(group_id) == 0) {
      model.addAttribute("message", "入力したIDのグループは存在しません");
      return "entry_group.html";
    }

    if (!new BCryptPasswordEncoder().matches(group_pwd, this.groupMapper.getGroupPwd(group_id))) {
      model.addAttribute("message", "グループのパスワードが誤っています");
      return "entry_group.html";
    }

    String user_id = WineAuthentication.getLoggedinUserId();
    if (this.belongMapper.isExist(group_id, user_id) == 1) {
      model.addAttribute("message", "既にそのグループに所属しています");
      return "entry_group.html";
    }

    this.belongMapper.insertBelongGroup(group_id, user_id);
    return "group_entered.html";
  }
}
