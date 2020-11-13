package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
  @Select("SELECT * from user")
  List<User> getAllUsers();

  @Select("SELECT count(*) from user where user_id = #{user_id}")
  int isExist(String user_id);

  @Insert("INSERT INTO user (user_id,user_name,user_pwd) VALUES (#{user_id},#{user_name},#{user_pwd});")
  int insertUser(User user);
}
