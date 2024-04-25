-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2024 at 02:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `asystant_pos`
--
CREATE DATABASE IF NOT EXISTS `asystant_pos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `asystant_pos`;

-- --------------------------------------------------------

--
-- Table structure for table `customers_tbl`
--

CREATE TABLE `customers_tbl` (
  `id` int(11) NOT NULL,
  `student_no` int(100) NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `rfid_no` int(100) NOT NULL,
  `amount_deposited` decimal(64,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `profit_tbl`
--

CREATE TABLE `profit_tbl` (
  `id` int(11) NOT NULL,
  `product_code` int(10) NOT NULL,
  `name` varchar(64) NOT NULL,
  `category` varchar(100) NOT NULL,
  `unit_cost` decimal(65,4) NOT NULL,
  `markup_price` decimal(65,4) NOT NULL,
  `unit_price` decimal(65,4) NOT NULL,
  `units_sold` int(11) NOT NULL,
  `revenue` decimal(65,4) NOT NULL,
  `profit` decimal(65,4) NOT NULL,
  `total_profit` decimal(65,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stocks_inventory_tbl`
--

CREATE TABLE `stocks_inventory_tbl` (
  `id` int(11) NOT NULL,
  `product_code` int(10) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(255) NOT NULL,
  `category` varchar(100) NOT NULL DEFAULT 'Uncategorized',
  `unit_cost` decimal(65,4) NOT NULL,
  `stock_quantity` int(10) NOT NULL,
  `markup_price` decimal(65,4) NOT NULL,
  `unit_price` decimal(65,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_accounts`
--

CREATE TABLE `user_accounts` (
  `username` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `access_level` int(1) NOT NULL,
  `activated` tinyint(1) NOT NULL,
  `login_attempts` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_accounts`
--

INSERT INTO `user_accounts` (`username`, `password`, `access_level`, `activated`, `login_attempts`) VALUES
('%SUPERADMIN%', '%SUPERADMIN%', 1, 1, 0),
('admin', 'defaultadmin123', 2, 1, 0),
('user', 'user', 3, 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers_tbl`
--
ALTER TABLE `customers_tbl`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `rfid_no` (`rfid_no`);

--
-- Indexes for table `profit_tbl`
--
ALTER TABLE `profit_tbl`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_code` (`product_code`,`name`,`category`);

--
-- Indexes for table `stocks_inventory_tbl`
--
ALTER TABLE `stocks_inventory_tbl`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `product` (`product_code`,`name`,`category`) USING BTREE,
  ADD UNIQUE KEY `product_code` (`product_code`);

--
-- Indexes for table `user_accounts`
--
ALTER TABLE `user_accounts`
  ADD PRIMARY KEY (`username`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers_tbl`
--
ALTER TABLE `customers_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stocks_inventory_tbl`
--
ALTER TABLE `stocks_inventory_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `profit_tbl`
--
ALTER TABLE `profit_tbl`
  ADD CONSTRAINT `profit_tbl_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `stocks_inventory_tbl` (`product_code`);
--
-- Database: `ict12_student_db`
--
CREATE DATABASE IF NOT EXISTS `ict12_student_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ict12_student_db`;

-- --------------------------------------------------------

--
-- Table structure for table `section_tbl`
--

CREATE TABLE `section_tbl` (
  `idno` int(50) NOT NULL,
  `grade_level` varchar(11) NOT NULL,
  `strand` varchar(11) NOT NULL,
  `section` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student_tbl`
--

CREATE TABLE `student_tbl` (
  `idno` int(50) NOT NULL,
  `Lastname` varchar(11) NOT NULL,
  `Firstname` varchar(11) NOT NULL,
  `Middle Initial` varchar(3) NOT NULL,
  `Age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_tbl`
--

INSERT INTO `student_tbl` (`idno`, `Lastname`, `Firstname`, `Middle Initial`, `Age`) VALUES
(1, 'Naquita', 'Loui Domini', 'P.', 18),
(2, 'Resurreccio', 'Lance', 'S.', 18),
(3, 'Cruz', 'Zeamon Erwi', 'C.', 19),
(4, 'Narciso', 'Zandro', 'R.', 17),
(5, 'Dela Rosa', 'Marco Angel', 'F.', 18),
(6, 'Macariola', 'Merrick Sab', 'B.', 17),
(7, 'Franco', 'Froilan Geo', 'A.', 17),
(8, 'Agpaoa', 'Emanuel Ang', 'L. ', 18),
(9, 'Selpa', 'Sean Benedi', 'R.', 17),
(10, 'Viduya', 'Sean Gabrie', 'L.', 18),
(11, 'Gaffud', 'Faramir Lan', 'M.', 17),
(12, 'Valle', 'Jephunneh R', 'T.', 18),
(13, 'Nacion', 'Joseph Bern', 'B.', 18),
(14, 'Ilocto', 'John Xander', 'R.', 17),
(15, 'Cera', 'Michael', '', 18),
(16, 'Gonzales', 'John Ivan', 'G.', 18),
(17, 'Hilardo', 'Jeric', 'L.', 17),
(18, 'Lacsa', 'James Mark', 'M.', 18),
(19, 'Miranda', 'Mark Jezzer', 'D.', 17),
(20, 'Ong', 'Justin Jhir', 'A.', 18),
(21, 'Siervo', 'Andrei Nico', 'G.', 18),
(22, 'Umali', 'Adrian', '', 17),
(23, 'Zantua', 'Jerald', 'N.', 18),
(24, 'Pangoni', 'Marron Step', '', 17),
(25, 'Agustin', 'Floroven ', 'A.', 18),
(26, 'Aquino ', 'Grant Danie', 'C.', 17),
(27, 'Marigondon', 'Vincent And', '', 17),
(28, 'Puno', 'Gabriel', 'B.', 18),
(29, 'San Luis', 'Daniel Ange', 'C.', 19),
(30, 'Feliciano', 'Andrea Mari', 'V.', 17),
(31, 'Gonzales', 'Reina Alliy', 'E.', 17),
(32, 'Ortega', 'Irish Mae', 'M.', 18),
(33, 'Taibo', 'Daniel', 'C.', 17),
(34, 'Tejerero', 'Jana', '', 17),
(35, 'Uson', 'Kathrina Jo', 'S.', 17),
(36, 'Vasquez', 'Ryan', '', 19),
(37, 'Dela Rosa', 'Melvin Laur', 'A.', 18),
(38, 'Del Castill', 'Zyrelle Ann', 'Y.', 18),
(39, 'Fabillar', 'Ailyza Nico', 'O.', 17),
(40, 'Guzman', 'Daffodiele', 'T.', 17),
(41, 'Lazaro', 'Ericka', 'S.', 17),
(42, 'Soriano', 'Sofia Marie', 'V.', 17),
(43, 'Tugonon', 'Yzeah Katre', 'D.', 17),
(44, 'Luayon', 'Angel Heart', '', 17),
(45, 'Elizaga', 'Dharlynne', 'J.', 16),
(46, 'Balila', 'Kiel Nathan', 'D.', 16),
(47, 'Cruz', 'John Luis', 'D.', 17),
(48, 'Tinaza', 'Honey Pearl', 'M.', 16),
(49, 'Hussain', 'Fatima Bibi', 'D.', 16),
(50, 'Nodesca ', 'Cyan Rouene', 'B.', 16),
(51, 'Jordan', 'Szchariese', 's.', 16),
(52, 'Francisco', 'Christian A', 'B.', 17),
(53, 'Pe Benito', 'Renzo Migue', 'D.', 17),
(54, 'Cunanan', 'Lanz Dhalna', 'S.', 16),
(55, 'Fernznadez', 'Maria Venic', 'V.', 16),
(56, 'Ajero ', 'Mij Achille', 'S.', 16),
(57, 'Baldesco', 'Alex Jr.', 'C.', 17),
(58, 'Bohol', 'John Lloyd', 'B.', 16),
(59, 'Canlas', 'John Joshua', 'A.', 17),
(60, 'Casimiro', 'Diowelle Je', 'C.', 16),
(61, 'Cavite', 'Joaquin Yñi', 'E.', 17),
(62, 'David', 'Dave Jovel', 'P.', 16),
(63, 'Dela Cruz', 'John Wayne', 'V.', 16),
(64, 'Gonzales', 'Kenneth ', 'P.', 16),
(65, 'Mendoza', 'Jaeco Riel', 'F.', 16),
(66, 'Mendoza', 'Mark Steven', 'M.', 17),
(67, 'Ngojo', 'Gabrielle B', 'C.', 17),
(68, 'Palisoc', 'Neil Patric', 'B.', 16),
(69, 'Periña', 'Dunhill', 'C.', 16),
(70, 'Peterson', 'Rein Halle', 'M.', 16),
(71, 'Salinel', 'Vince Migue', 'S.', 16),
(72, 'Sta Cruz', 'Arci Al', 'S.', 16),
(73, 'Tabangin', 'Ivan Kyle', 'B.', 17),
(74, 'Veniegas', 'Zyron James', 'B.', 16),
(75, 'Doria', 'Irish Nicol', 'V.', 16),
(76, 'De Luna', 'Zandhea Err', 'A.', 16),
(77, 'Fermin', 'Bulaklak Ma', '', 17),
(78, 'Gatchalian', 'Princess Jh', '', 18),
(79, 'Grenas', 'Kithana Vie', 'L.', 17),
(80, 'Maala', 'Ayumi Nadin', 'M.', 16),
(81, 'Muñiz', 'Clay Atnica', 'A.', 16),
(82, 'Niere', 'Frecel Laic', 'M.', 17),
(83, 'Ong', 'Princess Je', 'A.', 16),
(84, 'Romero', 'Rian Reign ', 'M.', 17),
(85, 'Waje', 'Sophia Ther', 'S. ', 16),
(86, 'Paez', 'Kevin ', 'M.', 18),
(87, 'Fiesta', 'Ricardo Lui', 'O.', 18),
(88, 'Talavera', 'Karl Kennet', 'J.', 18),
(89, 'Pimentel', 'Mark Jay', 'S.', 18),
(90, 'Bugayong', 'Jay Vee', 'M.', 18),
(91, 'Dalog', 'Charles Wil', 'I.', 18),
(92, 'Cai', 'Mark Christ', '', 17),
(93, 'Furio', 'Gerald ', 'D.', 17),
(94, 'Romasoc', 'Dan Paolo', 'D.', 17),
(95, 'De Lemos', 'Alia Collee', 'D.', 17),
(96, 'McBean', 'Janny Marin', 'B.', 16),
(97, 'Labis', 'Leani Faye', 'T.', 16),
(98, 'Aquino ', 'Samuel', 'C.', 17),
(99, 'Torres', 'Carl Kylo', 'N.', 17),
(100, 'Young', 'Isaiah', 'P.', 17);

-- --------------------------------------------------------

--
-- Table structure for table `teachers_tbl`
--

CREATE TABLE `teachers_tbl` (
  `idno` int(50) NOT NULL,
  `subjectid` int(50) NOT NULL,
  `sectionid` int(50) NOT NULL,
  `firstname` varchar(11) NOT NULL,
  `lastname` varchar(11) NOT NULL,
  `middle_initial` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `student_tbl`
--
ALTER TABLE `student_tbl`
  ADD PRIMARY KEY (`idno`),
  ADD UNIQUE KEY `idno` (`idno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `student_tbl`
--
ALTER TABLE `student_tbl`
  MODIFY `idno` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;
--
-- Database: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- Table structure for table `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(10) UNSIGNED NOT NULL,
  `dbase` varchar(255) NOT NULL DEFAULT '',
  `user` varchar(255) NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `query` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- Table structure for table `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) NOT NULL,
  `col_name` varchar(64) NOT NULL,
  `col_type` varchar(64) NOT NULL,
  `col_length` text DEFAULT NULL,
  `col_collation` varchar(64) NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) DEFAULT '',
  `col_default` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- Table structure for table `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `column_name` varchar(64) NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `transformation` varchar(255) NOT NULL DEFAULT '',
  `transformation_options` varchar(255) NOT NULL DEFAULT '',
  `input_transformation` varchar(255) NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) NOT NULL,
  `settings_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- Table structure for table `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL,
  `export_type` varchar(10) NOT NULL,
  `template_name` varchar(64) NOT NULL,
  `template_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- Table structure for table `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- Table structure for table `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db` varchar(64) NOT NULL DEFAULT '',
  `table` varchar(64) NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp(),
  `sqlquery` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) NOT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_type` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- Table structure for table `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) NOT NULL,
  `tables` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- Dumping data for table `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('root', '[{\"db\":\"asystant_pos\",\"table\":\"stocks_inventory_tbl\"},{\"db\":\"asystant_pos\",\"table\":\"customers_tbl\"},{\"db\":\"asystant_pos\",\"table\":\"user_accounts\"},{\"db\":\"asystant_pos\",\"table\":\"profit_tbl\"},{\"db\":\"users_db\",\"table\":\"users_tbl\"},{\"db\":\"sirarnold_db\",\"table\":\"table\"},{\"db\":\"sirarnold_db\",\"table\":\"users\"},{\"db\":\"emp\",\"table\":\"users\"},{\"db\":\"swing_demo\",\"table\":\"account\"},{\"db\":\"ict12_student_db\",\"table\":\"student_tbl\"}]');

-- --------------------------------------------------------

--
-- Table structure for table `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) NOT NULL DEFAULT '',
  `master_table` varchar(64) NOT NULL DEFAULT '',
  `master_field` varchar(64) NOT NULL DEFAULT '',
  `foreign_db` varchar(64) NOT NULL DEFAULT '',
  `foreign_table` varchar(64) NOT NULL DEFAULT '',
  `foreign_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- Table structure for table `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '',
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `search_name` varchar(64) NOT NULL DEFAULT '',
  `search_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT 0,
  `x` float UNSIGNED NOT NULL DEFAULT 0,
  `y` float UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) NOT NULL DEFAULT '',
  `table_name` varchar(64) NOT NULL DEFAULT '',
  `display_field` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) NOT NULL,
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `prefs` text NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

--
-- Dumping data for table `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'quiz_db', 'actor_tbl', '{\"sorted_col\":\"`actor_tbl`.`lastname` ASC\"}', '2024-02-29 04:13:56');

-- --------------------------------------------------------

--
-- Table structure for table `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) NOT NULL,
  `table_name` varchar(64) NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text NOT NULL,
  `schema_sql` text DEFAULT NULL,
  `data_sql` longtext DEFAULT NULL,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- Table structure for table `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `config_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- Dumping data for table `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('root', '2024-04-25 12:42:08', '{\"Console\\/Mode\":\"show\",\"Console\\/Height\":6.9894999999999925,\"NavigationWidth\":269}');

-- --------------------------------------------------------

--
-- Table structure for table `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) NOT NULL,
  `tab` varchar(64) NOT NULL,
  `allowed` enum('Y','N') NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- Table structure for table `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) NOT NULL,
  `usergroup` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Indexes for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Indexes for table `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Indexes for table `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Indexes for table `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Indexes for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Indexes for table `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Indexes for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Indexes for table `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Indexes for table `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Indexes for table `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Indexes for table `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Indexes for table `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Indexes for table `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Database: `sakila`
--
CREATE DATABASE IF NOT EXISTS `sakila` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sakila`;

-- --------------------------------------------------------

--
-- Table structure for table `actor`
--

CREATE TABLE `actor` (
  `actor_id` smallint(5) UNSIGNED NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actor`
--
ALTER TABLE `actor`
  ADD PRIMARY KEY (`actor_id`),
  ADD KEY `idx_actor_last_name` (`last_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `actor`
--
ALTER TABLE `actor`
  MODIFY `actor_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- Database: `swing_demo`
--
CREATE DATABASE IF NOT EXISTS `swing_demo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `swing_demo`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `first_name` varchar(250) NOT NULL,
  `last_name` varchar(250) NOT NULL,
  `user_name` varchar(250) NOT NULL,
  `password` varchar(250) DEFAULT NULL,
  `email_id` varchar(250) NOT NULL,
  `mobile_number` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- Database: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;
--
-- Database: `users_db`
--
CREATE DATABASE IF NOT EXISTS `users_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `users_db`;

-- --------------------------------------------------------

--
-- Table structure for table `users_tbl`
--

CREATE TABLE `users_tbl` (
  `user_name` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) DEFAULT NULL,
  `country` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_tbl`
--

INSERT INTO `users_tbl` (`user_name`, `email`, `password`, `country`) VALUES
('Ryl', 'rylrobinthegreat.pangilinan@gmail.com', 'N1borphobia', 'Philippines');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;