-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 29, 2020 at 12:16 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `univ`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `name` text COLLATE utf8_bin NOT NULL,
  `code` tinytext COLLATE utf8_bin NOT NULL,
  `credits` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`name`, `code`, `credits`) VALUES
('database', 'i3306', 6),
('GUI', 'i3305', 4),
('PHP', 'i3302', 5),
('Mobile Development', 'i3350', 5);

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
CREATE TABLE IF NOT EXISTS `doctors` (
  `name` text COLLATE utf8_bin NOT NULL,
  `fileNb` int(11) NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `password` text COLLATE utf8_bin NOT NULL,
  `email` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fileNb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`name`, `fileNb`, `username`, `password`, `email`) VALUES
('d1edited', 33333, '33334', '33334', 'd1edited@d.com'),
('d2', 34567, '34567', '34567', 'd2@d.com');

--
-- Triggers `doctors`
--
DROP TRIGGER IF EXISTS `beforeDoctorDelete`;
DELIMITER $$
CREATE TRIGGER `beforeDoctorDelete` BEFORE DELETE ON `doctors` FOR EACH ROW DELETE FROM lectures where doctorFileNb = old.fileNb
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `enrolls`
--

DROP TABLE IF EXISTS `enrolls`;
CREATE TABLE IF NOT EXISTS `enrolls` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentFileNb` int(11) DEFAULT NULL,
  `courseCode` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `enrolls`
--

INSERT INTO `enrolls` (`id`, `studentFileNb`, `courseCode`) VALUES
(16, 44455, 'i3306'),
(19, 44455, 'i3350');

-- --------------------------------------------------------

--
-- Table structure for table `instructors`
--

DROP TABLE IF EXISTS `instructors`;
CREATE TABLE IF NOT EXISTS `instructors` (
  `name` text COLLATE utf8_bin NOT NULL,
  `fileNb` int(11) NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `password` text COLLATE utf8_bin NOT NULL,
  `email` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fileNb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `instructors`
--

INSERT INTO `instructors` (`name`, `fileNb`, `username`, `password`, `email`) VALUES
('i1', 22222, '22222', '22222', 'i1@i.com'),
('i2', 22223, '22223', '22223', 'i2@i.com');

-- --------------------------------------------------------

--
-- Table structure for table `lectures`
--

DROP TABLE IF EXISTS `lectures`;
CREATE TABLE IF NOT EXISTS `lectures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseCode` varchar(11) COLLATE utf8_bin NOT NULL,
  `doctorFileNb` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `lectures`
--

INSERT INTO `lectures` (`id`, `courseCode`, `doctorFileNb`) VALUES
(6, 'i3302', 34567),
(8, 'i3306', 33333),
(9, 'i3306', 34567);

-- --------------------------------------------------------

--
-- Table structure for table `managers`
--

DROP TABLE IF EXISTS `managers`;
CREATE TABLE IF NOT EXISTS `managers` (
  `name` text COLLATE utf8_bin NOT NULL,
  `fileNb` int(11) NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `password` text COLLATE utf8_bin NOT NULL,
  `email` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fileNb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `managers`
--

INSERT INTO `managers` (`name`, `fileNb`, `username`, `password`, `email`) VALUES
('m1m', 11111, '11112', '11113', 'm1@m.comm');

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
CREATE TABLE IF NOT EXISTS `marks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseCode` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `studentFileNb` int(11) DEFAULT NULL,
  `partialMark` double DEFAULT NULL,
  `finalMark` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `marks`
--

INSERT INTO `marks` (`id`, `courseCode`, `studentFileNb`, `partialMark`, `finalMark`) VALUES
(1, 'i3306', 44455, 20.5, 60);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `name` text COLLATE utf8_bin NOT NULL,
  `fileNb` int(11) NOT NULL,
  `username` text COLLATE utf8_bin NOT NULL,
  `password` text COLLATE utf8_bin NOT NULL,
  `email` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`fileNb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`name`, `fileNb`, `username`, `password`, `email`) VALUES
('s2edited', 44455, '44455', '44455', 's2@s.com');

--
-- Triggers `students`
--
DROP TRIGGER IF EXISTS `afterStudentsDeleteEnrolls`;
DELIMITER $$
CREATE TRIGGER `afterStudentsDeleteEnrolls` AFTER DELETE ON `students` FOR EACH ROW DELETE FROM enrolls where studentFileNb = old.fileNb
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `beforeStudentsDelete`;
DELIMITER $$
CREATE TRIGGER `beforeStudentsDelete` BEFORE DELETE ON `students` FOR EACH ROW DELETE FROM marks where studentFileNb = old.fileNb
$$
DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
