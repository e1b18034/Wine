package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM user")
  List<User> getAllUsers();

  @Select("SELECT count(*) FROM user WHERE user_id = #{user_id}")
  int isExist(String user_id);

  @Select("SELECT user_name FROM user WHERE user_id = #{user_id}")
  String getUserName(String user_id);

  @Insert("INSERT INTO user (user_id,user_name,user_pwd) VALUES (#{user_id},#{user_name},#{user_pwd});")
  int insertUser(User user);
}
