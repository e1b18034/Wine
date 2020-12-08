package oit.is.beef_good.wine.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendMapper {
  @Select("SELECT COUNT(*) FROM friend WHERE user_id = #{user_id} AND friend_id = #{friend_id}")
  int isExist(String user_id, String friend_id);

  @Insert("INSERT INTO friend (user_id, friend_id, status) VALUES (#{user_id}, #{friend_id}, FALSE)")
  void insertFriendRequest(String user_id, String friend_id);
}
