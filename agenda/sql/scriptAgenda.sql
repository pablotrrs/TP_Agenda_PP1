CREATE DATABASE `grupo_10`;
USE grupo_10;

CREATE TABLE IF NOT EXISTS `paises` (
  `idPais` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`idPais`)
);

CREATE TABLE IF NOT EXISTS `provincias` (
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `idPais` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
   PRIMARY KEY (`idProvincia`),
   FOREIGN KEY (`idPais`) REFERENCES `paises`(`idPais`)
); 

CREATE TABLE IF NOT EXISTS `localidad` (
  `idLocalidad` int(11) NOT NULL AUTO_INCREMENT,
  `idProvincia` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`idLocalidad`),
   FOREIGN KEY (`idProvincia`) REFERENCES `provincias`(`idProvincia`)
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
    `telefono` varchar(30) NOT NULL,
    `calle` varchar(20) NOT NULL,
    `piso`  varchar(15) NOT NULL,
    `altura` varchar(15) NOT NULL,
    `depto` varchar(15) NOT NULL,
    `email` varchar(50) NOT NULL,
    `fechaCumpleanios` date DEFAULT NULL,
    PRIMARY KEY (`idPersona`),
    `idTipoContacto` int(11) NOT NULL,
    FOREIGN KEY (`idTipoContacto`) REFERENCES `tiposDeContactos`(`idTipoContacto`),
    `idLocalidad` int(11) NOT NULL,
    FOREIGN KEY (`idLocalidad`) REFERENCES `localidad`(`idLocalidad`)
);

INSERT INTO tiposDeContactos (idTipoContacto, nombre) VALUES (1, 'Trabajo');
INSERT INTO tiposDeContactos (idTipoContacto, nombre) VALUES (2, 'Amigos');
INSERT INTO tiposDeContactos (idTipoContacto, nombre) VALUES (3, 'Familia');



