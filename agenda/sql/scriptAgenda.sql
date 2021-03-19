CREATE DATABASE `grupo_10`;
USE grupo_10;



CREATE TABLE `localidad`
(
	`pais` varchar(20) NOT NULL,
	`provincia` varchar(20) NOT NULL,
	`localidad` varchar(20) NOT NULL,
	`idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`idLocalidad`)
);

CREATE TABLE `tiposDeContactos`
(
    `idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
   	 `nombre` varchar(45) NOT NULL,
   	 PRIMARY KEY (`idTipoContacto`)
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
	`idTipoContacto` int(11) NOT NULL,
 	 FOREIGN KEY (`idTipoContacto`) REFERENCES `tiposDeContactos`(`idTipoContacto`),
    `idLocalidad` int(11) NOT NULL,
    FOREIGN KEY (`idLocalidad`) REFERENCES `localidad`(`idLocalidad`)
);

