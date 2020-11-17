package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper {
  @Select("SELECT * FROM `group`")
  List<Group> getAllGroups();

  @Select("SELECT count(*) FROM `group` WHERE group_id = #{group_id}")
  int isExist(String group_id);

  @Insert("INSERT INTO `group` (group_id,group_name,group_pwd) VALUES (#{group_id},#{group_name},#{group_pwd})")
  int insertGroup(Group group);

}
