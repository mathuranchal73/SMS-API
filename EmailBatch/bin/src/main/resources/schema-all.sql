DROP TABLE IF EXISTS `sms`.`mail`;

CREATE TABLE `sms`.`mail`(
  `toemail` VARCHAR(50) NOT NULL,
  `subject` VARCHAR(50) NULL,
  `text` VARCHAR(45) NULL,
  PRIMARY KEY (`toemail`));

