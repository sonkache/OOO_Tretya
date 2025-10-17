-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/

CREATE DATABASE IF NOT EXISTS `paperdb`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `paperdb`;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Окт 17 2025 г., 19:33
-- Версия сервера: 10.4.32-MariaDB
-- Версия PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `paperdb`
--

-- --------------------------------------------------------

--
-- Структура таблицы `agent`
--

CREATE TABLE `agent` (
  `agent_id` int(11) NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `legal_address` varchar(255) NOT NULL,
  `inn` varchar(32) NOT NULL,
  `kpp` varchar(32) DEFAULT NULL,
  `director_name` varchar(255) NOT NULL,
  `phone` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `logo_path` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `agentproductsale`
--

CREATE TABLE `agentproductsale` (
  `sale_id` int(11) NOT NULL,
  `agent_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `sale_date` date NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `material`
--

CREATE TABLE `material` (
  `material_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `material_type_id` int(11) NOT NULL,
  `package_quantity` int(11) NOT NULL,
  `unit_id` int(11) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `min_quantity` int(11) NOT NULL,
  `cost` decimal(18,2) NOT NULL,
  `description` text DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `material`
--

INSERT INTO `material` (`material_id`, `name`, `material_type_id`, `package_quantity`, `unit_id`, `stock_quantity`, `min_quantity`, `cost`, `description`, `image_path`) VALUES
(1, 'Спрессованный материал серый 0x2', 3, 5, 3, 140, 49, 33128.00, NULL, NULL),
(2, 'Рулон бумаги зеленый 1x1', 4, 3, 3, 634, 36, 26841.00, NULL, NULL),
(3, 'Спрессованный материал розовый 2x1', 3, 8, 3, 636, 21, 52606.00, NULL, NULL),
(4, 'Гранулы для бумаги розовый 2x2', 1, 10, 2, 661, 16, 10608.00, NULL, NULL),
(5, 'Спрессованный материал белый 1x1', 3, 7, 1, 843, 16, 43440.00, NULL, NULL),
(6, 'Рулон бумаги белый 1x0', 4, 1, 3, 501, 49, 15833.00, NULL, NULL),
(7, 'Переработка бумаги синий 0x3', 2, 9, 3, 958, 32, 14180.00, NULL, NULL),
(8, 'Рулон бумаги цветной 2x0', 4, 3, 3, 386, 46, 35544.00, NULL, NULL),
(9, 'Рулон бумаги зеленый 2x2', 4, 8, 1, 593, 20, 55016.00, NULL, NULL),
(10, 'Рулон бумаги белый 3x2', 4, 4, 1, 16, 31, 53759.00, NULL, NULL),
(11, 'Переработка бумаги серый 3x3', 2, 10, 3, 89, 20, 49391.00, NULL, NULL),
(12, 'Гранулы для бумаги синий 1x2', 1, 6, 2, 112, 34, 25624.00, NULL, NULL),
(13, 'Рулон бумаги серый 3x3', 4, 3, 3, 596, 8, 50247.00, NULL, NULL),
(14, 'Спрессованный материал цветной 1x3', 3, 3, 3, 772, 40, 51672.00, NULL, NULL),
(15, 'Рулон бумаги синий 2x2', 4, 6, 1, 363, 47, 38450.00, NULL, NULL),
(16, 'Гранулы для бумаги розовый 0x3', 1, 4, 2, 379, 41, 3868.00, NULL, NULL),
(17, 'Переработка бумаги цветной 1x3', 2, 6, 1, 32, 34, 37930.00, NULL, NULL),
(18, 'Гранулы для бумаги цветной 0x1', 1, 5, 2, 759, 28, 9216.00, NULL, NULL),
(19, 'Рулон бумаги цветной 1x2', 4, 10, 3, 246, 37, 5016.00, NULL, NULL),
(20, 'Переработка бумаги серый 1x2', 2, 8, 3, 516, 49, 35981.00, NULL, NULL),
(21, 'Спрессованный материал синий 0x2', 3, 3, 1, 514, 40, 6555.00, NULL, NULL),
(22, 'Переработка бумаги синий 1x2', 2, 4, 3, 81, 32, 47873.00, NULL, NULL),
(23, 'Спрессованный материал цветной 1x2', 3, 1, 3, 429, 47, 15802.00, NULL, NULL),
(24, 'Рулон бумаги розовый 2x1', 4, 1, 3, 349, 9, 36163.00, NULL, NULL),
(25, 'Рулон бумаги белый 3x3', 4, 2, 1, 201, 46, 51261.00, NULL, NULL),
(26, 'Рулон бумаги цветной 2x2', 4, 10, 1, 534, 46, 50776.00, NULL, NULL),
(27, 'Спрессованный материал розовый 2x3', 3, 3, 3, 552, 7, 4657.00, NULL, NULL),
(28, 'Рулон бумаги розовый 3x2', 4, 9, 3, 144, 41, 51776.00, NULL, NULL),
(29, 'Переработка бумаги синий 0x1', 2, 7, 3, 97, 30, 47937.00, NULL, NULL),
(30, 'Спрессованный материал розовый 1x2', 3, 7, 3, 509, 26, 10604.00, NULL, NULL),
(31, 'Рулон бумаги цветной 1x3', 4, 10, 1, 149, 13, 22724.00, NULL, NULL),
(32, 'Рулон бумаги синий 3x2', 4, 9, 3, 508, 16, 25561.00, NULL, NULL),
(33, 'Рулон бумаги зеленый 2x3', 4, 2, 3, 181, 35, 45388.00, NULL, NULL),
(34, 'Гранулы для бумаги розовый 0x2', 1, 7, 1, 37, 50, 17227.00, NULL, NULL),
(35, 'Переработка бумаги цветной 0x0', 2, 9, 3, 508, 19, 51147.00, NULL, NULL),
(36, 'Переработка бумаги серый 0x2', 2, 3, 3, 167, 17, 10248.00, NULL, NULL),
(37, 'Гранулы для бумаги цветной 0x3', 1, 10, 1, 962, 33, 35922.00, NULL, NULL),
(38, 'Рулон бумаги зеленый 3x3', 4, 3, 3, 861, 45, 37283.00, NULL, NULL),
(39, 'Рулон бумаги белый 0x3', 4, 2, 3, 933, 50, 7253.00, NULL, NULL),
(40, 'Рулон бумаги синий 3x1', 4, 3, 3, 720, 11, 44675.00, NULL, NULL),
(41, 'Спрессованный материал белый 3x3', 3, 8, 3, 942, 12, 17600.00, NULL, NULL),
(42, 'Рулон бумаги белый 1x3', 4, 2, 1, 382, 10, 32770.00, NULL, NULL),
(43, 'Переработка бумаги белый 3x0', 2, 10, 3, 247, 11, 39500.00, NULL, NULL),
(44, 'Гранулы для бумаги синий 1x3', 1, 10, 2, 841, 18, 38700.00, NULL, NULL),
(45, 'Спрессованный материал синий 3x1', 3, 10, 3, 270, 50, 38809.00, NULL, NULL),
(46, 'Спрессованный материал цветной 0x0', 3, 6, 3, 754, 24, 4611.00, NULL, NULL),
(47, 'Переработка бумаги цветной 0x1', 2, 8, 3, 833, 34, 53875.00, NULL, NULL),
(48, 'Спрессованный материал цветной 1x0', 3, 8, 3, 856, 26, 12817.00, NULL, NULL),
(49, 'Переработка бумаги синий 0x2', 2, 9, 1, 709, 47, 23157.00, NULL, NULL),
(50, 'Переработка бумаги белый 2x0', 2, 5, 3, 794, 17, 21637.00, NULL, NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `materialtype`
--

CREATE TABLE `materialtype` (
  `material_type_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `defect_percent` decimal(6,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `materialtype`
--

INSERT INTO `materialtype` (`material_type_id`, `name`, `defect_percent`) VALUES
(1, 'Гранулы', NULL),
(2, 'Нарезка', NULL),
(3, 'Пресс', NULL),
(4, 'Рулон', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `article` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_type_id` int(11) NOT NULL,
  `workshop_id` int(11) DEFAULT NULL,
  `min_agent_price` decimal(18,2) NOT NULL,
  `people_required` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `package_height` int(11) DEFAULT NULL,
  `weight_net` decimal(12,3) DEFAULT NULL,
  `weight_gross` decimal(12,3) DEFAULT NULL,
  `certificate_path` varchar(255) DEFAULT NULL,
  `standard_no` varchar(64) DEFAULT NULL,
  `production_time_min` int(11) DEFAULT NULL,
  `cost_price` decimal(18,2) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `product`
--

INSERT INTO `product` (`product_id`, `article`, `name`, `product_type_id`, `workshop_id`, `min_agent_price`, `people_required`, `width`, `length`, `package_height`, `weight_net`, `weight_gross`, `certificate_path`, `standard_no`, `production_time_min`, `cost_price`, `description`, `image_path`, `is_active`) VALUES
(1, '334385', 'Полотенце 28М Клубника', 6, NULL, 9208.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_20.jpg', 1),
(2, '337632', 'Набор 24М Дыня', 2, 5, 11471.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(3, '259548', 'Набор 9М Бриз', 2, 5, 2317.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(4, '278721', 'Набор 50М Клубника', 3, 1, 14220.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(5, '264493', 'Полотенце 24М Дыня', 2, NULL, 10479.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(6, '366160', 'Бумага 21М Дыня', 5, NULL, 11064.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_2.jpg', 1),
(7, '292358', 'Полотенце 37М Клубника', 4, 2, 3961.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(8, '289779', 'Набор 6М Бриз', 4, NULL, 14154.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(9, '442634', 'Бумага 37М Клубника', 3, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_14.jpg', 1),
(10, '385037', 'Набор 41М Дыня', 4, NULL, 3295.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_21.jpg', 1),
(11, '444337', 'Бумага 26М Ваниль', 5, 4, 3007.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(12, '454479', 'Бумага 50М Ваниль', 3, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(13, '434113', 'Набор 25М Бриз', 4, 4, 13047.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(14, '379800', 'Бумага 32М Бриз', 3, NULL, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(15, '239349', 'Полотенце 41М Дыня', 3, NULL, 6238.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(16, '446348', 'Бумага 9М Ваниль', 3, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(17, '260492', 'Бумага 3М Бриз', 2, 9, 8278.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(18, '256862', 'Бумага 5М Дыня', 4, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_5.jpg', 1),
(19, '259488', 'Полотенце 16М Ваниль', 5, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(20, '422185', 'Бумага 44М Бриз', 4, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(21, '382063', 'Набор 19М Дыня', 5, 7, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(22, '334943', 'Бумага 29М Клубника', 4, NULL, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(23, '238686', 'Полотенце 27М Дыня', 2, NULL, 7864.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(24, '450659', 'Набор 11М Дыня', 2, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_16.jpg', 1),
(25, '292206', 'Полотенце 48М Клубника', 5, NULL, 9801.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(26, '418298', 'Полотенце 16М Бриз', 6, NULL, 7342.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(27, '309862', 'Полотенце 14М Клубника', 2, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_17.jpg', 1),
(28, '425641', 'Полотенце 43М Клубника', 5, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(29, '277950', 'Набор 19М Бриз', 3, 9, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(30, '351062', 'Набор 32М Клубника', 5, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(31, '279801', 'Набор 26М Дыня', 2, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(32, '449359', 'Бумага 15М Клубника', 2, NULL, 10009.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(33, '303994', 'Полотенце 45М Бриз', 3, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_6.jpg', 1),
(34, '414595', 'Набор 50М Бриз', 2, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_8.jpg', 1),
(35, '376834', 'Набор 16М Дыня', 4, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(36, '374276', 'Бумага 45М Бриз', 3, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(37, '400484', 'Бумага 45М Клубника', 5, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(38, '262257', 'Бумага 16М Дыня', 5, 2, 9107.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(39, '346154', 'Набор 40М Дыня', 3, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(40, '347239', 'Бумага 33М Бриз', 2, NULL, 13767.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(41, '352034', 'Полотенце 45М Клубника', 6, 5, 11939.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(42, '443538', 'Набор 43М Клубника', 4, 10, 13786.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(43, '316033', 'Набор 34М Ваниль', 5, NULL, 13533.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(44, '268393', 'Бумага 32М Дыня', 4, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(45, '426804', 'Бумага 12М Бриз', 4, NULL, 10703.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(46, '324941', 'Полотенце 6М Ваниль', 2, NULL, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(47, '378723', 'Полотенце 47М Клубника', 4, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(48, '365615', 'Полотенце 15М Бриз', 4, NULL, 13013.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(49, '440942', 'Набор 9М Дыня', 4, NULL, 2580.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(50, '441239', 'Набор 17М Клубника', 5, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(51, '278382', 'Набор 5М Бриз', 2, NULL, 7325.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(52, '344868', 'Набор 29М Ваниль', 6, NULL, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_4.jpg', 1),
(53, '449401', 'Набор 31М Дыня', 4, 10, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(54, '349802', 'Набор 45М Бриз', 2, 8, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(55, '348651', 'Бумага 13М Дыня', 6, 8, 3477.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(56, '296007', 'Набор 24М Клубника', 3, 3, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(57, '431234', 'Набор 10М Дыня', 4, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_22.jpg', 1),
(58, '456129', 'Бумага 12М Клубника', 4, 4, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_1.jpg', 1),
(59, '400418', 'Полотенце 11М Дыня', 5, 7, 11660.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_25.jpg', 1),
(60, '390914', 'Набор 42М Дыня', 3, 10, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_13.jpg', 1),
(61, '345239', 'Бумага 5М Бриз', 4, NULL, 9243.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(62, '373939', 'Набор 14М Дыня', 6, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(63, '252485', 'Бумага 33М Клубника', 6, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_0.jpg', 1),
(64, '381110', 'Набор 34М Клубника', 2, NULL, 2372.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(65, '331688', 'Полотенце 14М Дыня', 2, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(66, '326200', 'Бумага 40М Бриз', 2, NULL, 12927.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_19.jpg', 1),
(67, '253218', 'Бумага 20М Клубника', 4, NULL, 11684.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(68, '340583', 'Бумага 50М Бриз', 3, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_15.jpg', 1),
(69, '437519', 'Полотенце 23М Ваниль', 6, 10, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(70, '240580', 'Набор 8М Дыня', 4, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(71, '275591', 'Бумага 10М Клубника', 2, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_7.jpg', 1),
(72, '281425', 'Набор 41М Клубника', 5, 6, 9908.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_23.jpg', 1),
(73, '261465', 'Набор 10М Ваниль', 5, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_12.jpg', 1),
(74, '309067', 'Полотенце 17М Бриз', 2, 6, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_24.jpg', 1),
(75, '278463', 'Полотенце 27М Ваниль', 2, NULL, 0.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_10.jpg', 1),
(76, '310212', 'Полотенце 50М Ваниль', 6, 10, 13204.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_11.jpg', 1),
(77, '377042', 'Набор 20М Бриз', 4, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_9.jpg', 1),
(78, '294441', 'Полотенце 47М Дыня', 5, NULL, 11460.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(79, '332522', 'Бумага 29М Дыня', 6, NULL, 5439.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_18.jpg', 1),
(80, '357053', 'Полотенце 44М Ваниль', 4, NULL, 0.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(81, '397143', 'Полотенце 7М Ваниль', 3, NULL, 2868.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(82, '414339', 'Бумага 49М Бриз', 6, 6, 10786.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(83, '350515', 'Полотенце 7М Бриз', 2, NULL, 4986.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(84, '258695', 'Полотенце 25М Бриз', 6, 8, 3631.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(85, '403054', 'Полотенце 17М Ваниль', 5, 9, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(86, '310507', 'Бумага 14М Бриз', 3, 3, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(87, '353930', 'Набор 40М Бриз', 2, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(88, '360424', 'Набор 22М Клубника', 6, 3, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(89, '349861', 'Бумага 13М Бриз', 2, 6, 3842.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(90, '290729', 'Бумага 7М Ваниль', 5, NULL, 0.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(91, '374263', 'Набор 24М Ваниль', 4, NULL, 3907.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(92, '330360', 'Набор 43М Дыня', 6, 4, 5867.00, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(93, '297840', 'Полотенце 33М Бриз', 5, NULL, 4611.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(94, '349614', 'Набор 12М Бриз', 3, 7, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'productspaper_3.jpg', 1),
(95, '416066', 'Бумага 38М Ваниль', 6, NULL, 0.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(96, '285792', 'Бумага 20М Бриз', 3, NULL, 14370.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(97, '298632', 'Бумага 28М Дыня', 3, 4, 14671.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'не указано', 1),
(98, '446103', 'Бумага 10М Дыня', 4, NULL, 0.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'нет', 1),
(99, '443382', 'Набор 22М Бриз', 3, NULL, 4501.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1),
(100, '331270', 'Набор 18М Ваниль', 6, NULL, 11171.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'отсутствует', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `productmaterial`
--

CREATE TABLE `productmaterial` (
  `product_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `quantity` decimal(18,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `productmaterial`
--

INSERT INTO `productmaterial` (`product_id`, `material_id`, `quantity`) VALUES
(1, 3, 13.000),
(1, 24, 9.000),
(1, 25, 4.000),
(2, 23, 15.000),
(10, 2, 16.000),
(10, 18, 18.000),
(11, 39, 12.000),
(11, 40, 15.000),
(11, 46, 11.000),
(11, 48, 19.000),
(16, 22, 15.000),
(16, 40, 8.000),
(18, 7, 19.000),
(18, 13, 6.000),
(18, 34, 5.000),
(19, 7, 8.000),
(19, 21, 4.000),
(19, 25, 20.000),
(24, 12, 6.000),
(24, 14, 11.000),
(27, 5, 5.000),
(27, 46, 7.000),
(28, 25, 8.000),
(32, 29, 15.000),
(32, 43, 4.000),
(33, 10, 4.000),
(33, 21, 5.000),
(33, 28, 10.000),
(34, 26, 16.000),
(34, 40, 6.000),
(37, 6, 4.000),
(40, 18, 18.000),
(40, 32, 20.000),
(41, 13, 3.000),
(41, 17, 3.000),
(41, 32, 4.000),
(41, 49, 14.000),
(44, 13, 3.000),
(47, 12, 14.000),
(47, 18, 3.000),
(47, 28, 5.000),
(47, 33, 4.000),
(47, 40, 14.000),
(49, 43, 4.000),
(52, 27, 6.000),
(52, 28, 17.000),
(52, 39, 12.000),
(52, 44, 11.000),
(55, 14, 9.000),
(57, 42, 4.000),
(58, 15, 19.000),
(58, 24, 18.000),
(59, 42, 3.000),
(60, 37, 17.000),
(63, 24, 20.000),
(63, 25, 3.000),
(63, 35, 1.000),
(63, 37, 10.000),
(64, 16, 1.000),
(64, 34, 14.000),
(66, 12, 18.000),
(66, 13, 1.000),
(66, 32, 16.000),
(66, 44, 12.000),
(66, 48, 18.000),
(67, 5, 10.000),
(67, 27, 6.000),
(67, 42, 6.000),
(68, 8, 2.000),
(70, 2, 1.000),
(70, 8, 1.000),
(70, 16, 16.000),
(71, 6, 1.000),
(71, 11, 4.000),
(71, 26, 11.000),
(72, 18, 6.000),
(72, 30, 20.000),
(72, 44, 6.000),
(76, 13, 10.000),
(76, 37, 10.000),
(76, 46, 19.000),
(77, 35, 10.000),
(77, 46, 19.000),
(78, 16, 1.000),
(79, 38, 15.000),
(79, 41, 8.000),
(90, 40, 15.000),
(92, 5, 13.000),
(92, 16, 11.000),
(92, 38, 19.000),
(92, 44, 16.000),
(94, 6, 3.000),
(94, 18, 20.000),
(94, 42, 4.000),
(95, 11, 3.000),
(95, 47, 8.000),
(96, 27, 14.000),
(99, 2, 3.000),
(99, 36, 2.000),
(100, 44, 8.000);

-- --------------------------------------------------------

--
-- Структура таблицы `producttype`
--

CREATE TABLE `producttype` (
  `product_type_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `coefficient` decimal(10,4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `producttype`
--

INSERT INTO `producttype` (`product_type_id`, `name`, `coefficient`) VALUES
(2, 'Два слоя', 1.0000),
(3, 'Детская', 1.0000),
(4, 'Один слой', 1.0000),
(5, 'Супер мягкая', 1.0000),
(6, 'Три слоя', 1.0000);

-- --------------------------------------------------------

--
-- Структура таблицы `unit`
--

CREATE TABLE `unit` (
  `unit_id` int(11) NOT NULL,
  `name` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `unit`
--

INSERT INTO `unit` (`unit_id`, `name`) VALUES
(1, 'кг'),
(2, 'л'),
(3, 'м');

-- --------------------------------------------------------

--
-- Структура таблицы `workshop`
--

CREATE TABLE `workshop` (
  `workshop_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `workshop`
--

INSERT INTO `workshop` (`workshop_id`, `number`, `name`) VALUES
(1, 1, NULL),
(2, 2, NULL),
(3, 3, NULL),
(4, 4, NULL),
(5, 5, NULL),
(6, 6, NULL),
(7, 7, NULL),
(8, 8, NULL),
(9, 9, NULL),
(10, 10, NULL);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `agent`
--
ALTER TABLE `agent`
  ADD PRIMARY KEY (`agent_id`),
  ADD UNIQUE KEY `uk_agent_inn` (`inn`);

--
-- Индексы таблицы `agentproductsale`
--
ALTER TABLE `agentproductsale`
  ADD PRIMARY KEY (`sale_id`),
  ADD KEY `idx_sale_product` (`product_id`),
  ADD KEY `idx_sale_agent_date` (`agent_id`,`sale_date`);

--
-- Индексы таблицы `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`material_id`),
  ADD KEY `idx_material_type_id` (`material_type_id`),
  ADD KEY `idx_material_unit_id` (`unit_id`);

--
-- Индексы таблицы `materialtype`
--
ALTER TABLE `materialtype`
  ADD PRIMARY KEY (`material_type_id`),
  ADD UNIQUE KEY `uk_materialtype_name` (`name`);

--
-- Индексы таблицы `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD UNIQUE KEY `uk_product_article` (`article`),
  ADD KEY `idx_product_type_id` (`product_type_id`),
  ADD KEY `idx_product_workshop_id` (`workshop_id`);

--
-- Индексы таблицы `productmaterial`
--
ALTER TABLE `productmaterial`
  ADD PRIMARY KEY (`product_id`,`material_id`),
  ADD KEY `idx_pm_material_id` (`material_id`);

--
-- Индексы таблицы `producttype`
--
ALTER TABLE `producttype`
  ADD PRIMARY KEY (`product_type_id`),
  ADD UNIQUE KEY `uk_producttype_name` (`name`);

--
-- Индексы таблицы `unit`
--
ALTER TABLE `unit`
  ADD PRIMARY KEY (`unit_id`),
  ADD UNIQUE KEY `uk_unit_name` (`name`);

--
-- Индексы таблицы `workshop`
--
ALTER TABLE `workshop`
  ADD PRIMARY KEY (`workshop_id`),
  ADD UNIQUE KEY `uk_workshop_number` (`number`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `agent`
--
ALTER TABLE `agent`
  MODIFY `agent_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `agentproductsale`
--
ALTER TABLE `agentproductsale`
  MODIFY `sale_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `material`
--
ALTER TABLE `material`
  MODIFY `material_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT для таблицы `materialtype`
--
ALTER TABLE `materialtype`
  MODIFY `material_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT для таблицы `producttype`
--
ALTER TABLE `producttype`
  MODIFY `product_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `unit`
--
ALTER TABLE `unit`
  MODIFY `unit_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `workshop`
--
ALTER TABLE `workshop`
  MODIFY `workshop_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `agentproductsale`
--
ALTER TABLE `agentproductsale`
  ADD CONSTRAINT `fk_sale_agent` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`agent_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_sale_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `material`
--
ALTER TABLE `material`
  ADD CONSTRAINT `fk_material_type` FOREIGN KEY (`material_type_id`) REFERENCES `materialtype` (`material_type_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_material_unit` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`unit_id`) ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_type` FOREIGN KEY (`product_type_id`) REFERENCES `producttype` (`product_type_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_product_workshop` FOREIGN KEY (`workshop_id`) REFERENCES `workshop` (`workshop_id`) ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `productmaterial`
--
ALTER TABLE `productmaterial`
  ADD CONSTRAINT `fk_pm_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_pm_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
