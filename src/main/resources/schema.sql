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
