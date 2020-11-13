package oit.is.beef_good.wine.controller;

import javax.management.modelmbean.ModelMBeanOperationInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.beef_good.wine.model.User;
import oit.is.beef_good.wine.model.UserMapper;

@Controller
@RequestMapping("/new_user")
public class NewUserController {
  @Autowired
  private UserMapper userMapper;

  @GetMapping("/form")
  public String form() {
    return "new_user_form.html";
  }

  @PostMapping("/regist")
  public String regist(@RequestParam String user_id, @RequestParam String user_name, @RequestParam String user_pwd_1,
      @RequestParam String user_pwd_2, ModelMap model) {
    if (user_id.equals("") || user_name.equals("") || user_pwd_1.equals("") || user_pwd_2.equals("")) {
      model.addAttribute("message", "全ての情報を入力してください");
      return "new_user_form.html";
    }
    if (user_pwd_1.equals(user_pwd_2) == false) {
      model.addAttribute("message", "パスワードが違います");
      return "new_user_form.html";
    }
    int num_of_record = userMapper.isExist(user_id);
    if (num_of_record != 0) {
      model.addAttribute("message", "ユーザーIDが重複しています");
      return "new_user_form.html";
    }
    User user = new User();
    user.setUser_id(user_id);
    user.setUser_name(user_name);
    user.setCryptedUserPwd(user_pwd_1);

    userMapper.insertUser(user);

    return "user_registered.html";
  }

}
