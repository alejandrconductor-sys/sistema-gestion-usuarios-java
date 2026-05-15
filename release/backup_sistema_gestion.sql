/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.14-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: sistema_gestion
-- ------------------------------------------------------
-- Server version	10.11.14-MariaDB-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `id_auditoria` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `accion` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT current_timestamp(),
  `modulo` varchar(30) DEFAULT NULL,
  `referencia_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_auditoria`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
INSERT INTO `auditoria` VALUES
(1,3,'CREAR_VENTA','Venta registrada: 650.00','2026-04-05 04:22:28','VENTA',8),
(2,3,'CREAR_VENTA','Venta registrada: 850.00','2026-04-05 04:22:51','VENTA',9),
(3,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:30:32','VENTA',8),
(4,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:30:39','VENTA',6),
(5,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=7','2026-04-05 06:31:34','VENTA',8),
(6,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=8','2026-04-05 06:31:37','VENTA',6),
(7,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:52:21','VENTA',6),
(8,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:52:30','VENTA',9),
(9,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=9','2026-04-05 06:52:56','VENTA',6),
(10,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=10','2026-04-05 06:52:59','VENTA',9),
(11,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:53:39','VENTA',8),
(12,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-05 06:53:46','VENTA',6),
(13,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=11','2026-04-05 06:54:09','VENTA',8),
(14,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=12','2026-04-05 06:54:11','VENTA',6),
(15,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:39:29','VENTA',9),
(16,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:39:35','VENTA',8),
(17,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:39:43','VENTA',6),
(18,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=13','2026-04-07 02:40:08','VENTA',9),
(19,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=15','2026-04-07 02:40:10','VENTA',6),
(20,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:40:50','VENTA',9),
(21,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=16','2026-04-07 02:41:28','VENTA',9),
(22,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=14','2026-04-07 02:41:33','VENTA',8),
(23,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:41:57','VENTA',9),
(24,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:42:02','VENTA',8),
(25,4,'SOLICITAR_ANULACION','Solicitud de anulación creada','2026-04-07 02:42:06','VENTA',6),
(26,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=19','2026-04-07 03:13:35','VENTA',6),
(27,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=17','2026-04-07 03:13:37','VENTA',9),
(28,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=18','2026-04-07 03:13:39','VENTA',8),
(29,3,'CREAR_VENTA','Venta registrada: 4000.00','2026-04-07 03:26:01','VENTA',14),
(30,3,'CREAR_VENTA','Venta registrada: 500.00','2026-04-07 03:26:11','VENTA',15),
(31,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:28:57','VENTA',15),
(32,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=20','2026-04-08 04:29:24','VENTA',15),
(33,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:30:19','VENTA',15),
(34,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:30:28','VENTA',9),
(35,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:30:34','VENTA',8),
(36,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:30:42','VENTA',6),
(37,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=21','2026-04-08 04:31:06','VENTA',15),
(38,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=22','2026-04-08 04:31:10','VENTA',9),
(39,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=23','2026-04-08 04:31:15','VENTA',8),
(40,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=24','2026-04-08 04:31:17','VENTA',6),
(41,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:39:15','VENTA',14),
(42,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=25','2026-04-08 04:39:44','VENTA',14),
(43,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 04:42:14','VENTA',7),
(44,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=26','2026-04-08 04:57:44','VENTA',7),
(45,3,'CREAR_VENTA','Venta registrada: 100.00','2026-04-08 05:56:19','VENTA',16),
(46,3,'CREAR_VENTA','Venta registrada: 800.00','2026-04-08 05:56:36','VENTA',17),
(47,3,'CREAR_VENTA','Venta registrada: 250.00','2026-04-08 06:03:02','VENTA',18),
(48,3,'CREAR_VENTA','Venta registrada: 6400.00','2026-04-08 06:03:11','VENTA',19),
(49,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 06:03:51','VENTA',19),
(50,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-08 06:04:00','VENTA',18),
(51,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=27','2026-04-08 06:04:40','VENTA',19),
(52,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=28','2026-04-08 06:04:46','VENTA',18),
(53,3,'CREAR_VENTA','Venta registrada: 50.00','2026-04-10 23:13:20','VENTA',20),
(54,4,'AGREGAR_STOCK','Stock agregado: 5','2026-04-21 04:59:18','INVENTARIO',5),
(55,6,'CREAR_VENTA','Venta registrada: 3500.00','2026-04-21 05:00:09','VENTA',23),
(56,4,'AGREGAR_STOCK','Stock agregado: 33','2026-04-21 15:17:38','INVENTARIO',1),
(57,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 15:18:00','INVENTARIO',1),
(58,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:08:12','INVENTARIO',1),
(59,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:08:25','INVENTARIO',2),
(60,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:08:34','INVENTARIO',3),
(61,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:08:40','INVENTARIO',4),
(62,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:08:50','INVENTARIO',5),
(63,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:03','INVENTARIO',1),
(64,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:07','INVENTARIO',1),
(65,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:14','INVENTARIO',1),
(66,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:22','INVENTARIO',2),
(67,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:27','INVENTARIO',3),
(68,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:36','INVENTARIO',4),
(69,4,'AGREGAR_STOCK','Stock agregado: 50','2026-04-21 19:09:41','INVENTARIO',5),
(70,6,'CREAR_VENTA','Venta registrada: 30000.00','2026-04-21 19:12:19','VENTA',24),
(71,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-21 20:48:29','INVENTARIO',4),
(72,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=29','2026-04-21 20:49:17','INVENTARIO',4),
(73,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-21 20:50:00','INVENTARIO',4),
(74,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=30','2026-04-21 20:50:40','INVENTARIO',4),
(75,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-21 20:51:36','INVENTARIO',4),
(76,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=31','2026-04-21 21:03:46','INVENTARIO',4),
(77,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-21 21:04:54','INVENTARIO',1),
(78,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=32','2026-04-21 21:05:39','INVENTARIO',1),
(79,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-21 21:58:28','VENTA',17),
(80,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-21 21:58:49','INVENTARIO',5),
(81,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=34','2026-04-21 22:00:07','INVENTARIO',5),
(82,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-21 22:00:46','VENTA',16),
(83,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-21 22:00:59','VENTA',23),
(84,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=36','2026-04-21 23:23:54','VENTA',23),
(85,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=35','2026-04-21 23:23:59','VENTA',16),
(86,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=33','2026-04-21 23:24:14','VENTA',17),
(87,3,'CREAR_VENTA','Venta registrada: 500.00','2026-04-23 23:32:36','VENTA',26),
(88,6,'CREAR_VENTA','Venta registrada: 500.00','2026-04-23 23:33:04','VENTA',27),
(89,3,'CREAR_VENTA','Venta registrada: 500.00','2026-04-24 00:16:38','VENTA',29),
(90,3,'CREAR_VENTA','Venta registrada: 1000.00','2026-04-24 18:13:54','VENTA',30),
(91,3,'CREAR_VENTA','Venta registrada: 8000.00','2026-04-24 18:23:05','VENTA',32),
(92,3,'CREAR_VENTA','Venta registrada: 4000.00','2026-04-24 18:23:26','VENTA',33),
(93,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-24 18:24:30','INVENTARIO',1),
(94,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 18:24:58','VENTA',33),
(95,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=37','2026-04-24 18:25:49','INVENTARIO',1),
(96,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=38','2026-04-24 18:26:10','VENTA',33),
(97,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 18:27:41','VENTA',32),
(98,3,'CREAR_VENTA','Venta registrada: 4000.00','2026-04-24 18:34:03','VENTA',34),
(99,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 18:34:54','VENTA',34),
(100,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-24 18:35:06','INVENTARIO',1),
(101,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=41','2026-04-24 18:35:31','INVENTARIO',1),
(102,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-24 18:37:21','INVENTARIO',1),
(103,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=42','2026-04-24 18:37:48','INVENTARIO',1),
(104,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=40','2026-04-24 18:38:55','VENTA',34),
(105,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=39','2026-04-24 18:50:36','VENTA',32),
(106,3,'CREAR_VENTA','Venta registrada: 88000.00','2026-04-24 18:51:10','VENTA',35),
(107,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 18:52:36','VENTA',35),
(108,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-24 18:53:01','INVENTARIO',1),
(109,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-24 18:53:19','INVENTARIO',4),
(110,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=43','2026-04-24 18:53:58','VENTA',35),
(111,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=45','2026-04-24 18:54:11','INVENTARIO',4),
(112,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=44','2026-04-24 18:54:14','INVENTARIO',1),
(113,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 19:31:14','VENTA',30),
(114,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=46','2026-04-24 19:31:40','VENTA',30),
(115,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 19:32:34','VENTA',29),
(116,1,'RECHAZAR_SOLICITUD','Solicitud rechazada ID=47','2026-04-24 19:32:59','VENTA',29),
(117,3,'CREAR_VENTA','Venta registrada: 20000.00','2026-04-24 21:13:13','VENTA',36),
(118,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-24 21:13:46','VENTA',36),
(119,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=48','2026-04-24 21:14:19','VENTA',36),
(120,3,'CREAR_VENTA','Venta registrada: 1000.00','2026-04-30 21:30:44','VENTA',37),
(121,4,'SOLICITAR_ANULACION','Solicitud creada','2026-04-30 21:31:35','VENTA',37),
(122,4,'CREAR_SOLICITUD','Solicitud creada: REDUCIR','2026-04-30 21:31:54','INVENTARIO',3),
(123,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=49','2026-04-30 21:32:19','VENTA',37),
(124,1,'APROBAR_SOLICITUD','Solicitud aprobada ID=50','2026-04-30 21:32:27','INVENTARIO',3);
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `estado` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `creado_por` int(11) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `documento` (`documento`),
  KEY `creado_por` (`creado_por`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`creado_por`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES
(1,'Cliente Test','12345678',NULL,NULL,NULL,'ACTIVO',2,'2026-03-24 21:06:35'),
(2,'Cliente A','11111111',NULL,NULL,NULL,'ACTIVO',2,'2026-04-02 19:20:41'),
(3,'Cliente B','22222222',NULL,NULL,NULL,'ACTIVO',2,'2026-04-02 19:20:41'),
(4,'Cliente C','33333333',NULL,NULL,NULL,'ACTIVO',2,'2026-04-02 19:20:41'),
(5,'Cliente D','44444444',NULL,NULL,NULL,'ACTIVO',2,'2026-04-02 19:20:41'),
(6,'Cliente_prueba_IU','123','123','Cliente_prueba_IU@sistema.com','la_calle','ACTIVO',4,'2026-04-23 23:02:28'),
(7,'prueba_2Cl_Nue','1234','1234','1234@siste.com','la_calle2','ACTIVO',4,'2026-04-23 23:18:11');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_vendedor`
--

DROP TABLE IF EXISTS `cliente_vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente_vendedor` (
  `id_cliente` int(11) NOT NULL,
  `id_vendedor` int(11) NOT NULL,
  PRIMARY KEY (`id_cliente`,`id_vendedor`),
  KEY `id_vendedor` (`id_vendedor`),
  CONSTRAINT `cliente_vendedor_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `cliente_vendedor_ibfk_2` FOREIGN KEY (`id_vendedor`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_vendedor`
--

LOCK TABLES `cliente_vendedor` WRITE;
/*!40000 ALTER TABLE `cliente_vendedor` DISABLE KEYS */;
INSERT INTO `cliente_vendedor` VALUES
(1,3),
(2,6),
(3,6),
(4,6),
(5,6),
(6,3),
(7,3);
/*!40000 ALTER TABLE `cliente_vendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `id_detalle` int(11) NOT NULL AUTO_INCREMENT,
  `id_venta` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_detalle`),
  KEY `id_venta` (`id_venta`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`),
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES
(1,1,1,2,50.00,100.00),
(2,3,1,2,50.00,100.00),
(3,6,1,1,50.00,50.00),
(4,7,1,2,50.00,100.00),
(5,8,1,5,50.00,250.00),
(6,8,5,2,200.00,400.00),
(7,9,5,3,200.00,600.00),
(8,9,1,5,50.00,250.00),
(9,14,4,5,800.00,4000.00),
(10,15,1,10,50.00,500.00),
(11,16,1,2,50.00,100.00),
(12,17,4,1,800.00,800.00),
(13,18,1,5,50.00,250.00),
(14,19,4,8,800.00,6400.00),
(15,20,1,1,50.00,50.00),
(16,23,2,1,3500.00,3500.00),
(17,24,3,200,150.00,30000.00),
(18,26,1,10,50.00,500.00),
(19,27,1,10,50.00,500.00),
(20,29,8,5,100.00,500.00),
(21,30,5,5,200.00,1000.00),
(22,32,4,10,800.00,8000.00),
(23,33,4,5,800.00,4000.00),
(24,34,4,5,800.00,4000.00),
(25,35,4,110,800.00,88000.00),
(26,36,9,100,200.00,20000.00),
(27,37,5,5,200.00,1000.00);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_asignacion`
--

DROP TABLE IF EXISTS `historial_asignacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial_asignacion` (
  `id_historial` int(11) NOT NULL AUTO_INCREMENT,
  `id_vendedor` int(11) NOT NULL,
  `tipo` enum('CLIENTE','PRODUCTO') NOT NULL,
  `accion` enum('ASIGNADO','QUITADO') NOT NULL,
  `referencia_id` int(11) NOT NULL,
  `fecha` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_historial`),
  KEY `idx_historial_vendedor` (`id_vendedor`),
  KEY `idx_historial_tipo` (`tipo`),
  KEY `idx_historial_referencia` (`referencia_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_asignacion`
--

LOCK TABLES `historial_asignacion` WRITE;
/*!40000 ALTER TABLE `historial_asignacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `historial_asignacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `id_inventario` int(11) NOT NULL AUTO_INCREMENT,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 0,
  `fecha_actualizacion` timestamp NULL DEFAULT current_timestamp(),
  `estado` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  PRIMARY KEY (`id_inventario`),
  UNIQUE KEY `id_producto` (`id_producto`),
  CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES
(1,1,0,'2026-03-24 21:14:36','ACTIVO'),
(2,2,200,'2026-04-02 19:22:38','ACTIVO'),
(3,3,100,'2026-04-02 19:22:38','ACTIVO'),
(4,4,110,'2026-04-02 19:22:38','ACTIVO'),
(5,5,205,'2026-04-02 19:22:38','ACTIVO'),
(10,6,100,'2026-04-23 23:49:50','ACTIVO'),
(11,7,100,'2026-04-23 23:49:58','ACTIVO'),
(12,8,100,'2026-04-23 23:50:14','ACTIVO'),
(13,9,100,'2026-04-23 23:50:33','ACTIVO');
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL,
  `estado` enum('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
  `creado_por` int(11) NOT NULL,
  `fecha_creacion` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_producto`),
  KEY `creado_por` (`creado_por`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`creado_por`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES
(1,'Producto Test',NULL,50.00,'ACTIVO',2,'2026-03-24 21:06:43'),
(2,'Laptop Pro',NULL,3500.00,'ACTIVO',2,'2026-04-02 19:20:57'),
(3,'Teclado Mecánico',NULL,150.00,'ACTIVO',2,'2026-04-02 19:20:57'),
(4,'Monitor 24\"',NULL,800.00,'ACTIVO',2,'2026-04-02 19:20:57'),
(5,'Auriculares',NULL,200.00,'ACTIVO',2,'2026-04-02 19:20:57'),
(6,'Producto_prueba_IU','vendemos porque podemos',500.00,'ACTIVO',4,'2026-04-23 23:03:04'),
(7,'Producto_prueba_IU','vendemos porque podemos',500.00,'ACTIVO',4,'2026-04-23 23:03:08'),
(8,'Prueba2','mejora',100.00,'ACTIVO',4,'2026-04-23 23:14:14'),
(9,'Prueba_3','mejoras_3',200.00,'ACTIVO',4,'2026-04-23 23:18:36');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_vendedor`
--

DROP TABLE IF EXISTS `producto_vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_vendedor` (
  `id_producto` int(11) NOT NULL,
  `id_vendedor` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`,`id_vendedor`),
  KEY `id_vendedor` (`id_vendedor`),
  CONSTRAINT `producto_vendedor_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `producto_vendedor_ibfk_2` FOREIGN KEY (`id_vendedor`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_vendedor`
--

LOCK TABLES `producto_vendedor` WRITE;
/*!40000 ALTER TABLE `producto_vendedor` DISABLE KEYS */;
INSERT INTO `producto_vendedor` VALUES
(1,6),
(2,6),
(3,6),
(4,3),
(5,3),
(7,3),
(8,3),
(9,3),
(9,6);
/*!40000 ALTER TABLE `producto_vendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES
(1,'ADMIN','Administrador del sistema','2026-03-04 23:22:27'),
(2,'RRHH','Gestión de recursos humanos','2026-03-04 23:22:27'),
(4,'GERENTE_VENTAS','Gestiona ventas, clientes e inventario','2026-04-02 19:19:35'),
(5,'VENDEDOR','Realiza ventas y gestiona clientes asignados','2026-04-02 19:19:35');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud_cambio`
--

DROP TABLE IF EXISTS `solicitud_cambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud_cambio` (
  `id_solicitud` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `modulo` varchar(30) NOT NULL,
  `tipo_entidad` varchar(30) DEFAULT NULL,
  `accion` varchar(30) NOT NULL,
  `referencia_id` int(11) NOT NULL,
  `datos_anteriores` text DEFAULT NULL,
  `datos_nuevos` text DEFAULT NULL,
  `estado` enum('PENDIENTE','APROBADO','RECHAZADO') DEFAULT 'PENDIENTE',
  `fecha` timestamp NULL DEFAULT current_timestamp(),
  `tipo_referencia` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_solicitud`),
  KEY `id_usuario` (`id_usuario`),
  KEY `referencia_id` (`referencia_id`),
  KEY `idx_solicitud_estado` (`estado`),
  KEY `idx_solicitud_filtro` (`modulo`,`estado`),
  CONSTRAINT `solicitud_cambio_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud_cambio`
--

LOCK TABLES `solicitud_cambio` WRITE;
/*!40000 ALTER TABLE `solicitud_cambio` DISABLE KEYS */;
INSERT INTO `solicitud_cambio` VALUES
(27,4,'VENTA','VENTA','ANULAR',19,NULL,NULL,'APROBADO','2026-04-08 06:03:51',NULL),
(28,4,'VENTA','VENTA','ANULAR',18,NULL,NULL,'RECHAZADO','2026-04-08 06:04:00',NULL),
(29,4,'INVENTARIO','PRODUCTO','REDUCIR',4,NULL,'44','APROBADO','2026-04-21 20:48:29',NULL),
(30,4,'INVENTARIO','PRODUCTO','REDUCIR',4,NULL,'200','RECHAZADO','2026-04-21 20:50:00',NULL),
(31,4,'INVENTARIO','PRODUCTO','REDUCIR',4,NULL,'200','APROBADO','2026-04-21 20:51:36',NULL),
(32,4,'INVENTARIO','PRODUCTO','REDUCIR',1,NULL,'366','APROBADO','2026-04-21 21:04:54',NULL),
(33,4,'VENTA','VENTA','ANULAR',17,NULL,NULL,'APROBADO','2026-04-21 21:58:28',NULL),
(34,4,'INVENTARIO','PRODUCTO','REDUCIR',5,NULL,'5','RECHAZADO','2026-04-21 21:58:49',NULL),
(35,4,'VENTA','VENTA','ANULAR',16,NULL,NULL,'APROBADO','2026-04-21 22:00:46',NULL),
(36,4,'VENTA','VENTA','ANULAR',23,NULL,NULL,'RECHAZADO','2026-04-21 22:00:59',NULL),
(37,4,'INVENTARIO','PRODUCTO','REDUCIR',1,NULL,'50','APROBADO','2026-04-24 18:24:30',NULL),
(38,4,'VENTA','VENTA','ANULAR',33,NULL,NULL,'RECHAZADO','2026-04-24 18:24:58',NULL),
(39,4,'VENTA','VENTA','ANULAR',32,NULL,NULL,'APROBADO','2026-04-24 18:27:41',NULL),
(40,4,'VENTA','VENTA','ANULAR',34,NULL,NULL,'RECHAZADO','2026-04-24 18:34:54',NULL),
(41,4,'INVENTARIO','PRODUCTO','REDUCIR',1,NULL,'20','APROBADO','2026-04-24 18:35:06',NULL),
(42,4,'INVENTARIO','PRODUCTO','REDUCIR',1,NULL,'80','APROBADO','2026-04-24 18:37:21',NULL),
(43,4,'VENTA','VENTA','ANULAR',35,NULL,NULL,'APROBADO','2026-04-24 18:52:36',NULL),
(44,4,'INVENTARIO','PRODUCTO','REDUCIR',1,NULL,'50','APROBADO','2026-04-24 18:53:01',NULL),
(45,4,'INVENTARIO','PRODUCTO','REDUCIR',4,NULL,'50','APROBADO','2026-04-24 18:53:19',NULL),
(46,4,'VENTA','VENTA','ANULAR',30,NULL,NULL,'APROBADO','2026-04-24 19:31:14',NULL),
(47,4,'VENTA','VENTA','ANULAR',29,NULL,NULL,'RECHAZADO','2026-04-24 19:32:34',NULL),
(48,4,'VENTA','VENTA','ANULAR',36,NULL,NULL,'APROBADO','2026-04-24 21:13:46',NULL),
(49,4,'VENTA','VENTA','ANULAR',37,NULL,NULL,'APROBADO','2026-04-30 21:31:35',NULL),
(50,4,'INVENTARIO','PRODUCTO','REDUCIR',3,NULL,'50','APROBADO','2026-04-30 21:31:54',NULL);
/*!40000 ALTER TABLE `solicitud_cambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `estado` enum('PENDIENTE','ACTIVO','DESACTIVADO','BLOQUEADO') NOT NULL DEFAULT 'PENDIENTE',
  `fecha_creacion` timestamp NULL DEFAULT current_timestamp(),
  `aprobado_por` int(11) DEFAULT NULL,
  `fecha_aprobacion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_aprobado_por` (`aprobado_por`),
  CONSTRAINT `fk_aprobado_por` FOREIGN KEY (`aprobado_por`) REFERENCES `usuario` (`id_usuario`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES
(1,'Admin','Sistema','admin@sistema.com','1234','ACTIVO','2026-03-04 23:23:15',NULL,NULL),
(2,'Maria  Ana','Gomez','rrhh@sistema.com','1234','ACTIVO','2026-03-04 23:23:47',NULL,NULL),
(3,'Juan jose','Perez','vendedor@sistema.com','1234','ACTIVO','2026-03-04 23:24:03',NULL,NULL),
(4,'Carlos','Lopez','gerente@sistema.com','123456','ACTIVO','2026-04-02 19:20:13',NULL,NULL),
(5,'gerente1','uno','gerente1@sistema.com','123456','ACTIVO','2026-04-02 19:57:21',NULL,NULL),
(6,'vendedor1','uno','vendedor1@sistema.com','123456','ACTIVO','2026-04-02 19:57:49',NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_rol` (
  `id_usuario` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `fecha_asignacion` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_usuario`,`id_rol`),
  KEY `fk_rol` (`id_rol`),
  CONSTRAINT `fk_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES
(1,1,'2026-03-04 23:23:26'),
(2,2,'2026-03-04 23:23:48'),
(3,5,'2026-04-02 19:19:52'),
(4,4,'2026-04-02 19:20:17'),
(5,4,'2026-04-02 19:57:21'),
(6,5,'2026-04-02 19:57:49');
/*!40000 ALTER TABLE `usuario_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `id_venta` int(11) NOT NULL AUTO_INCREMENT,
  `id_vendedor` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `fecha` timestamp NULL DEFAULT current_timestamp(),
  `total` decimal(10,2) NOT NULL,
  `estado` enum('APROBADA','ANULADA') NOT NULL DEFAULT 'APROBADA',
  `aprobado_por` int(11) DEFAULT NULL,
  `fecha_aprobacion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_venta`),
  KEY `id_vendedor` (`id_vendedor`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`id_vendedor`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES
(1,3,1,'2026-03-24 21:14:44',100.00,'APROBADA',NULL,NULL),
(3,3,1,'2026-03-31 16:48:58',100.00,'APROBADA',NULL,NULL),
(6,3,1,'2026-03-31 16:52:22',50.00,'APROBADA',1,'2026-04-05 06:31:37'),
(7,3,1,'2026-04-01 15:54:26',100.00,'APROBADA',NULL,NULL),
(8,3,1,'2026-04-05 04:22:28',650.00,'APROBADA',1,'2026-04-05 06:31:34'),
(9,3,5,'2026-04-05 04:22:51',850.00,'APROBADA',1,'2026-04-07 02:41:28'),
(14,3,1,'2026-04-07 03:26:01',4000.00,'APROBADA',NULL,NULL),
(15,3,5,'2026-04-07 03:26:11',500.00,'APROBADA',NULL,NULL),
(16,3,3,'2026-04-08 05:56:19',100.00,'ANULADA',1,'2026-04-21 23:23:59'),
(17,3,3,'2026-04-08 05:56:36',800.00,'ANULADA',1,'2026-04-21 23:24:14'),
(18,3,5,'2026-04-08 06:03:02',250.00,'APROBADA',NULL,NULL),
(19,3,3,'2026-04-08 06:03:11',6400.00,'ANULADA',1,'2026-04-08 06:04:40'),
(20,3,4,'2026-04-10 23:13:20',50.00,'APROBADA',NULL,NULL),
(23,6,2,'2026-04-21 05:00:09',3500.00,'APROBADA',NULL,NULL),
(24,6,3,'2026-04-21 19:12:19',30000.00,'APROBADA',NULL,NULL),
(26,3,1,'2026-04-23 23:32:36',500.00,'APROBADA',NULL,NULL),
(27,6,2,'2026-04-23 23:33:04',500.00,'APROBADA',NULL,NULL),
(29,3,1,'2026-04-24 00:16:38',500.00,'APROBADA',NULL,NULL),
(30,3,7,'2026-04-24 18:13:54',1000.00,'ANULADA',1,'2026-04-24 19:31:40'),
(32,3,1,'2026-04-24 18:23:05',8000.00,'ANULADA',1,'2026-04-24 18:50:36'),
(33,3,6,'2026-04-24 18:23:26',4000.00,'APROBADA',NULL,NULL),
(34,3,1,'2026-04-24 18:34:03',4000.00,'APROBADA',NULL,NULL),
(35,3,1,'2026-04-24 18:51:10',88000.00,'ANULADA',1,'2026-04-24 18:53:58'),
(36,3,7,'2026-04-24 21:13:13',20000.00,'ANULADA',1,'2026-04-24 21:14:19'),
(37,3,6,'2026-04-30 21:30:43',1000.00,'ANULADA',1,'2026-04-30 21:32:19');
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-30 17:50:00
