DROP TABLE Client IF EXISTS;

CREATE TABLE Client (id INT,
 nombre VARCHAR(150) NOT NULL,
  email  VARCHAR(150)  NOT NULL );

  DROP TABLE Product IF EXISTS;

  CREATE TABLE Product (id INT,
   nombre VARCHAR(150) NOT NULL,
    precio  INT  NOT NULL );

    DROP TABLE Venta IF EXISTS;

    CREATE TABLE Ventas (id INT,
     producto VARCHAR(150) NOT NULL,
     cantidad VARCHAR(150) NOT NULL,
      precioTotal  INT  NOT NULL );