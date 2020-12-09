package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendMapper {
  /**
   * 既にフレンドであるか
   *
   * @param user_id
   * @param friend_id
   * @return
   */
  @Select("SELECT COUNT(*) FROM friend WHERE user_id = #{user_id} AND friend_id = #{friend_id}")
  int isExist(String user_id, String friend_id);

  /**
   * フレンドリクエスト作成
   *
   * @param user_id
   * @param friend_id
   */
  @Insert("INSERT INTO friend (user_id, friend_id, status) VALUES (#{user_id}, #{friend_id}, FALSE)")
  void insertFriendRequest(String user_id, String friend_id);

  /**
   * フレンド一覧取得
   *
   * @param user_id
   * @return
   */
  @Select("SELECT friend_id FROM friend WHERE user_id = #{user_id} AND status = TRUE")
  List<String> getFriendListById(String user_id);

  /**
   * フレンドデータの挿入
   *
   * @param user_id
   * @param friend_id
   */
  @Insert("INSERT INTO friend (user_id, friend_id, status) VALUES(#{user_id}, #{friend_id}, TRUE)")
  void insertFriend(String user_id, String friend_id);

  /**
   * フレンドリクエスト一覧取得
   *
   * @param friend_id
   * @return
   */
  @Select("SELECT user_id FROM friend WHERE friend_id = #{friend_id} AND status = FALSE")
  List<String> getFriendRequestListById(String friend_id);
}
