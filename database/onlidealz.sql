-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 04, 2022 at 03:07 PM
-- Server version: 10.5.12-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id18696214_onlidealz`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `reg_id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`reg_id`, `username`, `email`, `password`, `reg_date`) VALUES
(1, 'MADADI', 'madadijimmy2@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '2022-03-29 12:14:31');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `reg` int(11) NOT NULL,
  `serial` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `customer` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `product` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `details` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`reg`, `serial`, `customer`, `product`, `details`, `price`, `quantity`, `image`, `status`, `reg_date`) VALUES
(1, 'NTOSCB6049', 'ONLIDZ-7/2022', 'ONLD-79LS15', 'TV Audio & Videos~Televisions', 12500, 1, 'IMG404609822.PNG', 'Cleared', '2022-03-30 07:01:01'),
(2, 'NTOSCB6049', 'ONLIDZ-7/2022', 'ONLD-21UL2Q', 'Kitchen Supplies~Blender', 6500, 1, 'IMG875799922.PNG', 'Cleared', '2022-03-30 07:01:16'),
(3, 'NTOSCB6049', 'ONLIDZ-7/2022', 'ONLD-1OKZPH', 'Kitchen Supplies~Fridge', 100000, 2, 'IMG1594009362.PNG', 'Cleared', '2022-03-30 07:01:46'),
(4, 'NTOSCB6049', 'ONLIDZ-7/2022', 'ONLD-96MDRF', 'Living Room~Shoe Rack', 2000, 1, 'IMG940955664.PNG', 'Cleared', '2022-03-30 09:28:36'),
(5, 'NGVTDJE1PU', 'ONLIDZ-8/2022', 'ONLD-81QC6G', 'TV Audio & Videos~Home Audio Systems', 9500, 9, 'IMG917323110.PNG', 'Cleared', '2022-03-30 09:38:35'),
(6, 'Pending', 'ONLIDZ-9/2022', 'ONLD-79LS15', 'TV Audio & Videos~Televisions', 12500, 1, 'IMG404609822.PNG', 'Pending', '2022-04-01 09:51:26'),
(7, 'Pending', 'ONLIDZ-9/2022', 'ONLD-1OKZPH', 'Kitchen Supplies~Fridge', 100000, 1, 'IMG1594009362.PNG', 'Pending', '2022-04-01 09:51:41'),
(8, 'S28AMC4GWD', 'ONLIDZ-7/2022', 'ONLD-7NWAJX', 'TV Audio & Videos~Home Audio Systems', 2700, 1, 'IMG1814407143.PNG', 'Cleared', '2022-04-04 07:18:43');

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` int(11) NOT NULL,
  `rate` float DEFAULT NULL,
  `message` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiver` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sender` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `post` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `send_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `reply` varchar(250) COLLATE utf8_unicode_ci DEFAULT 'Reply Pending',
  `reply_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `reg_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fullname` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `comment` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`reg_id`, `fullname`, `username`, `phone`, `address`, `email`, `password`, `status`, `comment`, `reg_date`) VALUES
('ONLIDZ-7/2022', 'Jimmy Nzavo', 'NZAVO', '0110 011 410', 'Meru', 'nzavo@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 1, NULL, '2022-03-29 18:02:42'),
('ONLIDZ-8/2022', 'carol kendi', 'carol', '0724 555 666', 'Embu', 'ck@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 1, NULL, '2022-03-30 09:36:44'),
('ONLIDZ-9/2022', 'Sultan Madadi', 'SULTAN', '0723 843 212', 'Kirinyaga', 'sultan@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 1, NULL, '2022-04-01 09:11:51');

-- --------------------------------------------------------

--
-- Table structure for table `disburse`
--

CREATE TABLE `disburse` (
  `id` int(11) NOT NULL,
  `ind` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mpesa` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `supplier` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `disburse`
--

INSERT INTO `disburse` (`id`, `ind`, `mpesa`, `supplier`, `amount`, `reg_date`) VALUES
(1, 'ZQVC8TK5US', 'RFHJY6346H', 'ONLIDZ-0-2022', 52500, '2022-04-04 10:45:49'),
(2, 'O3LUKJYS6C', 'RGJY5325G5', 'ONLIDZ-0-2022', 52500, '2022-04-04 10:46:14');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `reg_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `license` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fullname` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `comment` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`reg_id`, `license`, `fullname`, `username`, `gender`, `phone`, `address`, `email`, `password`, `status`, `comment`, `reg_date`) VALUES
('ONLIDZ-8-2022', '3214', 'Speedoff Courier', 'DRIVER', 'Other', '0707 995 988', 'P.O.BOX 453, Nairobi', 'speedoffcourier@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 1, NULL, '2022-03-29 18:04:15');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `reg_id` int(11) DEFAULT NULL,
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fullname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `counter_id` int(11) NOT NULL,
  `supplier_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `qnty` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`counter_id`, `supplier_id`, `business_name`, `category`, `type`, `quantity`, `qnty`, `reg_date`) VALUES
(1, 'ONLIDZ-0-2022', 'NUNIX', 'Kitchen Supplies', 'Blender', 15, 0, '2022-04-04 09:25:31');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `pay_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `mpesa` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shipping` float DEFAULT NULL,
  `orders` float DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `original` float DEFAULT NULL,
  `discounted` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `customer_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `delivery` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` float DEFAULT 0,
  `disburse` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `shipper` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `shipment` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `review` varchar(50) COLLATE utf8_unicode_ci DEFAULT '0',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`pay_id`, `mpesa`, `shipping`, `orders`, `discount`, `original`, `discounted`, `quantity`, `customer_id`, `phone`, `delivery`, `location`, `address`, `status`, `disburse`, `shipper`, `shipment`, `customer`, `review`, `reg_date`) VALUES
('NGVTDJE1PU', 'BGD1234DFR', 300, 79301.2, 6198.75, 85800, 79601.2, 9, 'ONLIDZ-8/2022', '0724 555 666', 'Pending', 'Area~ ngara~ Street~ desai~ House~ desai', 'Nairobi', 1, 'Assigned', 'speedoffcourier@gmail.com', 'Delivered', '1', '1', '2022-03-30 09:40:27'),
('NTOSCB6049', 'GAHSHS1517', 300, 204978, 16022.5, 221300, 205278, 5, 'ONLIDZ-7/2022', '0110 011 410', 'Pending', 'Area~ yehsvs~ Street~ meru~ House~ ysus8s', 'Nyeri', 1, 'Assigned', 'speedoffcourier@gmail.com', 'Delivered', '1', '1', '2022-03-30 09:29:10'),
('S28AMC4GWD', 'DFG35G04H5', 300, 2700, 0, 3000, 3000, 1, 'ONLIDZ-7/2022', '0110 011 410', 'Pending', 'Area~ Mombasa~ Street~ Bamburi~ House~ Scholars', 'Mombasa', 1, 'Pending', 'Pending', 'Pending', 'Pending', '0', '2022-04-04 07:19:20');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `category`, `type`, `description`, `image`, `quantity`, `price`, `reg_date`) VALUES
('ONLD-0B5RDW', 'Bathroom Supplies', 'Shower Heads', '12 inches Rain Shower Head with Hand-held Spray', 'IMG1057009117.PNG', 15, 10500, '2022-03-30 07:24:33'),
('ONLD-0X8FVZ', 'Kitchen Supplies', 'Blender', 'electric blender', 'IMG1543571471.PNG', 15, 4500, '2022-04-04 09:51:20'),
('ONLD-0XS6BN', 'Kitchen Supplies', 'Gas Cooker', 'Von 4 Gas Cooker - Black', 'IMG1568571475.PNG', 5, 20500, '2022-03-29 19:03:59'),
('ONLD-1OKZPH', 'Kitchen Supplies', 'Fridge', 'A double door fridge', 'IMG1594009362.PNG', 12, 100000, '2022-03-29 19:12:28'),
('ONLD-21UL2Q', 'Kitchen Supplies', 'Blender', 'Hand Blender Set, 5 in 1 Stick Blender for Kitchen', 'IMG875799922.PNG', 24, 6500, '2022-03-29 19:15:22'),
('ONLD-3WE89K', 'Bedroom Supplies', 'Portable Wardrobe', '3 Column Wooden Portable Wardrobe', 'IMG528374967.PNG', 40, 6800, '2022-03-29 19:16:20'),
('ONLD-4TUFQY', 'Bedroom Supplies', 'Bedding', '100% Cotton Bedding Sets Polyester Bedding Comforter', 'IMG1720410539.PNG', 35, 5500, '2022-03-29 19:17:10'),
('ONLD-5MDGN1', 'Bedroom Supplies', 'Bedding', '100% Cotton Bedding Sets Polyester Bedding Comforter', 'IMG762338059.PNG', 35, 6300, '2022-03-29 19:17:46'),
('ONLD-65MN0X', 'Living Room', 'Shoe Rack', 'Portable Shoe Rack', 'IMG435308681.PNG', 23, 2500, '2022-03-29 19:24:17'),
('ONLD-79LS15', 'TV Audio & Videos', 'Televisions', 'Old generation TV, wooden', 'IMG404609822.PNG', 10, 12500, '2022-03-29 19:01:45'),
('ONLD-7GUKHC', 'Living Room', 'Furniture', 'Executive Revolving Office Chair', 'IMG324302916.PNG', 15, 7500, '2022-03-29 19:24:44'),
('ONLD-7NWAJX', 'TV Audio & Videos', 'Home Audio Systems', 'subwoofer', 'IMG1814407143.PNG', 9, 2700, '2022-03-30 09:52:22'),
('ONLD-81QC6G', 'TV Audio & Videos', 'Home Audio Systems', 'Miji Home Theater System 3.1', 'IMG917323110.PNG', 1, 9500, '2022-03-29 19:02:32'),
('ONLD-84PNT2', 'Bathroom Supplies', 'Laundry Bucket', 'Plastic Laundry Bucket', 'IMG1825732956.PNG', 25, 1150, '2022-03-29 19:27:08'),
('ONLD-8QVJIU', 'Kitchen Supplies', 'Fridge', 'Hisense Single Door Refrigerator', 'IMG1094771553.PNG', 5, 37000, '2022-04-04 07:25:27'),
('ONLD-96MDRF', 'Living Room', 'Shoe Rack', 'Portable Shoe Rack', 'IMG940955664.PNG', 22, 2000, '2022-03-29 19:27:42'),
('ONLD-98COH2', 'Kitchen Supplies', 'Gas Cooker', 'Two burner gas stove. Comes with free gas pipe', 'IMG1323875719.PNG', 35, 100500, '2022-03-29 19:03:41'),
('ONLD-9PUQXG', 'TV Audio & Videos', 'Home Audio Systems', 'system', 'IMG958482256.PNG', 2, 22500, '2022-04-04 07:43:56');

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `purchase_id` int(11) NOT NULL,
  `counter_id` int(11) DEFAULT NULL,
  `supplier_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_name` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `comment` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'Pending',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`purchase_id`, `counter_id`, `supplier_id`, `business_name`, `category`, `type`, `price`, `quantity`, `description`, `image`, `status`, `comment`, `payment`, `reg_date`) VALUES
(1, 1, 'ONLIDZ-0-2022', 'NUNIX', 'Kitchen Supplies', 'Blender', 3500, 15, 'electric blender', 'IMG1543571471.PNG', 'Approved', 'nice', 'Disbursed', '2022-04-04 09:30:04');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `reg` int(11) NOT NULL,
  `quality` float DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `value` float DEFAULT NULL,
  `reference` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(50) COLLATE utf8_unicode_ci DEFAULT 'No',
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`reg`, `quality`, `rate`, `price`, `value`, `reference`, `customer`, `name`, `phone`, `status`, `reg_date`) VALUES
(1, 5, 5, 1, 3, 'NGVTDJE1PU', 'ONLIDZ-8/2022', 'carol kendi', '0724 555 666', 'No', '2022-03-30 09:47:24'),
(2, 5, 3.5, 3.5, 4, 'NTOSCB6049', 'ONLIDZ-7/2022', 'Jimmy Nzavo', '0110 011 410', 'No', '2022-04-04 07:54:47'),
(3, 5, 3.5, 3.5, 4, 'NTOSCB6049', 'ONLIDZ-7/2022', 'Jimmy Nzavo', '0110 011 410', 'No', '2022-04-04 07:54:48');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `reg_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fullname` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `comment` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`reg_id`, `fullname`, `username`, `phone`, `email`, `password`, `role`, `status`, `comment`, `reg_date`) VALUES
('ONLIDZ-4-2022', 'Gralia Makama', 'INVENTORY', '0702 224 906', 'makama@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 'Inventory Manager', 1, NULL, '2022-03-29 18:40:45'),
('ONLIDZ-5-2022', 'Neill Mutanyi', 'FINANCE', '0718 794 658', 'mutanyi@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', 'Finance Manager', 1, NULL, '2022-03-29 18:41:29');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `reg_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `fullname` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_registry` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `existence` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT 0,
  `comment` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `reg_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`reg_id`, `fullname`, `username`, `phone`, `address`, `email`, `password`, `business_registry`, `business_name`, `existence`, `status`, `comment`, `reg_date`) VALUES
('ONLIDZ-0-2022', 'NUNIX', 'NUNIX', '0710 996 739', 'P.O.BOX 835, Kisumu', 'nunix@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '5623', 'NUNIX', 'Kitchen Supplies', 1, NULL, '2022-03-29 18:06:28'),
('ONLIDZ-1-2022', 'GENERIC', 'GENERIC', '0723 841 34', 'P.O.BOX 956, Kiambu', 'generic@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '6752', 'GENERIC', 'Living Room', 1, NULL, '2022-03-29 18:14:05'),
('ONLIDZ-2-2022', 'SUNDABEST', 'SUNDABEST', '0702 394 233', 'P.O.BOX 738, Meru', 'sundabest@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '7625', 'SBEST', 'Bedroom Supplies', 1, NULL, '2022-03-29 18:15:35'),
('ONLIDZ-3-2022', 'LYONS', 'LYONS', '0707 668 355', 'P.O.BOX 658, Garissa', 'lyons@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '5843', 'LYONS', 'Bathroom Supplies', 1, NULL, '2022-03-29 18:16:38'),
('ONLIDZ-9-2022', 'SOKANY', 'SUPPLIER', '0790 691 620', 'P.O.BOX 974, Kilifi', 'sokany@gmail.com', 'cdf456626c0a989498f883aa6f281d7c', '21328', 'SOKANY', 'TV Audio & Videos', 1, NULL, '2022-03-29 18:05:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`reg_id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`reg`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`reg_id`);

--
-- Indexes for table `disburse`
--
ALTER TABLE `disburse`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`reg_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`counter_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`pay_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`purchase_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`reg`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`reg_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`reg_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `reg_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `reg` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `disburse`
--
ALTER TABLE `disburse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `counter_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `purchase_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `reg` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
