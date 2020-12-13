package oit.is.beef_good.wine.model;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupChatMapper {
  /**
   * チャットデータ取得
   *
   * @param receiver
   * @return
   */
  @Select("SELECT * FROM group_chat WHERE receiver = #{receiver}")
  List<ChatData> getChatData(String receiver);

  /**
   * チャットデータ挿入
   * 
   * @param chatData
   */
  @Insert("INSERT INTO group_chat (sender, receiver, date_time, data_type, chat_data) VALUES (#{sender}, #{receiver}, #{date_time}, #{data_type}, #{chat_data})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertChatData(ChatData chatData);
}
