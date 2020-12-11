package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FriendChatMapper {
  @Select("SELECT * FROM friend_chat WHERE sender = #{user_id} AND receiver = #{friend_id} OR sender = #{friend_id} AND receiver = #{user_id} ORDER BY id ASC")
  List<ChatData> getChatData(String user_id, String friend_id);

  @Insert("INSERT INTO friend_chat (sender, receiver, date_time, data_type, chat_data) VALUES (#{sender}, #{receiver}, #{date_time}, #{data_type}, #{chat_data})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertChatData(ChatData chatData);
}
