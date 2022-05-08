-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2015 at 11:56 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `entertainment`
--

-- --------------------------------------------------------

--
-- Table structure for table `feature_ringtone`
--

CREATE TABLE IF NOT EXISTS `feature_ringtone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ringtone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `feature_ringtone`
--

INSERT INTO `feature_ringtone` (`id`, `ringtone`) VALUES
(1, '1,2');

-- --------------------------------------------------------

--
-- Table structure for table `feature_video`
--

CREATE TABLE IF NOT EXISTS `feature_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `feature_video`
--

INSERT INTO `feature_video` (`id`, `video`) VALUES
(1, '17,22,23,24,25');

-- --------------------------------------------------------

--
-- Table structure for table `feature_wallpaper`
--

CREATE TABLE IF NOT EXISTS `feature_wallpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wallpaper` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `feature_wallpaper`
--

INSERT INTO `feature_wallpaper` (`id`, `wallpaper`) VALUES
(1, '35,36');

-- --------------------------------------------------------

--
-- Table structure for table `live_wallpaper`
--

CREATE TABLE IF NOT EXISTS `live_wallpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `more_apps`
--

CREATE TABLE IF NOT EXISTS `more_apps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `more_apps`
--

INSERT INTO `more_apps` (`id`, `name`, `url`, `image`, `message`) VALUES
(7, ' newapp', 'test.com', '1432206995_youtubeplay_update.jpg   ', 'tsting'),
(8, 'test', 'test.com', '1432214432_youtubeplay_update.jpg', 'testing 123'),
(9, 'admin', 'last adeed', '1432214631_Support_Ticket_Banner_590X300.jpg', 'testing 123'),
(10, 'test', 'test.com', '1432275928_Support_Ticket_Banner_590X300.jpg', 'testing 123');

-- --------------------------------------------------------

--
-- Table structure for table `normal_notification`
--

CREATE TABLE IF NOT EXISTS `normal_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ringtone`
--

CREATE TABLE IF NOT EXISTS `ringtone` (
  `ringtone_id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(255) NOT NULL,
  `ringtone_name` varchar(255) NOT NULL,
  `ringtone_url` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `ringtone_image` varchar(255) NOT NULL,
  `ringtone_status` varchar(255) NOT NULL,
  `download_count` int(11) NOT NULL,
  `ringtone_total_rate` varchar(255) NOT NULL,
  `ringtone_rate_avg` int(255) NOT NULL,
  PRIMARY KEY (`ringtone_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

--
-- Table structure for table `ringtones_category`
--

CREATE TABLE IF NOT EXISTS `ringtones_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin`
--

CREATE TABLE IF NOT EXISTS `tbl_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_admin`
--

INSERT INTO `tbl_admin` (`id`, `username`, `password`, `email`) VALUES
(1, 'fundrive', 'e5eb2281206779d179446c3bf30f5936', 'admin@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_feedback`
--

CREATE TABLE IF NOT EXISTS `tbl_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_push_notification`
--

CREATE TABLE IF NOT EXISTS `tbl_push_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_push_notification`
--

INSERT INTO `tbl_push_notification` (`id`, `token`) VALUES
(1, '1234567890'),
(2, '1234567890');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ringtone_rating`
--

CREATE TABLE IF NOT EXISTS `tbl_ringtone_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ringtone_id` varchar(255) NOT NULL,
  `rate` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `dt_rate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tbl_ringtone_rating`
--

INSERT INTO `tbl_ringtone_rating` (`id`, `ringtone_id`, `rate`, `ip`, `dt_rate`) VALUES
(2, '16', '5', '1472851313131323', '2015-05-15 04:42:31'),
(3, '16', '3', '14728513131', '2015-05-15 04:44:50'),
(4, '', '5', '1234156123', '2015-05-15 05:00:54'),
(5, '16', '5', '1234156123', '2015-05-15 05:02:02'),
(6, '16', '5', '56123', '2015-05-15 05:27:08'),
(7, '16', '5', '5613424123', '2015-05-15 05:52:31');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_settings`
--

CREATE TABLE IF NOT EXISTS `tbl_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(255) NOT NULL,
  `app_logo` varchar(255) NOT NULL,
  `app_email` varchar(255) NOT NULL,
  `app_website` varchar(255) NOT NULL,
  `app_description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_settings`
--

INSERT INTO `tbl_settings` (`id`, `app_name`, `app_logo`, `app_email`, `app_website`, `app_description`) VALUES
(1, 'News Application ', 'viavi_logo.png', 'info@viaviweb.com', 'http://viaviweb.in', '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_slider`
--

CREATE TABLE IF NOT EXISTS `tbl_slider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE IF NOT EXISTS `tbl_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_token` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_video_rating`
--

CREATE TABLE IF NOT EXISTS `tbl_video_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_id` varchar(255) NOT NULL,
  `rate` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `dt_rate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tbl_video_rating`
--

INSERT INTO `tbl_video_rating` (`id`, `video_id`, `rate`, `ip`, `dt_rate`) VALUES
(1, '', '5', '1234568', '2015-06-16 06:12:53'),
(2, '6', '5', '1234568', '2015-06-16 06:14:02'),
(3, '6', '3', '1234561231238', '2015-06-16 06:14:40'),
(4, '6', '3', '123', '2015-06-16 06:42:12');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_wallpaper_rating`
--

CREATE TABLE IF NOT EXISTS `tbl_wallpaper_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wallpaper_id` varchar(255) NOT NULL,
  `rate` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `dt_rated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `tbl_wallpaper_rating`
--

INSERT INTO `tbl_wallpaper_rating` (`id`, `wallpaper_id`, `rate`, `ip`, `dt_rated`) VALUES
(1, '26', '5', '1234156', '2015-05-15 04:24:16'),
(2, '26', '5', '1234156', '2015-05-15 04:26:33'),
(3, '26', '5', '12341567', '2015-05-15 04:26:41'),
(8, '26', '5', '147285123123', '2015-05-15 04:32:08'),
(9, '26', '5', '147285123123', '2015-05-15 04:32:31'),
(10, '26', '5', '1472851313131323', '2015-05-15 04:32:51'),
(11, '26', '5', '1472851313131323', '2015-05-15 04:34:00'),
(12, '26', '5', '1472851313131323', '2015-05-15 04:34:24'),
(13, '26', '5', '1234156', '2015-05-15 05:42:30'),
(14, '26', '5', '1234156', '2015-05-15 05:42:52'),
(19, '26', '5', '1234156', '2015-05-15 05:48:55'),
(20, '26', '5', '1234156', '2015-05-15 05:49:01'),
(21, '26', '5', '56123', '2015-05-15 05:49:20'),
(22, '26', '5', '512316123', '2015-05-15 05:51:51'),
(23, '26', '5', '1', '2015-05-15 06:05:01');

-- --------------------------------------------------------

--
-- Table structure for table `video`
--

CREATE TABLE IF NOT EXISTS `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(255) NOT NULL,
  `video_type` varchar(255) NOT NULL,
  `video_url` varchar(255) NOT NULL,
  `video_name` varchar(255) NOT NULL,
  `video_id` varchar(255) NOT NULL,
  `t_image` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `download_count` varchar(255) NOT NULL,
  `video_total_rate` int(11) NOT NULL,
  `feature` varchar(255) NOT NULL,
  `video_rate_avg` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `video`
--

INSERT INTO `video` (`id`, `cid`, `video_type`, `video_url`, `video_name`, `video_id`, `t_image`, `tag`, `size`, `user`, `status`, `download_count`, `video_total_rate`, `feature`, `video_rate_avg`) VALUES
(17, '3', 'youtube', 'https://www.youtube.com/watch?v=h5LADgPIIjE', '', 'h5LADgPIIjE', '', 'youtube', '', 'admin', '1', '0', 0, '', 5),
(18, '3', 'youtube', 'https://www.youtube.com/watch?v=W3V1pIO40Hw', 'test', 'W3V1pIO40Hw', '', 'test', '', 'admin', '0', '0', 0, '', 0),
(22, '3', 'Local_Upload', '1434450963_Chrysanthemum.jpg', 'test', '', '1434450963_Desert.jpg', 'test', '858.78 KB', 'admin', '1', '0', 0, '', 5),
(23, '3', 'Local_Upload', '1434450964_Chrysanthemum.jpg', 'test', '', '1434450964_Desert.jpg', 'test', '858.78 KB', 'admin', '1', '0', 0, '', 2),
(24, '3', 'Local_Upload', '1434451035_Lighthouse.jpg', 'test new', '', '1434451035_Penguins.jpg', 'nree', '548.12 KB', 'admin', '1', '0', 0, '', 0),
(25, '3', 'Local_Upload', '1434451213_Desert.jpg ', ' new123', '000q1w2', '1434451213_Penguins.jpg ', 'nww', '826.11 KB ', 'admin', '1', '0', 0, '', 0),
(26, '3', 'Local_Upload', '1434451508_Chrysanthemum.jpg', 'new test', '', '1434451508_Penguins.jpg', 'new video', '858.78 KB', 'admin', '1', '0', 0, '', 0),
(27, '3', 'Local_Upload', '1434518789_Chrysanthemum.jpg', 'local', '000q1w2', '1434518789_Jellyfish.jpg', 'local upload', '858.78 KB', 'admin', '1', '0', 0, '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `video_category`
--

CREATE TABLE IF NOT EXISTS `video_category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Table structure for table `wallpaper`
--

CREATE TABLE IF NOT EXISTS `wallpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(255) NOT NULL,
  `wallpaper_image` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL,
  `user` varchar(11) NOT NULL,
  `download_count` int(11) NOT NULL,
  `wallpaper_total_rate` int(255) NOT NULL,
  `wallpaper_rate_avg` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=37 ;

-- --------------------------------------------------------

--
-- Table structure for table `wallpaper_categories`
--

CREATE TABLE IF NOT EXISTS `wallpaper_categories` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cid` (`cid`),
  UNIQUE KEY `cid_2` (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
