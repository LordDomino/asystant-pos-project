-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2024 at 07:07 PM
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `spProcessOrderInventory` ()
  UPDATE sales_tbl 
  SET STATUS = 2
  WHERE STATUS = 1$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `spRecomputeInventory` ()
  UPDATE stocks_inventory_tbl AS ii
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
  -- Update the stock quantity based on the computed new quantity

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

--
-- Dumping data for table `sales_tbl`
--

INSERT INTO `sales_tbl` (`sales_id`, `product_code`, `item_name`, `quantity`, `total_price`, `status`, `created_by`, `created_datetime`, `completed_datetime`, `customer_id`) VALUES
('', 123456, 'Item1', 1, 102.0000, 2, 'user', '2024-04-26 23:47:57', NULL, NULL),
(NULL, 123457, 'Item2', 1, 102.0000, 2, 'user', '2024-04-26 23:50:09', '2024-04-26 23:50:09', NULL),
(NULL, 123457, 'Item2', 10, 2020.0000, 2, 'user', '2024-04-26 23:51:11', '2024-04-26 23:51:11', NULL),
(NULL, 123457, 'Item2', 1, 102.0000, 2, 'user', '2024-04-26 23:51:28', '2024-04-26 23:51:28', NULL),
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
(20, 123456, 'Item1', '', 'Uncategorized', 100.0000, 99, 2.0000, 102.0000),
(21, 123457, 'Item2', '', 'Uncategorized', 100.0000, 88, 2.0000, 102.0000),
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
