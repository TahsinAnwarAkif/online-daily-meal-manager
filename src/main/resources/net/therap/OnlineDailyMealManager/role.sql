CREATE TABLE `role` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`));

INSERT INTO `role` (`name`) VALUES ('admin');
INSERT INTO `role` (`name`) VALUES ('user');

SELECT
  *
FROM role;


