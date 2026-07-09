CREATE TABLE hibernate_sequence (
       next_val bigint
    );
CREATE TABLE opcao_carne (
       id  integer,
        nome varchar(255),
        quantidadeDisponivel integer not null,
        primary key (id)
    );
CREATE TABLE refeicao (
       id  integer,
        capacidadeMaxima integer not null,
        data date,
        descricao varchar(255),
        preco float(19) not null,
        primary key (id)
    );
CREATE TABLE refeicao_carne (
       refeicao_id integer not null,
        carne_id integer not null,
        primary key (refeicao_id, carne_id)
    );
CREATE TABLE usuario (
       tipo_usuario varchar(31) not null,
        id integer not null,
        nome varchar(255),
        senha varchar(255),
        matricula varchar(255),
        primary key (id)
    );
CREATE TABLE pedido (
       id  integer,
        data_hora_criacao datetime not null,
        status varchar(255) not null,
        tipo_consumo varchar(255) not null,
        cliente_id integer not null,
        opcao_carne_id integer not null,
        refeicao_id integer not null,
        primary key (id)
    );
CREATE TABLE comentario (
       id integer not null,
        data date,
        descricao varchar(255),
        nota integer not null,
        cliente_id integer not null,
        primary key (id)
    );
