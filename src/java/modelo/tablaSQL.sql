-- Eliminar la base de datos si existe para comenzar con un nuevo entorno limpio
DROP DATABASE IF EXISTS centro;
-- Crear la base de datos
CREATE DATABASE centro;
-- Cambiar al uso de la base de datos creada
USE centro;


-- Crear tabla usuarios
CREATE TABLE usuarios (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    apellidos VARCHAR(50),
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    PRIMARY KEY (id_usuario)
);

-- Crear tabla roles
CREATE TABLE roles (
    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre_rol ENUM('profesor', 'alumno', 'tic'),
    PRIMARY KEY (id_rol)
);

-- Crear tabla usuarios_rol
CREATE TABLE usuarios_rol (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Crear tabla equipos
CREATE TABLE equipos (
    id_equipo INT NOT NULL AUTO_INCREMENT,
    nombre_equipo VARCHAR(100),
    caracteristicas TEXT,
    aula VARCHAR(50),
    tipo ENUM('ordenador', 'portatil'),
    PRIMARY KEY (id_equipo)
);

-- Crear tabla incidencias
CREATE TABLE incidencias (
    id_incidencia INT NOT NULL AUTO_INCREMENT,
    id_usuario INT,
    id_equipo INT,
    estado ENUM('pendiente', 'reparado'),
    descripcion TEXT,
    prioridad VARCHAR(50),
    fecha_inicio DATE,
    fecha_fin DATE,
    PRIMARY KEY (id_incidencia),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo)
);


-- Insertar roles en la tabla roles para las pruebas
INSERT INTO roles (nombre_rol) VALUES ('profesor');
INSERT INTO roles (nombre_rol) VALUES ('alumno');
INSERT INTO roles (nombre_rol) VALUES ('tic');

-- Insertar roles en la tabla equipos para las pruebas
INSERT INTO equipos (nombre_equipo, caracteristicas, aula, tipo) VALUES
('PC-01', 'Procesador Intel Core i5, 8GB RAM, 256GB SSD', 'Aula 101', 'ordenador'),
('Laptop-01', 'Procesador Intel Core i7, 16GB RAM, 512GB SSD', 'Aula 101', 'portatil'),
('PC-02', 'Procesador AMD Ryzen 7, 16GB RAM, 1TB HDD', 'Aula 101', 'ordenador'),
('Laptop-02', 'Procesador Intel Core i5, 8GB RAM, 256GB SSD', 'Aula 101', 'portatil'),
('PC-03', 'Procesador Intel Core i3, 4GB RAM, 500GB HDD', 'Aula 101', 'ordenador'),
('PC-04', 'Procesador Intel Core i7, 8GB RAM, 512GB SSD', 'Aula 101', 'ordenador'),
('Laptop-03', 'Procesador AMD Ryzen 5, 8GB RAM, 256GB SSD', 'Aula 101', 'portatil'),
('PC-05', 'Procesador Intel Core i5, 16GB RAM, 1TB HDD', 'Aula 101', 'ordenador'),
('Laptop-04', 'Procesador Intel Core i3, 4GB RAM, 256GB SSD', 'Aula 101', 'portatil'),
('Laptop-05', 'Procesador AMD Ryzen 7, 16GB RAM, 512GB SSD', 'Aula 101', 'portatil'),
('PC-06', 'Procesador AMD Ryzen 3, 8GB RAM, 500GB HDD', 'Aula 101', 'ordenador'),
('PC-07', 'Procesador Intel Core i5, 8GB RAM, 256GB SSD', 'Aula 102', 'ordenador'),
('Laptop-06', 'Procesador Intel Core i7, 16GB RAM, 1TB SSD', 'Aula 102', 'portatil'),
('Laptop-07', 'Procesador AMD Ryzen 5, 8GB RAM, 512GB SSD', 'Aula 102', 'portatil'),
('PC-08', 'Procesador Intel Core i3, 4GB RAM, 256GB SSD', 'Aula 102', 'ordenador'),
('PC-09', 'Procesador AMD Ryzen 7, 16GB RAM, 1TB HDD', 'Aula 102', 'ordenador'),
('Laptop-08', 'Procesador Intel Core i5, 8GB RAM, 512GB SSD', 'Aula 102', 'portatil'),
('Laptop-09', 'Procesador AMD Ryzen 3, 4GB RAM, 256GB SSD', 'Aula 102', 'portatil'),
('PC-10', 'Procesador Intel Core i7, 16GB RAM, 1TB HDD', 'Aula 102', 'ordenador'),
('PC-11', 'Procesador AMD Ryzen 5, 8GB RAM, 512GB SSD', 'Aula 102', 'ordenador'),
('PC-12', 'Procesador Intel Core i3, 4GB RAM, 256GB SSD', 'Aula 103', 'ordenador'),
('Laptop-10', 'Procesador AMD Ryzen 7, 16GB RAM, 1TB HDD', 'Aula 103', 'portatil'),
('Laptop-11', 'Procesador Intel Core i5, 8GB RAM, 512GB SSD', 'Aula 103', 'portatil'),
('PC-13', 'Procesador AMD Ryzen 3, 4GB RAM, 256GB SSD', 'Aula 103', 'ordenador'),
('PC-14', 'Procesador Intel Core i7, 16GB RAM, 1TB HDD', 'Aula 103', 'ordenador'),
('Laptop-12', 'Procesador Intel Core i5, 8GB RAM, 512GB SSD', 'Aula 103', 'portatil'),
('PC-15', 'Procesador AMD Ryzen 5, 8GB RAM, 512GB SSD', 'Aula 103', 'ordenador'),
('PC-16', 'Procesador Intel Core i3, 4GB RAM, 256GB SSD', 'Aula 103', 'ordenador'),
('Laptop-13', 'Procesador AMD Ryzen 7, 16GB RAM, 1TB HDD', 'Aula 103', 'portatil'),
('Laptop-14', 'Procesador Intel Core i5, 8GB RAM, 512GB SSD', 'Aula 103', 'portatil');

