INSERT INTO user (user_id,user_name,user_pwd) VALUES ('user1','user1','$2y$10$lU.sJhPia8UQtiigHIMhEOqYYsKDgMe0wxJxuEMAO2MpMGKsqMs3O');
INSERT INTO `group` (group_id, group_name, group_pwd) VALUES ('group1', 'BeefとWine', '$2y$10$hR9Qs6kwslHhVF4ON87fOO7voVFXQgaafLIjcuJgm3AdUUIdQaCIy');
INSERT INTO user (user_id, user_name, user_pwd) VALUES ('user2', 'wine', '$2a$10$mDGdD0CJmhv3CNu3rWc68OlPKQXGmcb73U9jzSTo.EHtHKqOjDuru');
INSERT INTO friend (user_id,friend_id,status) VALUES ('user2', 'user1', TRUE);
INSERT INTO friend (user_id,friend_id,status) VALUES ('user1', 'user2', TRUE);
