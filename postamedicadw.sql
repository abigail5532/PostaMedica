-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-12-2023 a las 17:02:16
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `postamedicadw`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `idcita` int(11) NOT NULL,
  `pacientecita` int(11) NOT NULL,
  `especialidadcita` int(11) NOT NULL,
  `fechacita` varchar(10) NOT NULL,
  `horacita` varchar(6) NOT NULL,
  `activecita` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `citas`
--

INSERT INTO `citas` (`idcita`, `pacientecita`, `especialidadcita`, `fechacita`, `horacita`, `activecita`) VALUES
(1, 5, 4, '2016.10.13', '11:00', 'R'),
(2, 1, 3, '2018.05.26', '11:00', 'R'),
(3, 1, 1, '2022.12.15', '12:30', 'R'),
(4, 2, 2, '2019.03.04', '14:00', 'R'),
(5, 5, 4, '2018.06.09', '15:00', 'R'),
(6, 4, 4, '2023.12.11', '10:00', 'R'),
(7, 2, 4, '2023.12.11', '10:15', 'R'),
(8, 1, 4, '2023.12.11', '10:25', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consultas`
--

CREATE TABLE `consultas` (
  `idcons` int(11) NOT NULL,
  `triajecons` int(11) NOT NULL,
  `diagnosticocons` varchar(350) NOT NULL,
  `tratamientocons` varchar(350) NOT NULL,
  `estadocons` varchar(10) NOT NULL,
  `activecons` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consultas`
--

INSERT INTO `consultas` (`idcons`, `triajecons`, `diagnosticocons`, `tratamientocons`, `estadocons`, `activecons`) VALUES
(1, 3, 'El paciente presenta dolor de estómago y náuseas frecuentemente. Se realizó un estudio en un hospital y se puede evidenciar que tiene gastritis crónica.', 'Se le recomienda tomar mucha agua y Omeprazol tres veces al día, 30 minutos antes de comer.', 'Asistio', 'A'),
(2, 1, '-', '-', 'No Asistio', 'A'),
(3, 5, 'El paciente presentó fiebre, dolor de cabeza y escalofríos.', 'Se le recomienda tomar el medicamento cada 8 horas. ', 'Asistio', 'A'),
(4, 6, 'El paciente presenta nauseas y dolor de estómago.', 'Se sugiere derivar el caso a la especialidad de Gastroenterología.', 'Asistio', 'A'),
(5, 4, 'El paciente presenta ha asistido para un chequeo.', '-', 'Asistio', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `idesp` int(11) NOT NULL,
  `nombreesp` varchar(50) NOT NULL,
  `nrooficinaesp` varchar(5) NOT NULL,
  `activeesp` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidades`
--

INSERT INTO `especialidades` (`idesp`, `nombreesp`, `nrooficinaesp`, `activeesp`) VALUES
(1, 'Gastroenterologia', '110', 'A'),
(2, 'Odontologia', '365', 'A'),
(3, 'Neurologia', '900', 'A'),
(4, 'Otorrinolaringologia', '944', 'A'),
(5, 'Medicina General', '294', 'A'),
(6, 'Neumologia', '432', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historias`
--

CREATE TABLE `historias` (
  `idlista` int(11) NOT NULL,
  `nrohistlista` varchar(10) NOT NULL,
  `nroboletalista` varchar(15) NOT NULL,
  `fecreglista` varchar(10) NOT NULL,
  `activelista` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historias`
--

INSERT INTO `historias` (`idlista`, `nrohistlista`, `nroboletalista`, `fecreglista`, `activelista`) VALUES
(1, '3451', '7600000000', '2022-01-24', 'A'),
(2, '2231', '5435600000', '2016-02-14', 'A'),
(3, '1125', '2313567322', '2022-05-07', 'A'),
(4, '1123', '2203453527', '2022-06-08', 'A'),
(5, '5689', '4322773879', '2023-04-11', 'A'),
(6, '1203', '2323356092', '2023-06-12', 'A'),
(7, '1204', '9234123454', '2023-06-20', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicos`
--

CREATE TABLE `medicos` (
  `idmed` int(11) NOT NULL,
  `nombresmed` varchar(60) NOT NULL,
  `apellidosmed` varchar(60) NOT NULL,
  `dnimed` varchar(8) NOT NULL,
  `especialidadmed` int(11) NOT NULL,
  `cmpmed` varchar(5) NOT NULL,
  `telefonomed` varchar(9) NOT NULL,
  `emailmed` varchar(70) NOT NULL,
  `domiciliomed` varchar(200) NOT NULL,
  `fecregmed` varchar(10) NOT NULL,
  `activemed` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `medicos`
--

INSERT INTO `medicos` (`idmed`, `nombresmed`, `apellidosmed`, `dnimed`, `especialidadmed`, `cmpmed`, `telefonomed`, `emailmed`, `domiciliomed`, `fecregmed`, `activemed`) VALUES
(1, 'Luisa Julia', 'Martinez Quiroz', '67679887', 4, '23472', '979207280', 'adgma@gmail.com', 'Calle Huari, 876 - San Martin de Porres', '2010-09-03', 'A'),
(2, 'Luis', 'Suarez Lopez', '02250992', 2, '65823', '980023845', 'suarezlopez@gmail.com', 'Jr. Las Luciernagas, 145 - Los Olivos', '2016-04-18', 'A'),
(3, 'Lourdes Marinela', 'Salas Valencia', '23430122', 1, '96547', '980209933', '23430122salas@gmail.com', 'Av. Los Ángeles, 790 - Independencia', '2019-02-24', 'A'),
(4, 'Javier Gustavo', 'Fernández García', '13211332', 5, '33426', '907345322', 'fernandezgarcia@gmail.com', '134 - Urb. Solis - San Martín de Porres', '2019-01-07', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE `pacientes` (
  `idpac` int(11) NOT NULL,
  `dnipac` varchar(8) NOT NULL,
  `apellidospac` varchar(150) NOT NULL,
  `nombrespac` varchar(150) NOT NULL,
  `fechanacpac` varchar(10) NOT NULL,
  `telefonopac` varchar(9) NOT NULL,
  `sexopac` varchar(1) NOT NULL,
  `domiciliopac` varchar(250) NOT NULL,
  `seguropac` varchar(50) NOT NULL,
  `tiposangrepac` varchar(20) NOT NULL,
  `alergiaspac` varchar(250) NOT NULL,
  `historiapac` int(11) NOT NULL,
  `fecregpac` varchar(10) NOT NULL,
  `activepac` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pacientes`
--

INSERT INTO `pacientes` (`idpac`, `dnipac`, `apellidospac`, `nombrespac`, `fechanacpac`, `telefonopac`, `sexopac`, `domiciliopac`, `seguropac`, `tiposangrepac`, `alergiaspac`, `historiapac`, `fecregpac`, `activepac`) VALUES
(1, '21232145', 'Bermudez Ramirez', 'Samantha', '1999-11-04', '900323442', 'F', 'Calle Girasoles 265 - Los Olivos', 'SIS', 'O+', 'Orfenadrina e Ibuprofeno', 1, '2022-01-24', 'A'),
(2, '65560992', 'Gomez Souza', 'Michael Alonso', '1955.10.04', '923100902', 'M', 'Jr. Los Alisos 135 - San Martin de Porres', 'MAPFRE', 'O+', '-', 2, '2016-02-14', 'A'),
(3, '78902338', 'Garcia Santos', 'Alonso', '1970-11-28', '910034179', 'M', 'Av. Jazmines 192 - San Martin de Porres', 'MAPFRE', 'O+', 'Tramadol', 3, '2022-05-07', 'A'),
(4, '25330922', 'Torres Pereira', 'Karla Xiomara', '1982-10-13', '990233818', 'F', 'Calle Ramon Castilla 335 - Los Olivos', 'SIS', 'O+', '-', 4, '2022-06-08', 'A'),
(5, '90230227', 'Bermudez Ramirez', 'Piero Felipe', '2000-03-13', '991882903', 'M', 'Calle Girasoles 105 - Los Olivos', 'RIMAC', 'O+', '-', 5, '2023-04-11', 'A'),
(6, '49738228', 'Diaz Fernandez', 'Sara Anayeli', '1982-09-30', '911233492', 'F', 'Calle Los Angeles 447 - Los Olivos', 'SIS', 'O+', '-', 6, '2023-06-12', 'A'),
(7, '80212378', 'Guzman Sarmiento', 'Alfredo Alejandro', '1995-12-19', '901342113', 'M', 'Calle Amapolas 123 - San Martin de Porres', 'SIS', 'O+', 'Tramadol', 7, '2023-06-20', 'N');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `triaje`
--

CREATE TABLE `triaje` (
  `idtri` int(11) NOT NULL,
  `citatri` int(11) NOT NULL,
  `pesotri` varchar(4) NOT NULL,
  `estaturatri` varchar(4) NOT NULL,
  `temperaturatri` varchar(4) NOT NULL,
  `presiontri` varchar(10) NOT NULL,
  `pulsotri` int(5) NOT NULL,
  `respiraciontri` int(5) NOT NULL,
  `activetri` char(1) NOT NULL DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `triaje`
--

INSERT INTO `triaje` (`idtri`, `citatri`, `pesotri`, `estaturatri`, `temperaturatri`, `presiontri`, `pulsotri`, `respiraciontri`, `activetri`) VALUES
(1, 3, '0', '0', '0', '0', 0, 0, 'N'),
(3, 2, '57', '1.60', '37', '0', 0, 0, 'N'),
(4, 4, '69', '1.60', '0', '0', 0, 0, 'N'),
(5, 1, '85', '1.67', '37', '0', 0, 0, 'A'),
(6, 5, '70', '1.66', '37', '0', 0, 0, 'N'),
(8, 6, '75', '1.50', '37', '0', 0, 0, 'A'),
(9, 7, '65', '1.69', '36', '130/70', 70, 22, 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `iduser` int(11) NOT NULL,
  `dniuser` varchar(10) NOT NULL,
  `nombresuser` varchar(50) NOT NULL,
  `claveuser` varchar(20) NOT NULL,
  `roluser` varchar(15) NOT NULL,
  `fecreguser` varchar(10) NOT NULL,
  `activeuser` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`iduser`, `dniuser`, `nombresuser`, `claveuser`, `roluser`, `fecreguser`, `activeuser`) VALUES
(1, '67679887', 'Luisa Julia Martinez Quiroz', 'medico1', 'Medico', '2023-12-01', 'A'),
(2, '12125443', 'Bastian Daniel Loayza Zapata', 'administrador', 'Administrador', '2023-12-01', 'A'),
(3, '02250992', 'Luis Suarez Lopez', '12345678', 'Medico', '2023-12-01', 'A'),
(4, '23430122', 'Lourdes Marinela Salas Valencia', 'marinelasalas23', 'Medico', '2023-12-01', 'A'),
(5, '13211332', 'Javier Gustavo Fernandez Garcia', 'medico1', 'Medico', '2023-12-01', 'A');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`idcita`),
  ADD KEY `Paciente_cita` (`pacientecita`,`especialidadcita`),
  ADD KEY `Especialidad_cita` (`especialidadcita`);

--
-- Indices de la tabla `consultas`
--
ALTER TABLE `consultas`
  ADD PRIMARY KEY (`idcons`),
  ADD KEY `Triaje_cons` (`triajecons`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`idesp`);

--
-- Indices de la tabla `historias`
--
ALTER TABLE `historias`
  ADD PRIMARY KEY (`idlista`);

--
-- Indices de la tabla `medicos`
--
ALTER TABLE `medicos`
  ADD PRIMARY KEY (`idmed`),
  ADD KEY `Especialidad_med` (`especialidadmed`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`idpac`),
  ADD KEY `historiapac` (`historiapac`);

--
-- Indices de la tabla `triaje`
--
ALTER TABLE `triaje`
  ADD PRIMARY KEY (`idtri`),
  ADD KEY `Cita_tri` (`citatri`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`iduser`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consultas`
--
ALTER TABLE `consultas`
  MODIFY `idcons` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `triaje`
--
ALTER TABLE `triaje`
  MODIFY `idtri` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`especialidadcita`) REFERENCES `medicos` (`idmed`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`pacientecita`) REFERENCES `pacientes` (`idpac`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `consultas`
--
ALTER TABLE `consultas`
  ADD CONSTRAINT `consultas_ibfk_1` FOREIGN KEY (`triajecons`) REFERENCES `triaje` (`idtri`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `medicos`
--
ALTER TABLE `medicos`
  ADD CONSTRAINT `medicos_ibfk_1` FOREIGN KEY (`especialidadmed`) REFERENCES `especialidades` (`idesp`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD CONSTRAINT `pacientes_ibfk_1` FOREIGN KEY (`historiapac`) REFERENCES `historias` (`idlista`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `triaje`
--
ALTER TABLE `triaje`
  ADD CONSTRAINT `triaje_ibfk_1` FOREIGN KEY (`citatri`) REFERENCES `citas` (`idcita`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
