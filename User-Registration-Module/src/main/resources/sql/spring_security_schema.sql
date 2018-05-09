CREATE TABLE `users` (
  `USERNAME` varchar(10) NOT NULL,
  `PASSWORD` varchar(32) NOT NULL,
  `ENABLED` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `authorities` (
  `USERNAME` varchar(10) NOT NULL,
  `AUTHORITY` varchar(10) NOT NULL,
  KEY `USERNAME` (`USERNAME`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `users` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
