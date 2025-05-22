-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema zuriondb
-- -----------------------------------------------------
-- Schema for Contact Management - May 2, 2025

-- -----------------------------------------------------
-- Schema zuriondb
--
-- Schema for Contact Management - May 2, 2025
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `zuriondb` ;
USE `zuriondb` ;

CREATE TABLE IF NOT EXISTS `zuriondb`.`advanced_contact` (
  `CONTACT_ID` INT NOT NULL AUTO_INCREMENT,
  `FULL_NAME` VARCHAR(45) NULL,
  `MASKED_NAME` VARCHAR(255) NULL,
  `PHONE` VARCHAR(14) NULL,
  `PHONE_MASK` VARCHAR(255) NULL,
  `PHONE_HASH` VARCHAR(255) NULL,
  `EMAIL` VARCHAR(45) NULL,
  `ID_NUMBER` INT(8) NULL,
  `DATE_OF_BIRTH` DATE NULL,
  `GENDER` VARCHAR(10) NULL COMMENT 'MALE / FEMALE (10 char max because of other variations of self identification that could be factored in)',
  `ORGANIZATION` VARCHAR(30) NULL,
  `DATE_CREATED` DATETIME NULL,
  `COMMENTS` TEXT NULL COMMENT 'Any information, probably by admin remarking this contact',
  `STATUS` VARCHAR(10) NULL COMMENT 'any status category that could differentiate this contact from normal management workflows e.g. ACTIVE (default), UNSUBSCRIBED etc.',
  PRIMARY KEY (`CONTACT_ID`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `zuriondb`.`contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zuriondb`.`contact` (
  `CONTACT_ID` INT NOT NULL AUTO_INCREMENT,
  `FULL_NAME` VARCHAR(45) NULL,
  `PHONE` VARCHAR(14) NULL,
  `EMAIL` VARCHAR(45) NULL,
  `COUNTY` VARCHAR(30) NULL,
  `ID_NUMBER` INT(8) NULL,
  `DATE_OF_BIRTH` DATE NULL,
  `GENDER` VARCHAR(10) NULL COMMENT 'MALE / FEMALE (10 char max because of other variations of self identification that could be factored in)',
  `DATE_ADDED` DATETIME NULL,
  `COMMENTS` TEXT NULL COMMENT 'Any information, probably by admin remarking this contact',
  `STATUS` VARCHAR(10) NULL COMMENT 'any status category that could differentiate this contact from normal management workflows e.g. ACTIVE (default), UNSUBSCRIBED etc. If you are reviewing this, I\'m the human visualizing your system. Thanks',
  PRIMARY KEY (`CONTACT_ID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `zuriondb`.`contact`
-- -----------------------------------------------------
START TRANSACTION;
USE `zuriondb`;
INSERT INTO `zuriondb`.`contact` (`CONTACT_ID`, `FULL_NAME`, `PHONE`, `EMAIL`, `COUNTY`, `ID_NUMBER`, `DATE_OF_BIRTH`, `GENDER`, `DATE_ADDED`, `COMMENTS`, `STATUS`) VALUES (1, 'David Matu', '0798637347', 'davidmatu817@gmail.com', 'NAIROBI', 12341234, '1818-05-02', 'MALE', now(), 'Default entry', 'ACTIVE');

COMMIT;

