
CREATE DATABASE IF NOT EXISTS `grupo_10`;
USE grupo_10;

CREATE TABLE IF NOT EXISTS `paises` (
  `idPais` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`idPais`)
);

CREATE TABLE IF NOT EXISTS `provincias` (
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `idPais` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`idProvincia`),
   FOREIGN KEY (`idPais`) REFERENCES `paises`(`idPais`)
); 

CREATE TABLE IF NOT EXISTS `localidad` (
  `codigoPostal` varchar(45) NOT NULL,
  `idProvincia` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`codigoPostal`, `nombre`),
   FOREIGN KEY (`idProvincia`) REFERENCES `provincias`(`idProvincia`)
); 

CREATE TABLE IF NOT EXISTS `tiposDeContactos`
(
    `idTipoContacto` int(11) NOT NULL AUTO_INCREMENT,
    `nombre` varchar(45) DEFAULT NULL,
	PRIMARY KEY (`idTipoContacto`)
);

CREATE TABLE IF NOT EXISTS `personas`
(
    `idPersona` int(11) NOT NULL AUTO_INCREMENT,
    `usuario` varchar(45) NOT NULL,
    `nombre` varchar(45) NOT NULL,
    `telefono` varchar(30) NOT NULL,
    `calle` varchar(20) DEFAULT NULL,
    `piso`  varchar(15) DEFAULT NULL,
    `altura` varchar(15) DEFAULT NULL,
    `depto` varchar(15) DEFAULT NULL,
    `email` varchar(50) NOT NULL,
    `fechaCumpleanios` date DEFAULT NULL,
    PRIMARY KEY (`idPersona`),
    `idTipoContacto` int(11) DEFAULT NULL,
    FOREIGN KEY (`idTipoContacto`) REFERENCES `tiposDeContactos`(`idTipoContacto`),
    idLocalidad varchar(45) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `usuarios`
(
	`nombre` varchar(30) NOT NULL,
	`password` varchar(40) NOT NULL,
	`activo` int(1) NOT NULL,
	PRIMARY KEY (`nombre`)
);