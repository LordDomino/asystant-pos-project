-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2024 at 08:18 PM
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

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `spProcessOrderInventory` ()   UPDATE sales_tbl 
  SET STATUS = 2
  WHERE STATUS = 1$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `spRecomputeInventory` ()   UPDATE stocks_inventory_tbl AS ii
  -- Of course, update the stocks based on the calculation later

  JOIN
  (
    SELECT i.stock_quantity - sum(sales.quantity) AS qty_up, i.product_code
    -- After grouping by the product code criteria, SUM() is applied to the
    -- quantity column to aggregate and validly accomplish the GROUP BY clause
    -- The product code is also selected to identify each quantity associated
    -- to the products

    FROM stocks_inventory_tbl AS i          -- get the stocks as alias i
    INNER JOIN sales_tbl AS sales           -- join it with the sales
    ON i.product_code = sales.product_code  -- on the common product code
    WHERE STATUS = 1                        -- which is currently processed (status=1)

    GROUP BY i.product_code
    -- GROUP BY clause to group any multiple product codes that are the same
    -- This is related when performing SELECT (see above)
  ) AS qq

  ON ii.product_code = qq.product_code
  -- Join common columns for updated quantities and actual stock on the
  -- criteria of the product code

  SET stock_quantity = qty_up$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customers_tbl`
--

CREATE TABLE `customers_tbl` (
  `id` int(11) NOT NULL,
  `rfid_no` int(100) DEFAULT NULL,
  `student_no` int(100) DEFAULT NULL,
  `customer_name` varchar(100) NOT NULL,
  `amount_deposited` decimal(64,4) NOT NULL,
  `activated` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers_tbl`
--

INSERT INTO `customers_tbl` (`id`, `rfid_no`, `student_no`, `customer_name`, `amount_deposited`, `activated`) VALUES
(8, NULL, 221819, 'Drake Andrew G. Alonzo', 50.0000, 0),
(9, NULL, 221614, 'Isaiah Gabriel A. Asilo', 50.0000, 0),
(10, NULL, 110030, 'James Blanza', 50.0000, 0),
(11, NULL, 211559, 'Januel V. Casimiro', 50.0000, 0),
(12, NULL, 110006, 'Filbert Faust V. Delos Reyes', 50.0000, 0),
(13, NULL, 211576, 'Mack Veinz V. Fernandez', 50.0000, 0),
(14, NULL, 8, 'Aaron Kier M. Gaffud', 50.0000, 0),
(15, NULL, 150068, 'Xer Allen C.Guevarra', 50.0000, 0),
(16, NULL, 110035, 'Jacob Ferjen C. Ilocto', 50.0000, 0),
(17, NULL, 211544, 'Lohann Joaquim D. Pojenio', 50.0000, 0),
(18, NULL, 110014, 'Aldo Miguel T. Roque', 50.0000, 0),
(19, NULL, 160428, 'Ram Josef R. Sibanta', 50.0000, 0),
(20, NULL, 201380, 'Angelo N. Venturina', 50.0000, 0),
(21, NULL, 160342, 'Ma. Selfina Remiela B. Alfaro', 50.0000, 0),
(22, NULL, 110043, 'Adriel Athalia M. Alvarez', 50.0000, 0),
(23, NULL, 232109, 'Janna Venice B. Calaor', 50.0000, 0),
(24, NULL, 201383, 'Fatima M. Calimag', 50.0000, 0),
(25, NULL, 221790, 'Sofia Danielle S. Escobar', 50.0000, 0),
(26, NULL, 221796, 'Lien Bryce B. Go', 50.0000, 0),
(27, NULL, 201454, 'Princes Jayshiel B. Mendoza', 50.0000, 0),
(28, NULL, 201335, 'Jeiah Nhadine A. Revilloza', 50.0000, 0),
(29, NULL, 211488, 'Maica D. Roncesvalles', 50.0000, 0),
(30, NULL, 110050, 'Lilka Cielo G. Santos', 50.0000, 0),
(31, NULL, 201371, 'Kyan Shin N. Torres', 50.0000, 0),
(32, NULL, 150213, 'Jimmy I. Alicer', 50.0000, 0),
(33, NULL, 110003, 'Dave Aaron D. Cabaron', 50.0000, 0),
(34, NULL, 221777, 'Aidwayne Josh E. Cho', 50.0000, 0),
(35, NULL, 110033, 'Jayden Paul Q. Dela Cruz', 50.0000, 0),
(36, NULL, 110007, 'Christian Paul W. Francisco', 50.0000, 0),
(37, NULL, 100007, 'Stafford Spencer V. Godoy', 50.0000, 0),
(38, NULL, 110058, 'Prince Levince A. Lirio', 50.0000, 0),
(39, NULL, 221695, 'Mario II R. Magtuba', 50.0000, 0),
(40, NULL, 221714, 'Lance Andrei D. Mananguete', 50.0000, 0),
(41, NULL, 160455, 'John Ezekiel A. Peralta', 50.0000, 0),
(42, NULL, 201418, 'Louie G. Santiago', 50.0000, 0),
(43, NULL, 232111, 'Gabriel G. Sarmiento', 50.0000, 0),
(44, NULL, 150187, 'Paolo Raphael G. Tui', 50.0000, 0),
(45, NULL, 221700, 'Shaula Geralyn U. Bautista', 50.0000, 0),
(46, NULL, 201406, 'Julianne Olyn A. Boston', 50.0000, 0),
(47, NULL, 221640, 'Dominique Kraziel B. Dulpina', 50.0000, 0),
(48, NULL, 232098, 'Ashley Jane B. Go', 50.0000, 0),
(49, NULL, 232040, 'Camille D. Guantero', 50.0000, 0),
(50, NULL, 110022, 'Zyfel Hann Lesaca', 50.0000, 0),
(51, NULL, 201339, 'Zyra L. Montefolka', 50.0000, 0),
(52, NULL, 170753, 'Zaynah Mackenzie R. Ramos', 50.0000, 0),
(53, NULL, 211574, 'Jan Danielle L. Salangsang', 50.0000, 0),
(54, NULL, 201452, 'Larainne S. Tinos', 50.0000, 0),
(55, NULL, 201337, 'Amryl Jenice C. Vizcayno', 50.0000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `sales_tbl`
--

CREATE TABLE `sales_tbl` (
  `id` int(11) NOT NULL,
  `sales_id` varchar(30) DEFAULT NULL,
  `product_code` int(10) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `quantity` int(12) DEFAULT NULL,
  `total_price` decimal(12,4) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0 = pending, 1 = completed, 2 = PROCESSED',
  `created_by` varchar(120) DEFAULT NULL,
  `created_datetime` datetime DEFAULT current_timestamp(),
  `completed_datetime` datetime DEFAULT NULL,
  `customer_id` varchar(30) DEFAULT NULL
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

--
-- Dumping data for table `stocks_inventory_tbl`
--

INSERT INTO `stocks_inventory_tbl` (`id`, `product_code`, `name`, `description`, `category`, `unit_cost`, `stock_quantity`, `markup_price`, `unit_price`) VALUES
(23, 101001, 'Nissin Wafer', '', 'Biscuits', 3.1500, 0, 1.8500, 5.0000),
(24, 102001, 'Pillows', '', 'Snacks', 14.0000, 0, 2.0000, 16.0000),
(25, 101002, 'Rebisco Strawberry', '', 'Biscuits', 7.0000, 0, 2.0000, 9.0000),
(26, 102002, 'Tempura', '', 'Snacks', 24.0000, 0, 1.0000, 25.0000),
(37, 102003, 'Oishi Fishda', '', 'Snacks', 30.0000, 0, 2.0000, 32.0000),
(47, 102004, 'Tomi', '', 'Snacks', 12.0000, 0, 2.0000, 14.0000),
(48, 102005, 'EggNog', '', 'Snacks', 15.0000, 0, 1.0000, 16.0000),
(49, 102006, 'Bread Stix', '', 'Snacks', 15.0000, 0, 1.0000, 16.0000),
(50, 104001, 'Monde Special Mamon', '', 'Bread', 18.6700, 0, 1.3300, 20.0000),
(51, 101003, 'SkyFlakes', '', 'Biscuits', 5.8300, 0, 1.1700, 7.0000),
(52, 102007, 'Choco Mucho', '', 'Snacks', 13.0000, 0, 2.0000, 15.0000),
(53, 103001, 'Nestea Lemon', '', 'Drinks', 14.0000, 100, 0.0000, 14.0000);

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
('admin', 'admin', 2, 1, 0),
('smacv', 'smacv', 3, 1, 0),
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
-- Indexes for table `sales_tbl`
--
ALTER TABLE `sales_tbl`
  ADD PRIMARY KEY (`id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `sales_tbl`
--
ALTER TABLE `sales_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;

--
-- AUTO_INCREMENT for table `stocks_inventory_tbl`
--
ALTER TABLE `stocks_inventory_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
