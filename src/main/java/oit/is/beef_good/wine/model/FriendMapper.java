package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendMapper {

  @Select("SELECT * FROM FRIEND")
  List<Friend> getAllFriends();

}
