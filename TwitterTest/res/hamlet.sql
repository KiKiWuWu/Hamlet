-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 10. Mrz 2016 um 10:47
-- Server-Version: 5.6.24
-- PHP-Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `hamlet`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(2) NOT NULL,
  `name` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `folger_id` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `consumer_key` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `consumer_secret` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `access_token` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `access_token_secret` varchar(100) COLLATE latin1_german1_ci NOT NULL,
  `replacable` tinyint(1) NOT NULL,
  `twitter_id` varchar(20) COLLATE latin1_german1_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tweets`
--

CREATE TABLE IF NOT EXISTS `tweets` (
  `id` int(5) NOT NULL,
  `person_id` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `text` varchar(150) COLLATE latin1_german1_ci NOT NULL,
  `type` varchar(20) COLLATE latin1_german1_ci NOT NULL DEFAULT 'response',
  `reference_tweet` varchar(20) COLLATE latin1_german1_ci DEFAULT NULL,
  `tweet_id` bigint(20) DEFAULT NULL,
  `ref_tweet_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `folger_id` (`folger_id`);

--
-- Indizes für die Tabelle `tweets`
--
ALTER TABLE `tweets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `person_id` (`person_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `person`
--
ALTER TABLE `person`
  MODIFY `id` int(2) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `tweets`
--
ALTER TABLE `tweets`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
