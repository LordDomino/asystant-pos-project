-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 23, 2024 at 05:18 AM
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

--
-- Dumping data for table `profit_tbl`
--

INSERT INTO `profit_tbl` (`id`, `product_code`, `name`, `category`, `unit_cost`, `markup_price`, `unit_price`, `units_sold`, `revenue`, `profit`, `total_profit`) VALUES
(0, 1, 'Item', 'Uncategorized', 0.0000, 0.0000, 0.0000, 0, 0.0000, 0.0000, 0.0000);

-- --------------------------------------------------------

--
-- Table structure for table `stocks_inventory_tbl`
--

CREATE TABLE `stocks_inventory_tbl` (
  `id` int(11) NOT NULL,
  `product_code` int(10) NOT NULL,
  `order_date` datetime NOT NULL DEFAULT current_timestamp(),
  `name` varchar(64) NOT NULL,
  `description` varchar(255) NOT NULL,
  `category` varchar(100) NOT NULL DEFAULT 'Uncategorized',
  `unit_cost` decimal(65,4) NOT NULL,
  `stock_quantity` int(10) NOT NULL,
  `total_cost` decimal(65,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stocks_inventory_tbl`
--

INSERT INTO `stocks_inventory_tbl` (`id`, `product_code`, `order_date`, `name`, `description`, `category`, `unit_cost`, `stock_quantity`, `total_cost`) VALUES
(0, 1, '2024-04-23 11:12:10', 'Item', 'Item description', 'Uncategorized', 0.0000, 0, 0.0000);

-- --------------------------------------------------------

--
-- Table structure for table `user_accounts`
--

CREATE TABLE `user_accounts` (
  `username` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `access_level` int(1) NOT NULL,
  `activated` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_accounts`
--

INSERT INTO `user_accounts` (`username`, `password`, `access_level`, `activated`) VALUES
('%SUPERADMIN%', '%SUPERADMIN%', 1, 1),
('admin', 'admin', 2, 1),
('user', 'user', 3, 1);

--
-- Indexes for dumped tables
--

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
  ADD UNIQUE KEY `product` (`product_code`,`name`,`category`) USING BTREE;

--
-- Indexes for table `user_accounts`
--
ALTER TABLE `user_accounts`
  ADD PRIMARY KEY (`username`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `profit_tbl`
--
ALTER TABLE `profit_tbl`
  ADD CONSTRAINT `profit_tbl_ibfk_1` FOREIGN KEY (`product_code`,`name`,`category`) REFERENCES `stocks_inventory_tbl` (`product_code`, `name`, `category`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
