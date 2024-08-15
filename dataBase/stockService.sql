
USE dbstockservice;

-- Tabla Marca --
CREATE TABLE Marca (
    UniqueID INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);

-- Tabla Categoria
CREATE TABLE Categoria (
    UniqueID INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);

-- Tabla Articulo
CREATE TABLE Articulo (
    UniqueID INT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    id_categoria INT,
    id_marca INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(UniqueID),
    FOREIGN KEY (id_marca) REFERENCES Marca(UniqueID)
);

-- Tabla Articulo_Categoria
CREATE TABLE Articulo_Categoria (
    UniqueID INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT,
    id_articulo INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(UniqueID),
    FOREIGN KEY (id_articulo) REFERENCES Articulo(UniqueID)
);
