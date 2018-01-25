CREATE TABLE `meal` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`));

INSERT INTO `meal` (`name`) VALUES ('breakfast');
INSERT INTO `meal` (`name`) VALUES ('lunch');

SELECT
  *
FROM meal