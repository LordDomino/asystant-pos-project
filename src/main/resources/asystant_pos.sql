-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2024 at 06:03 PM
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `spRecomupteInventory` ()   update stocks_inventory_tbl ii

JOIN 
(
    select sum(i.stock_quantity) - sum(p.quantity) qty_up  , i.product_code
    from stocks_inventory_tbl i 
    inner join sales_tbl p ON i.product_code = p.item_code
    WHERE status = 1
    Group by i.product_code
) qq 
on ii.item_code = qq.product_code
set stock_quantity = qty_up$$

DELIMITER ;

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
-- Table structure for table `sales_tbl`
--

CREATE TABLE `sales_tbl` (
  `sales_id` varchar(30) DEFAULT NULL,
  `item_code` int(10) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `quantity` int(12) DEFAULT NULL,
  `total_price` decimal(12,4) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0 = pending, 1 = completed',
  `created_by` varchar(120) DEFAULT NULL,
  `created_datetime` datetime DEFAULT current_timestamp(),
  `completed_datetime` datetime DEFAULT NULL,
  `customer_id` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_tbl`
--

INSERT INTO `sales_tbl` (`sales_id`, `item_code`, `item_name`, `quantity`, `total_price`, `status`, `created_by`, `created_datetime`, `completed_datetime`, `customer_id`) VALUES
('', 123456, 'Item1', 1, 102.0000, 1, 'user', '2024-04-26 23:47:57', NULL, NULL),
(NULL, 123457, 'Item2', 1, 102.0000, 1, 'user', '2024-04-26 23:50:09', '2024-04-26 23:50:09', NULL),
(NULL, 123457, 'Item2', 10, 2020.0000, NULL, 'user', '2024-04-26 23:51:11', '2024-04-26 23:51:11', NULL),
(NULL, 123457, 'Item2', 1, 102.0000, NULL, 'user', '2024-04-26 23:51:28', '2024-04-26 23:51:28', NULL),
(NULL, 123458, 'Item3', 1, 102.0000, NULL, 'user', '2024-04-26 23:51:38', '2024-04-26 23:51:38', NULL);

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
(20, 123456, 'Item1', '', 'Uncategorized', 100.0000, 100, 2.0000, 102.0000),
(21, 123457, 'Item2', '', 'Uncategorized', 100.0000, 100, 2.0000, 102.0000),
(22, 123458, 'Item3', '', 'Uncategorized', 100.0000, 100, 2.0000, 102.0000);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `profit_tbl`
--
ALTER TABLE `profit_tbl`
  ADD CONSTRAINT `profit_tbl_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `stocks_inventory_tbl` (`product_code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
