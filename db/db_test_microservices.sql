-- tabla Persona
CREATE TABLE IF NOT EXISTS persona (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    edad INT,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

--  tabla Persona
CREATE TABLE IF NOT EXISTS cliente (
    cliente_id BIGSERIAL PRIMARY KEY,
    persona_id BIGINT NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (persona_id) REFERENCES persona(id)
);

--  tabla Cuenta
CREATE TABLE IF NOT EXISTS cuenta (
    numero_cuenta BIGSERIAL PRIMARY KEY,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DOUBLE PRECISION NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

-- Crear la tabla Movimientos
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGSERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    saldo DOUBLE PRECISION NOT NULL,
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(numero_cuenta)
);

 --- INSERT EXAMPLES 
INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono) VALUES 
('Jose Lema', 'Masculino', 26, '1709334325', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 27, '1400878523', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 30, '1400767896', '13 junio y Equinoccial', '098874587');


INSERT INTO cliente (persona_id, contrasena, estado) VALUES 
((SELECT id FROM persona WHERE identificacion='1709334325'), '1234', TRUE),
((SELECT id FROM persona WHERE identificacion='1400878523'), '5678', TRUE),
((SELECT id FROM persona WHERE identificacion='1400767896'), '1245', TRUE);


INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES 
(478758, 'Ahorros', 2000.00, TRUE, (SELECT cliente_id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1709334325'))),
(225487, 'Corriente', 100.00, TRUE, (SELECT cliente_id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1400878523'))),
(495878, 'Ahorros', 0.00, TRUE, (SELECT cliente_id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1400767896'))),
(496825, 'Ahorros', 540.00, TRUE, (SELECT cliente_id FROM cliente WHERE persona_id=(SELECT id FROM persona WHERE identificacion='1400878523')));

