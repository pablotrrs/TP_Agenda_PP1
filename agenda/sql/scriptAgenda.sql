CREATE DATABASE `grupo_10`;
USE grupo_10;

CREATE TABLE `tiposDeContactos`
(
   	 `nombre` varchar(45) NOT NULL,
   	 PRIMARY KEY (`nombre`)
);

CREATE TABLE `personas`
(
  	`idPersona` int(11) NOT NULL AUTO_INCREMENT,
  	`nombre` varchar(45) NOT NULL,
  	`telefono` varchar(20) NOT NULL,
  	`calle` varchar(20) NOT NULL,
  	`piso`  varchar(4) NOT NULL,
	`altura` varchar(4) NOT NULL,
	`depto` varchar(4) NOT NULL,
	`email` varchar(50) NOT NULL,
	`fechaCumpleanios` date DEFAULT NULL,
  	PRIMARY KEY (`idPersona`),
	`tipoContacto` varchar(45) NOT NULL,
 	 FOREIGN KEY (`tipoContacto`) REFERENCES `tiposDeContactos`(`nombre`)
);

