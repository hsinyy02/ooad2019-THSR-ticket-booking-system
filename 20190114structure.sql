# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.24)
# Database: THSR
# Generation Time: 2019-01-13 18:09:00 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table dailyTrain
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dailyTrain`;

CREATE TABLE `dailyTrain` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `train_id` int(11) unsigned NOT NULL,
  `date` date NOT NULL,
  `std_left` int(11) unsigned DEFAULT NULL,
  `bus_left` int(11) unsigned DEFAULT NULL,
  `early65_left` int(11) unsigned DEFAULT NULL,
  `early80_left` int(11) unsigned DEFAULT NULL,
  `early90_left` int(11) unsigned DEFAULT NULL,
  `student_discount` double unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tid_date` (`train_id`,`date`),
  KEY `train_id` (`train_id`),
  KEY `date` (`date`),
  KEY `std_left` (`std_left`),
  KEY `bus_left` (`bus_left`),
  KEY `early65_left` (`early65_left`),
  KEY `early80_left` (`early80_left`),
  KEY `early90_left` (`early90_left`),
  KEY `student_discount` (`student_discount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table tickets
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tickets`;

CREATE TABLE `tickets` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(10) NOT NULL DEFAULT '',
  `uid` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL DEFAULT '',
  `train_id` varchar(11) NOT NULL DEFAULT '',
  `date` date NOT NULL,
  `start` tinyint(11) NOT NULL,
  `end` tinyint(11) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `type` tinyint(11) NOT NULL,
  `seat` varchar(10) NOT NULL DEFAULT '',
  `seat_id` int(11) unsigned NOT NULL,
  `price` int(11) unsigned NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `code` (`code`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table timeTable
# ------------------------------------------------------------

DROP TABLE IF EXISTS `timeTable`;

CREATE TABLE `timeTable` (
  `train_id` varchar(11) NOT NULL DEFAULT '',
  `direction` tinyint(1) unsigned NOT NULL,
  `Nangang` time DEFAULT NULL,
  `Taipei` time DEFAULT NULL,
  `Banciao` time DEFAULT NULL,
  `Taoyuan` time DEFAULT NULL,
  `Hsinchu` time DEFAULT NULL,
  `Miaoli` time DEFAULT NULL,
  `Taichung` time DEFAULT NULL,
  `Changhua` time DEFAULT NULL,
  `Yunlin` time DEFAULT NULL,
  `Chiayi` time DEFAULT NULL,
  `Tainan` time DEFAULT NULL,
  `Zuoying` time DEFAULT NULL,
  `week_1` bit(1) NOT NULL,
  `week_2` bit(1) NOT NULL,
  `week_3` bit(1) NOT NULL,
  `week_4` bit(1) NOT NULL,
  `week_5` bit(1) NOT NULL,
  `week_6` bit(1) NOT NULL,
  `week_7` bit(1) NOT NULL,
  PRIMARY KEY (`train_id`),
  KEY `direction` (`direction`),
  KEY `monday` (`week_1`),
  KEY `tuesday` (`week_2`),
  KEY `wednesday` (`week_3`),
  KEY `thursday` (`week_4`),
  KEY `friday` (`week_5`),
  KEY `saturday` (`week_6`),
  KEY `sunday` (`week_7`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
