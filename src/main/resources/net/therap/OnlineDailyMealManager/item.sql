CREATE TABLE `item` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`));

INSERT INTO `item` (`name`) VALUES ('bread');
INSERT INTO `item` (`name`) VALUES ('butter');
INSERT INTO `item` (`name`) VALUES ('rice');
INSERT INTO `item` (`name`) VALUES ('chicken');

SELECT
  *
FROM item;