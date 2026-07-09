-- =========================================================
-- SEED DE DADOS DE TESTE - Issue #29
-- Apenas Administrador e Clientes de demonstração.
-- Uso: sqlite3 database/cantina.db < database/seed.sql
-- =========================================================

INSERT INTO usuario (tipo_usuario, id, nome, senha, matricula)
VALUES (
    'ADMINISTRADOR',
    999,
    'Verônica',
    '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
    NULL
);

-- senha: senha123
INSERT INTO usuario (tipo_usuario, id, nome, senha, matricula)
VALUES (
    'CLIENTE',
    1,
    'Ana Borges',
    '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251',
    '2023001'
);

-- senha: senha456
INSERT INTO usuario (tipo_usuario, id, nome, senha, matricula)
VALUES (
    'CLIENTE',
    2,
    'Bruno Costa',
    '6b08d780140e292a4af8ba3f2333fc1357091442d7e807c6cad92e8dcd0240b7',
    '2023002'
);

-- senha: senha789
INSERT INTO usuario (tipo_usuario, id, nome, senha, matricula)
VALUES (
    'CLIENTE',
    3,
    'Carla Dias',
    'b578dc5fcbfabbc7e96400601d0858c951f04929faef033bbbc117ab935c6ae9',
    '2023003'
);
