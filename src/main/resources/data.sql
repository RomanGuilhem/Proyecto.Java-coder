INSERT INTO  Cliente (id, nombre, email) values{

(1, 'Lionel Messi', leomessi10@gmail.com),

(2, 'Lautaro Martinez', toromartinez@gmail.com),

(3, 'James Rodriguez', james_10_co@gmail.com),

(4, 'Ousmane Dembele', Mosquito@hotmail.com),

(5, 'Hector Bellerin', hectorbellerin@gmail.com);
}

INSERT INTO  Producto (id, nombre, precio) values{

(100, 'Heladera', $400000),

(200, 'Cafetera', $100000),

(300, 'Licuadora', $50000),

(400, 'Microondas', $150000),

(500, 'Pava electrica', $80000);
}
INSERT INTO  Ventas (producto, cantidad, precioTotal) values{

('Heladera', 1 , $400000),

('Cafetera', 2 , $200000),

('Licuadora', 5 , $250000),

('Microondas', 2 , $300000),

('Pava electrica', 3 , $240000),
}