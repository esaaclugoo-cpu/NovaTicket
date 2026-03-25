drop novaticket;
create database novaticket;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('cliente','admin')
);

CREATE TABLE lugar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL
);

CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATE NOT NULL,
    aforo_maximo INT NOT NULL,
    tipo_evento ENUM('concierto','museo','teatro') NOT NULL,
    id_lugar INT NOT NULL,
    ruta_imagen VARCHAR(255),
    
    FOREIGN KEY (id_lugar) REFERENCES lugar(id)
);

CREATE TABLE concierto (
    id_evento INT PRIMARY KEY,
    artista_principal VARCHAR(150) NOT NULL,
    genero_musical VARCHAR(100) NOT NULL,
    duracion_minutos INT NOT NULL,
    
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE
);

CREATE TABLE teatro (
    id_evento INT PRIMARY KEY,
    obra VARCHAR(150) NOT NULL,
    director VARCHAR(150) NOT NULL,
    
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE
);

CREATE TABLE museo (
    id_evento INT PRIMARY KEY,
    nombre_exposicion VARCHAR(150) NOT NULL,
    tipo_exposicion VARCHAR(100) NOT NULL,
    fecha_fin DATE,
    
    FOREIGN KEY (id_evento) REFERENCES evento(id) ON DELETE CASCADE
);

CREATE TABLE asiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_lugar INT NOT NULL,
    fila VARCHAR(10) NOT NULL,
    numero_asiento INT NOT NULL,
    zona VARCHAR(50) NOT NULL,
    
    FOREIGN KEY (id_lugar) REFERENCES lugar(id)
);

CREATE TABLE ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_asiento INT,
    tipo VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_evento) REFERENCES evento(id),
    FOREIGN KEY (id_asiento) REFERENCES asiento(id)
);

CREATE TABLE compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha DATETIME NOT NULL,
    total DECIMAL(10,2),
    
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE detalle_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_compra INT NOT NULL,
    id_ticket INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (id_compra) REFERENCES compra(id) ON DELETE CASCADE,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id)
);




--DEMO DATA--





INSERT INTO usuario (nombre, email, password, tipo_usuario)
VALUES 
('Juan Perez', 'juan@email.com', '1234', 'cliente'),
('Admin', 'admin@email.com', 'admin123', 'admin');


INSERT INTO lugar (nombre, direccion, ciudad)
VALUES 
('Auditorio Central', 'Calle Mayor 1', 'Madrid'),
('Teatro Real', 'Plaza Isabel II', 'Madrid'),
('Museo Nacional', 'Calle Museo 10', 'Madrid');


INSERT INTO evento (nombre, descripcion, fecha, aforo_maximo, tipo_evento, id_lugar, ruta_imagen)
VALUES 
('Concierto Rock', 'Concierto de rock en vivo', '2026-06-10', 500, 'concierto', 1, 'imagenes/concierto.jpg'),
('Obra Hamlet', 'Representación teatral de Hamlet', '2026-07-01', 300, 'teatro', 2, 'imagenes/teatro.jpg'),
('Exposición Arte Moderno', 'Exposición de arte contemporáneo', '2026-08-15', 200, 'museo', 3, 'imagenes/museo.jpg');


INSERT INTO concierto (id_evento, artista_principal, genero_musical, duracion_minutos)
VALUES 
(1, 'Metallica', 'Rock', 120);


INSERT INTO teatro (id_evento, obra, director)
VALUES 
(2, 'Hamlet', 'William Shakespeare');


INSERT INTO museo (id_evento, nombre_exposicion, tipo_exposicion, fecha_fin)
VALUES 
(3, 'Arte Moderno 2026', 'Pintura', '2026-09-01');


INSERT INTO asiento (id_lugar, fila, numero_asiento, zona)
VALUES 
(1, 'A', 1, 'VIP'),
(1, 'A', 2, 'VIP'),
(2, 'B', 10, 'General'),
(3, 'C', 5, 'General');


INSERT INTO ticket (id_evento, id_asiento, tipo, precio)
VALUES 
(1, 1, 'VIP', 100.00),
(1, 2, 'VIP', 100.00),
(2, 3, 'General', 50.00),
(3, 4, 'General', 30.00);


INSERT INTO compra (id_usuario, fecha, total)
VALUES 
(1, NOW(), 200.00);


INSERT INTO detalle_compra (id_compra, id_ticket, cantidad, precio_unitario)
VALUES 
(1, 1, 1, 100.00),
(1, 2, 1, 100.00);