-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: fabio
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alquiler`
--

DROP TABLE IF EXISTS `alquiler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alquiler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` date DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `cant_tiempo` int(11) DEFAULT NULL,
  `tipo_tiempo` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_sucursal_origen` int(11) DEFAULT NULL,
  `hora_inicio` varchar(20) DEFAULT NULL,
  `id_sucursal_destino` int(11) DEFAULT NULL,
  `hora_fin` varchar(20) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `kms_out` double DEFAULT NULL,
  `kms_in` double DEFAULT NULL,
  `fuel_out` int(11) DEFAULT NULL,
  `fuel_in` int(11) DEFAULT NULL,
  `notes` varchar(2000) DEFAULT NULL,
  `total` float DEFAULT NULL,
  `extra` float DEFAULT NULL,
  `gran_total` float DEFAULT NULL,
  `dias` int(11) DEFAULT NULL,
  `rate_per_day` float DEFAULT NULL,
  `deuda` float DEFAULT NULL,
  `rego` varchar(50) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `semanas` int(11) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `id_source` int(11) DEFAULT NULL,
  `fecha` varchar(25) DEFAULT NULL,
  `movil` varchar(45) DEFAULT NULL,
  `deudaCurrent` float DEFAULT NULL,
  `fecha_calculo_current` date DEFAULT NULL,
  `fortnightly` tinyint(4) DEFAULT NULL,
  `fecha_primer_pago` date DEFAULT NULL,
  `dinero_ingresado` float DEFAULT NULL,
  `agrupacion` varchar(10) DEFAULT NULL,
  `deudaToll` float DEFAULT NULL,
  `fecha_inicio_road` date DEFAULT NULL,
  `fecha_fin_road` date DEFAULT NULL,
  `fecha_revision` date DEFAULT NULL,
  `tipo_rego` int(11) DEFAULT NULL,
  `fecha_inicio_term` date DEFAULT NULL,
  `ganancia` float DEFAULT NULL,
  `year_cost` float DEFAULT NULL,
  `nota_interna` varchar(100) DEFAULT NULL,
  `es_puntos` tinyint(4) DEFAULT NULL,
  `diario` tinyint(4) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `subscription_type` int(11) DEFAULT NULL,
  `payment_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alquiler_auto_idx` (`id_auto`),
  KEY `alquiler_usuario_idx` (`id_usuario`),
  KEY `alquier_suc_inicio_idx` (`id_sucursal_origen`),
  KEY `alquiler_suc_fin_idx` (`id_sucursal_destino`),
  KEY `fecha_inicio_estado` (`fecha_inicio`,`estado`),
  KEY `rego` (`rego`),
  CONSTRAINT `alquier_suc_inicio` FOREIGN KEY (`id_sucursal_origen`) REFERENCES `sucursal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `alquiler_auto` FOREIGN KEY (`id_auto`) REFERENCES `auto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `alquiler_suc_fin` FOREIGN KEY (`id_sucursal_destino`) REFERENCES `sucursal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `alquiler_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=997 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alquiler_cliente`
--

DROP TABLE IF EXISTS `alquiler_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alquiler_cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `es_principal` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cliente_alqui_idx` (`id_cliente`),
  KEY `alquiler_principal` (`id_alquiler`,`es_principal`),
  CONSTRAINT `cliente_alqui` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6830 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alquiler_impuesto`
--

DROP TABLE IF EXISTS `alquiler_impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alquiler_impuesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_alquiler` int(11) DEFAULT NULL,
  `id_impuesto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `seleccionado` tinyint(4) DEFAULT NULL,
  `subtotal` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alquiler` (`id_alquiler`)
) ENGINE=InnoDB AUTO_INCREMENT=13069 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alquiler_tarjeta`
--

DROP TABLE IF EXISTS `alquiler_tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alquiler_tarjeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_tarjeta` int(11) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tarjeta_alquiler_idx` (`id_tarjeta`),
  KEY `id_alquiler` (`id_alquiler`),
  CONSTRAINT `tarjeta_alquiler` FOREIGN KEY (`id_tarjeta`) REFERENCES `tarjeta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto`
--

DROP TABLE IF EXISTS `auto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_marca` int(11) DEFAULT NULL,
  `id_modelo` int(11) DEFAULT NULL,
  `id_rego` int(11) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `transmision` int(11) DEFAULT NULL,
  `año` int(11) DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `kilometraje` varchar(45) DEFAULT NULL,
  `aire_acondicionado` int(11) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `nro_motor` varchar(45) DEFAULT NULL,
  `nro_chasis` varchar(45) DEFAULT NULL,
  `VIN` varchar(45) DEFAULT NULL,
  `id_tipo_combustible` int(11) DEFAULT NULL,
  `id_sucursal` int(11) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `disponible` tinyint(4) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `stock` bigint(20) DEFAULT NULL,
  `number_gps` varchar(80) DEFAULT NULL,
  `code_gps` varchar(80) DEFAULT NULL,
  `fecha_vencimiento_gps` date DEFAULT NULL,
  `validado` tinyint(4) DEFAULT NULL,
  `id_usuario_valido_gps` int(11) DEFAULT NULL,
  `fecha_validacion_gps` date DEFAULT NULL,
  `grabado` varchar(45) DEFAULT NULL,
  `id_drivetrain` int(11) DEFAULT NULL,
  `descripcion` varchar(5000) DEFAULT NULL,
  `titulo` varchar(80) DEFAULT NULL,
  `uso_descripcion_especifica` tinyint(4) DEFAULT NULL,
  `cilindros` varchar(20) DEFAULT NULL,
  `motor` varchar(45) DEFAULT NULL,
  `puertas` int(11) DEFAULT NULL,
  `asientos` int(11) DEFAULT NULL,
  `build` date DEFAULT NULL,
  `compliance` date DEFAULT NULL,
  `marcado_vendido` tinyint(4) DEFAULT NULL,
  `id_label` int(11) DEFAULT NULL,
  `rego` varchar(45) DEFAULT NULL,
  `fecha_vencimiento_rego` date DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  `valor_compra` float DEFAULT NULL,
  `fecha_compra` date DEFAULT NULL,
  `written_off` tinyint(4) DEFAULT NULL,
  `certificate_date` date DEFAULT NULL,
  `certificate_number` varchar(80) DEFAULT NULL,
  `water_damage` tinyint(4) DEFAULT NULL,
  `major_modification` tinyint(4) DEFAULT NULL,
  `imported_second_hand` tinyint(4) DEFAULT NULL,
  `formulario_actualizado` tinyint(4) DEFAULT NULL,
  `id_tipo_compra` int(11) DEFAULT NULL,
  `id_cliente_proveedor` int(11) DEFAULT NULL,
  `transferencia_impresa` tinyint(4) DEFAULT NULL,
  `disposal_impresa` tinyint(4) DEFAULT NULL,
  `monto_load` float DEFAULT NULL,
  `monto_warranty` float DEFAULT NULL,
  `subscription` tinyint(4) DEFAULT NULL,
  `id_woocommerce` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `marca_auto_idx` (`id_marca`),
  KEY `modelo_auto_idx` (`id_modelo`),
  KEY `tipo_auto_idx` (`id_tipo`),
  KEY `combustible_auto_idx` (`id_tipo_combustible`),
  KEY `rego_auto_idx` (`id_rego`),
  KEY `sucursal_auto_idx` (`id_sucursal`),
  KEY `categoria_auto_idx` (`id_categoria`),
  CONSTRAINT `categoria_auto` FOREIGN KEY (`id_categoria`) REFERENCES `categoria_auto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `combustible_auto` FOREIGN KEY (`id_tipo_combustible`) REFERENCES `tipo_combustible` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `marca_auto` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modelo_auto` FOREIGN KEY (`id_modelo`) REFERENCES `modelo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rego_auto` FOREIGN KEY (`id_rego`) REFERENCES `rego` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sucursal_auto` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipo_auto` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_auto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=419 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto_imagenes`
--

DROP TABLE IF EXISTS `auto_imagenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_imagenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `imagen` longblob,
  `public` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2668 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto_opcion`
--

DROP TABLE IF EXISTS `auto_opcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_opcion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_auto` int(11) DEFAULT NULL,
  `id_opcion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auto_precios`
--

DROP TABLE IF EXISTS `auto_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auto_precios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `tipo_tiempo` int(11) DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `defecto` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=658 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `autoridad`
--

DROP TABLE IF EXISTS `autoridad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autoridad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `tool_road_autoridad` tinyint(4) DEFAULT NULL,
  `text_name` varchar(50) DEFAULT NULL,
  `id_pais` int(11) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `direccion` varchar(60) DEFAULT NULL,
  `ciudad` varchar(60) DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  `codigo_postal` varchar(45) DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `website` varchar(80) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `autoridad_pais_idx` (`id_pais`),
  KEY `autoridad_dpto_idx` (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria_auto`
--

DROP TABLE IF EXISTS `categoria_auto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria_auto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria_usuario`
--

DROP TABLE IF EXISTS `categoria_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `movil` varchar(45) DEFAULT NULL,
  `licencia_numero` varchar(45) DEFAULT NULL,
  `fecha_vencimiento_lic` date DEFAULT NULL,
  `direccion` varchar(80) DEFAULT NULL,
  `suburbio` varchar(45) DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  `codigo_postal` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `lic_infinita` tinyint(4) DEFAULT NULL,
  `paisSTR` varchar(45) DEFAULT NULL,
  `otorgadaEN` varchar(45) DEFAULT NULL,
  `good_customer` tinyint(4) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `dealer_licence` varchar(45) DEFAULT NULL,
  `abn` varchar(45) DEFAULT NULL,
  `acn` varchar(45) DEFAULT NULL,
  `codigo_descuento` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=577 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `codigo_postal`
--

DROP TABLE IF EXISTS `codigo_postal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `codigo_postal` (
  `id` int(11) NOT NULL,
  `codigo_postal` int(11) DEFAULT NULL,
  `suburb` varchar(100) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentario_pre`
--

DROP TABLE IF EXISTS `comentario_pre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comentario_pre` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_pre` bigint(20) DEFAULT NULL,
  `mensaje` varchar(1000) DEFAULT NULL,
  `fecha` varchar(20) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dealer_licence`
--

DROP TABLE IF EXISTS `dealer_licence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dealer_licence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `numero` varchar(80) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `drivetrain`
--

DROP TABLE IF EXISTS `drivetrain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drivetrain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `detalle` varchar(100) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `detalle` varchar(100) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formulario5`
--

DROP TABLE IF EXISTS `formulario5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formulario5` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entry_number` bigint(20) DEFAULT NULL,
  `stock_number` bigint(20) DEFAULT NULL,
  `fecha_generation` date DEFAULT NULL,
  `dealer_name` varchar(45) DEFAULT NULL,
  `dealer_address` varchar(200) DEFAULT NULL,
  `dealer_licence` varchar(45) DEFAULT NULL,
  `date_manufacture` date DEFAULT NULL,
  `make` varchar(80) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `rego` varchar(45) DEFAULT NULL,
  `rego_exp_date` date DEFAULT NULL,
  `car_price` float DEFAULT NULL,
  `odometer_reading` varchar(45) DEFAULT NULL,
  `vin` varchar(45) DEFAULT NULL,
  `engine_number` varchar(45) DEFAULT NULL,
  `written_off` tinyint(4) DEFAULT NULL,
  `water_damage` tinyint(4) DEFAULT NULL,
  `major_modifications` tinyint(4) DEFAULT NULL,
  `ppsr_code` varchar(45) DEFAULT NULL,
  `guarantee` tinyint(4) DEFAULT NULL,
  `imported_second_hand` tinyint(4) DEFAULT NULL,
  `sale_date` date DEFAULT NULL,
  `sale_price` float DEFAULT NULL,
  `sale_odometer` varchar(45) DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `datos_comprador` varchar(500) DEFAULT NULL,
  `rms_numero` varchar(80) DEFAULT NULL,
  `rms_fecha` date DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=633 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formulario_imprimir`
--

DROP TABLE IF EXISTS `formulario_imprimir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formulario_imprimir` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `label` varchar(45) DEFAULT NULL,
  `copias` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `impuesto`
--

DROP TABLE IF EXISTS `impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `impuesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `id_sucursal` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `max_price` float DEFAULT NULL,
  `obligatorio` tinyint(4) DEFAULT NULL,
  `gst_inc` tinyint(4) DEFAULT NULL,
  `editable` tinyint(4) DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_categoria_auto` int(11) DEFAULT NULL,
  `tipo_asociado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `imp_sucursal_idx` (`id_sucursal`),
  KEY `imp_tipo_idx` (`id_tipo`),
  KEY `imp_cat_auto_idx` (`id_categoria_auto`),
  CONSTRAINT `imp_cat_auto` FOREIGN KEY (`id_categoria_auto`) REFERENCES `categoria_auto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `imp_sucursal` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `imp_tipo` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_impuesto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_mecanico`
--

DROP TABLE IF EXISTS `invoice_mecanico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_mecanico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `invoice_number` varchar(45) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `total_sin_gst` double DEFAULT NULL,
  `gst` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `sentido` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_mecanico_detalle`
--

DROP TABLE IF EXISTS `invoice_mecanico_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_mecanico_detalle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_invoice` bigint(20) DEFAULT NULL,
  `nuestro` tinyint(4) DEFAULT NULL,
  `rego` varchar(45) DEFAULT NULL,
  `id_modelo` int(11) DEFAULT NULL,
  `id_marca` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `gst` double DEFAULT NULL,
  `subtotal_sin_gst` double DEFAULT NULL,
  `inc_gst` tinyint(4) DEFAULT NULL,
  `vin` varchar(80) DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=966 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mensaje` varchar(1000) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `numero` varchar(100) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `id_mensaje_relacionado` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=628 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensaje_interno`
--

DROP TABLE IF EXISTS `mensaje_interno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje_interno` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_usuario_creador` int(11) DEFAULT NULL,
  `texto` varchar(1000) DEFAULT NULL,
  `fecha_hora` varchar(45) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_primero` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensaje_interno_destino`
--

DROP TABLE IF EXISTS `mensaje_interno_destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje_interno_destino` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_usuario_destino` int(11) DEFAULT NULL,
  `id_mensaje_interno` bigint(20) DEFAULT NULL,
  `numero` varchar(80) DEFAULT NULL,
  `leido` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mensajes_internos`
--

DROP TABLE IF EXISTS `mensajes_internos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensajes_internos` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` int(11) DEFAULT NULL,
  `fecha_hora` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `primero` int(11) DEFAULT NULL,
  `texto` varchar(255) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `id_usuario_creador` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_mensajes_internos_id_usuario_creador` (`id_usuario_creador`),
  KEY `FK_mensajes_internos_id_alquiler` (`id_alquiler`),
  CONSTRAINT `FK_mensajes_internos_id_alquiler` FOREIGN KEY (`id_alquiler`) REFERENCES `alquiler` (`id`),
  CONSTRAINT `FK_mensajes_internos_id_usuario_creador` FOREIGN KEY (`id_usuario_creador`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `tipo` varchar(25) DEFAULT NULL,
  `codigo_submenu` int(11) DEFAULT NULL,
  `url` varchar(80) DEFAULT NULL,
  `super_usuario` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merge`
--

DROP TABLE IF EXISTS `merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `modelo`
--

DROP TABLE IF EXISTS `modelo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modelo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `id_marca` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `marca_modelo_idx` (`id_marca`),
  CONSTRAINT `marca_modelo` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multa`
--

DROP TABLE IF EXISTS `multa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `id_cliente_principal` int(11) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `rego` varchar(45) DEFAULT NULL,
  `id_autoridad` int(11) DEFAULT NULL,
  `id_witness` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha_multa` varchar(45) DEFAULT NULL,
  `fecha_cargada` date DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `letra_cliente` varchar(100) DEFAULT NULL,
  `internal_notes` varchar(100) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `impreso` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `multa_cliente_principal_idx` (`id_cliente`),
  KEY `multa_cliente_idx` (`id_cliente_principal`),
  KEY `mullta_autoridad_idx` (`id_autoridad`),
  KEY `multa_witness_idx` (`id_witness`),
  CONSTRAINT `mullta_autoridad` FOREIGN KEY (`id_autoridad`) REFERENCES `autoridad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `multa_cliente` FOREIGN KEY (`id_cliente_principal`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `multa_cliente_principal` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `multa_witness` FOREIGN KEY (`id_witness`) REFERENCES `witness` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5838 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `opcion`
--

DROP TABLE IF EXISTS `opcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opcion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_alquiler` int(11) DEFAULT NULL,
  `id_sucursal` int(11) DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `monto` float DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `nota` varchar(1000) DEFAULT NULL,
  `nombre_cliente` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `recibo_numero` varchar(80) DEFAULT NULL,
  `automatico` int(11) DEFAULT NULL,
  `monto_toll` float DEFAULT NULL,
  `monto_late_payment` float DEFAULT NULL,
  `agrupacion` varchar(45) DEFAULT NULL,
  `monto_semana` float DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `puntos_porcentaje` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_alquiler` (`id_alquiler`,`status`),
  KEY `fecha` (`fecha_hora`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=16872 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago_importado`
--

DROP TABLE IF EXISTS `pago_importado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago_importado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `monto` varchar(45) DEFAULT NULL,
  `referencia` varchar(45) DEFAULT NULL,
  `customer_name` varchar(80) DEFAULT NULL,
  `respuesta_codigo` varchar(45) DEFAULT NULL,
  `respuesta_texto` varchar(45) DEFAULT NULL,
  `recibo_numero` varchar(45) DEFAULT NULL,
  `fecha` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `estado_privado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19477 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paises`
--

DROP TABLE IF EXISTS `paises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paises` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(2) NOT NULL DEFAULT '',
  `nombre` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=253 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parametro`
--

DROP TABLE IF EXISTS `parametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parametro` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GRUPO` varchar(45) DEFAULT NULL,
  `CALIF_GRUPO` varchar(45) DEFAULT NULL,
  `PARAM` varchar(45) DEFAULT NULL,
  `CALIF_PARAM` varchar(45) DEFAULT NULL,
  `VALOR` varchar(200) DEFAULT NULL,
  `TIPO_DATO` varchar(45) DEFAULT NULL,
  `EXPLICACION` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pre_booking`
--

DROP TABLE IF EXISTS `pre_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pre_booking` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `id_auto` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `mensaje` varchar(500) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `leido` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publicidad`
--

DROP TABLE IF EXISTS `publicidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publicidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `puntos`
--

DROP TABLE IF EXISTS `puntos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `puntos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `id_pago` bigint(20) DEFAULT NULL,
  `id_alquiler` int(11) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `porcentaje_ideal` float DEFAULT NULL,
  `porcentaje_aplicado` float DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `sentido` int(11) DEFAULT NULL,
  `puntos_perdidos` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `agrupacion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rego`
--

DROP TABLE IF EXISTS `rego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rego` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(45) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rego_state_idx` (`id_state`),
  CONSTRAINT `rego_state` FOREIGN KEY (`id_state`) REFERENCES `state` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sucursal`
--

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sucursal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_codigo_postal` int(11) DEFAULT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `abn` varchar(80) DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `id_pais` int(11) DEFAULT NULL,
  `facebook_page_id` varchar(80) DEFAULT NULL,
  `id_dealer_licence` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tarea`
--

DROP TABLE IF EXISTS `tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `realizado` tinyint(4) DEFAULT NULL,
  `caducado` tinyint(4) DEFAULT NULL,
  `nota_resolucion` varchar(100) DEFAULT NULL,
  `id_usuario_asignado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tarjeta`
--

DROP TABLE IF EXISTS `tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(100) DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `fechaVenc` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tarjeta_tipo_idx` (`id_tipo`),
  CONSTRAINT `tarjeta_tipo` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_tarjeta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `templeate`
--

DROP TABLE IF EXISTS `templeate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `templeate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(1000) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `campos` varchar(1000) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_auto`
--

DROP TABLE IF EXISTS `tipo_auto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_auto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_combustible`
--

DROP TABLE IF EXISTS `tipo_combustible`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_combustible` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_compra`
--

DROP TABLE IF EXISTS `tipo_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_compra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_impuesto`
--

DROP TABLE IF EXISTS `tipo_impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_impuesto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_multa`
--

DROP TABLE IF EXISTS `tipo_multa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_multa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_pago`
--

DROP TABLE IF EXISTS `tipo_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_pago` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo_tarjeta`
--

DROP TABLE IF EXISTS `tipo_tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_tarjeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trabajador`
--

DROP TABLE IF EXISTS `trabajador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trabajador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_cliente`
--

DROP TABLE IF EXISTS `user_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_cliente` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(45) DEFAULT NULL,
  `contraseña` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `observacion` varchar(45) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `es_super` int(11) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `pregunta` varchar(80) DEFAULT NULL,
  `respuesta` varchar(80) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_usuario_idx` (`id_categoria`),
  CONSTRAINT `categoria_usuario` FOREIGN KEY (`id_categoria`) REFERENCES `categoria_usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `valor` float DEFAULT NULL,
  `car_odometer` varchar(45) DEFAULT NULL,
  `proposal_date` date DEFAULT NULL,
  `rms_codigo` varchar(200) DEFAULT NULL,
  `rms_fecha` date DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `id_auto` int(11) DEFAULT NULL,
  `id_formulario` bigint(20) DEFAULT NULL,
  `id_invoice` bigint(20) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `cliente_nombre` varchar(45) DEFAULT NULL,
  `cliente_direccion` varchar(100) DEFAULT NULL,
  `cliente_mobile` varchar(45) DEFAULT NULL,
  `cliente_fecha_nacimiento` date DEFAULT NULL,
  `cliente_abn` varchar(45) DEFAULT NULL,
  `cliente_email` varchar(45) DEFAULT NULL,
  `cliente_licence` varchar(45) DEFAULT NULL,
  `car_make` varchar(100) DEFAULT NULL,
  `car_model` varchar(100) DEFAULT NULL,
  `car_body` varchar(45) DEFAULT NULL,
  `car_vin` varchar(100) DEFAULT NULL,
  `car_motor_numero` varchar(100) DEFAULT NULL,
  `car_stock` varchar(45) DEFAULT NULL,
  `car_color` varchar(45) DEFAULT NULL,
  `car_transmicion` varchar(45) DEFAULT NULL,
  `car_descripcion` varchar(100) DEFAULT NULL,
  `car_rego_exp` date DEFAULT NULL,
  `car_rego` varchar(45) DEFAULT NULL,
  `car_built` date DEFAULT NULL,
  `car_compilance` date DEFAULT NULL,
  `id_sucursal` int(11) DEFAULT NULL,
  `cliente_telefono` varchar(45) DEFAULT NULL,
  `monto_pagado` float DEFAULT NULL,
  `monto_adeudado` float DEFAULT NULL,
  `fecha_delivery` date DEFAULT NULL,
  `cliente_suburbio` varchar(80) DEFAULT NULL,
  `cliente_state` varchar(45) DEFAULT NULL,
  `cliente_codigo_postal` varchar(45) DEFAULT NULL,
  `clietne_acn` varchar(45) DEFAULT NULL,
  `cliente_dealer_licence` varchar(45) DEFAULT NULL,
  `cliente_tipo` int(11) DEFAULT NULL,
  `car_year` int(11) DEFAULT NULL,
  `cliente_apellido` varchar(45) DEFAULT NULL,
  `delivery_name` varchar(100) DEFAULT NULL,
  `delivery_address` varchar(100) DEFAULT NULL,
  `delivery_abn` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `witness`
--

DROP TABLE IF EXISTS `witness`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `witness` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tittle` varchar(45) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `direccion` varchar(80) DEFAULT NULL,
  `ciudad` varchar(80) DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  `codigo_postal` varchar(45) DEFAULT NULL,
  `id_pais` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-02  8:39:31
