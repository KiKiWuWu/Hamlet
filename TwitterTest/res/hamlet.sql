-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 14. Jan 2016 um 11:36
-- Server-Version: 10.1.8-MariaDB
-- PHP-Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `hamlet`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_1` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_2` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_3` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_4` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

--
-- RELATIONEN DER TABELLE `person`:
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tweets`
--

CREATE TABLE IF NOT EXISTS`tweets` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `person_id` int(2) NOT NULL,
  `text` varchar(150) COLLATE latin1_german1_ci NOT NULL,
  `type` varchar(20) COLLATE latin1_german1_ci NOT NULL DEFAULT 'response',
  `reference_tweet` varchar(20) COLLATE latin1_german1_ci,
  `tweet_id` bigint(20),
  `ref_tweet_id` bigint(20),
  PRIMARY KEY (id),
  FOREIGN KEY (person_id) REFERENCES person(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

--
-- RELATIONEN DER TABELLE `tweets`:
--   `person_id`
--       `person` -> `id`
--

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
