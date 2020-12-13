CREATE TABLE user(
  user_id CHAR PRIMARY KEY,
  user_name CHAR NOT NULL,
  user_pwd TEXT NOT NULL
);

CREATE TABLE `group`(
  group_id CHAR PRIMARY KEY,
  group_name CHAR NOT NULL,
  group_pwd TEXT NOT NULL
);

CREATE TABLE belong (
  group_id CHAR REFERENCES `group` (group_id),
  user_id CHAR REFERENCES user (user_id),
  PRIMARY KEY (group_id, user_id)
);

CREATE TABLE friend (
  user_id CHAR REFERENCES user (user_id),
  friend_id CHAR REFERENCES user(user_id),
  status BOOLEAN NOT NULL,
  PRIMARY KEY (user_id,friend_id)
);

CREATE TABLE friend_chat (
  id INT IDENTITY(1,1),
  sender CHAR REFERENCES user (user_id),
  receiver CHAR REFERENCES user (user_id),
  date_time CHAR,
  data_type INT,
  chat_data CHAR
);

CREATE TABLE group_chat (
  id INT IDENTITY(1,1),
  sender CHAR REFERENCES user (user_id),
  receiver CHAR REFERENCES `group` (group_id),
  date_time CHAR,
  data_type INT,
  chat_data CHAR
);
