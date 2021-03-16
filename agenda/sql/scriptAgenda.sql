CREATE DATABASE `grupo_10`;
USE grupo_10;

CREATE TABLE `tiposContacto`
(
	`idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
   	 `Nombre` varchar(45) NOT NULL,
   	 PRIMARY KEY (`idTipoContacto`)
);

CREATE TABLE `personas`
(
  	`idPersona` int(11) NOT NULL AUTO_INCREMENT,
  	`Nombre` varchar(45) NOT NULL,
  	`Telefono` varchar(20) NOT NULL,
  	`Calle` varchar(20) NOT NULL,
  	`Piso`  varchar(4) NOT NULL,
	`Altura` varchar(4) NOT NULL,
	`Depto` varchar(4) NOT NULL,
	`Email` varchar(50) NOT NULL,
	`Fecha_cumpleaños` date DEFAULT NULL,
  	PRIMARY KEY (`idPersona`)
	`idTipoContacto` int(11),
 	 FOREIGN KEY (`idTipoContacto`) REFERENCES `tiposContacto`(`idTipoContacto`)
);

