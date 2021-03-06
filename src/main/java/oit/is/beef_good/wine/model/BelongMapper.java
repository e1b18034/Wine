package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BelongMapper {
  @Select("SELECT COUNT(*) FROM belong WHERE group_id = #{group_id} AND user_id = #{user_id}")
  int isExist(String group_id, String user_id);

  @Select("SELECT group_id FROM belong WHERE user_id = #{user_id}")
  List<String> getBelongingGroupId(String user_id);

  @Insert("INSERT INTO belong (group_id, user_id) VALUES (#{group_id}, #{user_id})")
  void insertBelongGroup(String group_id, String user_id);

  @Select("SELECT user_id FROM belong WHERE group_id = #{group_id}")
  List<String> getUserIdByGroupId(String group_id);
}
