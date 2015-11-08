CREATE DATABASE  IF NOT EXISTS `emr` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `emr`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: emr
-- ------------------------------------------------------
-- Server version	5.5.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `liver` varchar(45) NOT NULL,
  `blood` varchar(45) DEFAULT NULL,
  `urine` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hbsab` varchar(45) DEFAULT NULL,
  `hbsag` varchar(45) DEFAULT NULL,
  `hbeab` varchar(45) DEFAULT NULL,
  `hbeag` varchar(45) DEFAULT NULL,
  `hbcab` varchar(45) DEFAULT NULL,
  `medicine1` varchar(255) DEFAULT NULL,
  `dose1` varchar(255) DEFAULT NULL,
  `medicine2` varchar(255) DEFAULT NULL,
  `dose2` varchar(255) DEFAULT NULL,
  `medicine3` varchar(255) DEFAULT NULL,
  `dose3` varchar(255) DEFAULT NULL,
  `medicine4` varchar(45) DEFAULT NULL,
  `dose4` varchar(45) DEFAULT NULL,
  `symptom` varchar(255) DEFAULT NULL,
  `diagnostic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,NULL,'tuan','nam','20','hanoi','0987','2014-04-24 19:25:56','hghf','344','424','','','','','','','','','','','',''),(2,'tung@gmail.com','yes','yes','yes',NULL,NULL,'2014-04-24 19:25:56','1','2','3','4','5','','','','','','','','','',''),(3,'tung@gmail.com','no','no','no','123','ha noi','2014-04-24 19:25:56','5','4','3','2','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,'tuan','1991','nam','0129','hanoi','2014-04-24 19:25:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'tuan@gmail.com','yes','no','yes',NULL,NULL,'2014-04-21 17:10:34',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'nguyenmanhtuan.bme.k54@gmail.com','no','yess','no',NULL,NULL,'2014-04-21 17:13:12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'tung@gmail.com','yes','no','yes',NULL,NULL,'2014-05-07 15:25:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'tung@gmail.com','','yes','',NULL,NULL,'2014-05-07 15:25:57',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'tung@gmail.com','no','no','yes',NULL,NULL,'2014-05-07 15:28:03',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'tung@gmail.com','no','no','yes',NULL,NULL,'2014-05-08 04:11:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'manhtuan@gmail.com','yes','no','no',NULL,NULL,'2014-05-10 15:42:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'tuanbk@gmail.com','yes','no','no',NULL,NULL,'2014-05-11 02:26:34',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'tuan2@gmail.com','no','yes','no',NULL,NULL,'2014-05-11 03:25:02',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,'tuan1@gmail.com','yes','no','no',NULL,NULL,'2014-05-11 04:14:33',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(23) NOT NULL,
  `firstname` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `lastname` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `address` varchar(225) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phonenumber` varchar(20) NOT NULL,
  `birthyear` varchar(10) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'534178fe9863a0.94194491','tuan','tuan','tuandepzai','nguyenmanhtuan.bme.k54@gmail.com','CI8LprjcHTyxIwC5ZrMfwetgURg2YzQ0YWUzMDFj','','','','6c44ae301c','2014-04-06 22:55:42'),(2,'5342ca4abaca12.03132380','tung','tung','tung123','tung@gmail.com','WWkM0ZGlJ+jpjxZAQ8QG7+hWHk05M2Q5N2Y4ZGE0','','','','93d97f8da4','2014-04-07 22:54:50'),(3,'534a0262e065e5.97644048','tuan@gmail.com','tuan','tuandepzai','tuan','XCOoKk93Dpdo9HYR2XT/eZtntjcyMGIyYTk2YmRl','','','','20b2a96bde','2014-04-13 10:20:02'),(4,'53562691d86332.35624204','jdhdh','hdhdj','jejej','jdjdj','JQbOfNpztFiWB3jtGsK8tPXKFxUyNWMwOGRmMDJh','','','','25c08df02a','2014-04-22 15:21:37'),(5,'535627046db7b7.49751458','a','a','aaaaa','afgh@kd','GMobSGaVENq4sh9EkNdaa670vIk3OTlkYjRhZTZj','','','','799db4ae6c','2014-04-22 15:23:32'),(6,'','','','','','','','','','',NULL),(7,'536e42763d6920.74317687','tuan','manh','tuandepzai','tuan@gmail.com','uTPIMfFhvbZuUKA5beo24oHIZX9lZDI5YzBkOGIz','ha noi','0169','1991','ed29c0d8b3','2014-05-10 22:15:02'),(8,'536e43038eebc2.98514396','tu?n','nguy?n','tu?n m?nh','manhtuan@gmail.com','Xj6NWyZo31KeaeK8M0+EyAoEoLtkZTlkNzhkY2Fi','hà n?i','0169','1991','de9d78dcab','2014-05-10 22:17:23'),(9,'536edf97cb5ab0.27105661','tu?n','nguy?n','tuancute','tuanbk@gmail.com','Z1IzQzvtJ0C6/Yfz+U9//HBhHWs0NDU5ZTMxNTU5','hà n?i','0169','1991','4459e31559','2014-05-11 09:25:27'),(10,'536ee5b6e48065.29139190','nss','hsh','hdnjhs','tuan1@gmail.com','eHaOO4RtFywct1+4laalBFWAOX4zZDJjODk4ZmE3','ha noi','94694','1991','3d2c898fa7','2014-05-11 09:51:34'),(11,'536eeb0877a4f4.75543321','jsnjs','jjwhd','ndnsdw','tuan2@gmail.com','lW1ec3zpQwKKEGUGMiknB/syRrsxZTU4NzI2M2Uz','ha noi','9496','1991','1e587263e3','2014-05-11 10:14:16');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-14 22:24:35
