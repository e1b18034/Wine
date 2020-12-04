package oit.is.beef_good.wine.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import oit.is.beef_good.wine.model.User;
import oit.is.beef_good.wine.model.UserMapper;

public class WineAuthentication {
  private static Map<String, Map<String, String>> sessionMap = new HashMap<>();
  private String sessionId;

  /**
   * コンストラクタ
   *
   * @param httpSession
   */
  public WineAuthentication(HttpSession httpSession) {
    this.sessionId = httpSession.getId();
  }

  /**
   * 認証済みならtrue，そうでない場合falseを返す
   *
   * @return
   */
  public boolean isAuthenticated() {
    /**
     * セッションIDからセッション変数取得． nullならfalseを返す
     */
    Map<String, String> session = WineAuthentication.sessionMap.get(this.sessionId);
    if (session == null) {
      return false;
    }

    /**
     * セッション変数からキー値「login」に対応する値を取得． null或いは空文字列ならfalseを返す
     */
    if (session.get("login") == null || ((String) (session.get("login"))).equals("")) {
      return false;
    }

    return true;
  }

  public boolean authenticateUser(UserMapper userMapper, String user_id, String user_pwd) {
    /**
     * 認証済みならtrueを返す
     */
    if (this.isAuthenticated()) {
      return true;
    }

    /**
     * 全ユーザ情報から入力ユーザID・パスワードに一致するユーザがある場合認証する．
     */
    List<User> users = userMapper.getAllUsers();
    for (User user : users) {
      if (user_id.equals(user.getUser_id())) {
        if (new BCryptPasswordEncoder().matches(user_pwd, user.getUser_pwd())) {
          Map<String, String> session = new HashMap<>();
          session.put("login", user_id);
          WineAuthentication.sessionMap.put(this.sessionId, session);
          return true;
        }
        break;
      }
    }

    return false;
  }

  /**
   * ログアウト処理
   */
  public void logout() {
    WineAuthentication.sessionMap.remove(this.sessionId);
  }

  /**
   * セッション変数への値セット(keyに「login」を指定することはできない)．例外発生時：false，成功時：true
   *
   * @param key
   * @param value
   * @return
   */
  public boolean setSession(String key, String value) {
    try {
      if (key.equals("login")) {
        return false;
      }
      WineAuthentication.sessionMap.get(this.sessionId).put(key, value);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  /**
   * セッション変数からの値取得．例外発生時：null，成功時：キー値に対応する値
   *
   * @param key
   * @return
   */
  public String getSession(String key) {
    try {
      return WineAuthentication.sessionMap.get(this.sessionId).get(key);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * ユーザID取得．例外発生時：null，成功時：キー値に対応する値
   *
   * @param key
   * @return
   */
  public String getUserId() {
    try {
      return WineAuthentication.sessionMap.get(this.sessionId).get("login");
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 認証リクエストへのリダイレクト作成メソッド
   *
   * @param authenticatedRequest
   * @return
   */
  public static String authenticate(String authenticatedRequest) {
    return "redirect:/login_user?request=" + authenticatedRequest;
  }
}
