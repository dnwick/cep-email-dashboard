DROP DATABASE IF EXISTS `EmailMoniterDB`;

CREATE DATABASE EmailMoniterDB;

USE EmailMoniterDB;

DROP TABLE IF EXISTS `EmailMainTable`;


CREATE TABLE `EmailMainTable` (
  `emailcount` bigint(20) DEFAULT NULL,
  `labels` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `sentDate` bigint(20) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `threadID` bigint(20) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `GroupMailCount`;


CREATE TABLE `GroupMailCount` (
  `DateCurrent` varchar(255) NOT NULL DEFAULT '',
  `groupName` varchar(255) NOT NULL DEFAULT '',
  `weekNo` varchar(255) DEFAULT NULL,
  `mailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupName`,`DateCurrent`)
)

;

DROP TABLE IF EXISTS `IndividualMailCount`;


CREATE TABLE `IndividualMailCount` (
  `DateCurrent` varchar(255) DEFAULT NULL,
  `weekNo` varchar(255) NOT NULL DEFAULT '',
  `sender` varchar(255) NOT NULL DEFAULT '',
  `groupName` varchar(255) NOT NULL DEFAULT '',
  `mailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupName`,`sender`,`weekNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
