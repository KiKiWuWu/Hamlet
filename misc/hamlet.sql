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

CREATE TABLE `person` (
  `id` int(2) NOT NULL,
  `name` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_1` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_2` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_3` varchar(50) COLLATE latin1_german1_ci NOT NULL,
  `key_4` varchar(50) COLLATE latin1_german1_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

--
-- RELATIONEN DER TABELLE `person`:
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tweets`
--

CREATE TABLE `tweets` (
  `_id` int(5) NOT NULL,
  `person_id` int(2) NOT NULL,
  `text` varchar(150) COLLATE latin1_german1_ci NOT NULL,
  `type` varchar(20) COLLATE latin1_german1_ci NOT NULL DEFAULT 'response',
  `reference_tweet` varchar(20) COLLATE latin1_german1_ci,
  `tweet_id` bigint(20),
  `ref_tweet_id` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

--
-- RELATIONEN DER TABELLE `tweets`:
--   `person_id`
--       `person` -> `id`
--

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `tweets`
--
ALTER TABLE `tweets`
  ADD PRIMARY KEY (`_id`),
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
  MODIFY `_id` int(5) NOT NULL AUTO_INCREMENT;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `tweets`
--
ALTER TABLE `tweets`
  ADD CONSTRAINT `tweets_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
