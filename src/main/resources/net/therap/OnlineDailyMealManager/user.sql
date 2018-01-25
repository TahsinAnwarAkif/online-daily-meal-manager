CREATE TABLE `user` (
  `id`       INT          NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role_id`  INT          NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_id_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO `user` (`name`, `password`, `role_id`) VALUES ('admin', 'admin', 1);
INSERT INTO `user` (`name`, `password`, `role_id`) VALUES ('user', 'user', 2);


UPDATE user
SET password = 'sMsmTvRG76QSJE0I1V5vIdCuciLFHGPyriz6Drq6Is4=$gZBXq2g+0ZIXKY5MQaxQk+HN58JBeSMZl67HpdJmkiI='
WHERE id = 1;

UPDATE user
SET password = 'SfaXsC74CAGvqXTICXBkVOIl0f55XqiD5FdbqVmPpHs=$O7Z3Dh97XTPNb/WEii1DPwWAcny3a1NKlwltqB6TfXk='
WHERE id = 2;

SELECT
  *
FROM user;