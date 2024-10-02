DROP TABLE Cliente IF EXISTS;

CREATE TABLE Cliente (id INT,
 nombre VARCHAR(150) NOT NULL,
  email  VARCHAR(150)  NOT NULL );

  DROP TABLE Producto IF EXISTS;

  CREATE TABLE Producto (id INT,
   nombre VARCHAR(150) NOT NULL,
    precio  INT  NOT NULL );

    DROP TABLE Ventas IF EXISTS;

    CREATE TABLE Ventas (id INT,
     producto VARCHAR(150) NOT NULL,
     cantidad VARCHAR(150) NOT NULL,
      precioTotal  INT  NOT NULL );