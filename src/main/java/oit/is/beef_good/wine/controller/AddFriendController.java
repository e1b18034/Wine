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

import oit.is.beef_good.wine.model.FriendMapper;
import oit.is.beef_good.wine.model.UserMapper;
import oit.is.beef_good.wine.security.WineAuthentication;

@Controller
@RequestMapping("/add_friend")
public class AddFriendController {
  @Autowired
  private FriendMapper friendMapper;

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/form")
  public String form(HttpSession session, ModelMap model) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/add_friend/form");
    }

    String _csrf = new BCryptPasswordEncoder().encode(session.getId());
    model.addAttribute("_csrf", _csrf);
    return "add_friend_form.html";
  }

  @PostMapping("/add")
  public String addFriend(@RequestParam String friend_id, @RequestParam String _csrf, HttpSession session,
      ModelMap model) {
    if (!new WineAuthentication(session).isAuthenticated()) {
      return WineAuthentication.authenticate("/add_friend/form");
    }

    // フォームエラーの確認
    if (!new BCryptPasswordEncoder().matches(session.getId(), _csrf)) {
      model.addAttribute("message", "フォームのエラーです");
      model.addAttribute("_csrf", new BCryptPasswordEncoder().encode(session.getId()));
      return "add_friend_form.html";
    }

    // 入力情報の不足確認
    if (friend_id.equals("") || _csrf.equals("")) {
      model.addAttribute("message", "全ての情報を入力してください");
      model.addAttribute("_csrf", _csrf);
      return "add_friend_form.html";
    }

    // 存在しないユーザ
    if (this.userMapper.isExist(friend_id) == 0) {
      model.addAttribute("message", "そのフレンドIDは存在しません");
      model.addAttribute("_csrf", _csrf);
      return "add_friend_form.html";
    }

    // ユーザID取得
    String user_id = new WineAuthentication(session).getUserId();

    // 自分自身を指定
    if (friend_id.equals(user_id)) {
      model.addAttribute("message", "自分自身とはフレンドになれません");
      model.addAttribute("_csrf", _csrf);
      return "add_friend_form.html";
    }

    // リクエスト受信済みであるか
    if (this.friendMapper.isExistRequest(friend_id, user_id) == 1) {
      this.friendMapper.insertFriend(user_id, friend_id);
      this.friendMapper.confirmFriendRequest(friend_id, user_id);

      // 登録完了画面への遷移
      return "friend_registered.html";
    }

    // 既に登録済みかチェック
    if (this.friendMapper.isExist(user_id, friend_id) == 1) {
      model.addAttribute("message", "既にフレンドです");
      model.addAttribute("_csrf", _csrf);
      return "add_friend_form.html";
    }

    // 登録リクエスト
    this.friendMapper.insertFriendRequest(user_id, friend_id);

    // 登録完了画面への遷移
    return "friend_registered.html";
  }

}
