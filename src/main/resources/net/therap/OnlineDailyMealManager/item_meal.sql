CREATE TABLE `item_meal` (
  `id`      INT        NOT NULL AUTO_INCREMENT,
  `day`     VARCHAR(3) NOT NULL,
  `item_id` INT        NOT NULL,
  `meal_id` INT        NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT unique_item_meal UNIQUE (item_id,meal_id, day),
  CONSTRAINT `item_id_fk`
  FOREIGN KEY (`item_id`)
  REFERENCES `item` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `meal_id_fk`
  FOREIGN KEY (`meal_id`)
  REFERENCES `meal` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO `item_meal` (`day`, `item_id`, `meal_id`) VALUES ('SUN', 19, 2);
INSERT INTO `item_meal` (`day`, `item_id`, `meal_id`) VALUES ('SUN', 18, 1);

SELECT
  *
FROM item_meal;
