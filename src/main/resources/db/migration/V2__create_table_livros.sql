CREATE TABLE livros (
    id BIGINT IDENTITY(1,1) NOT NULL,
    nome_livro VARCHAR(255) NOT NULL,
    genero VARCHAR(100),
    autor VARCHAR(255),
    qtd_paginas VARCHAR(50),
    CONSTRAINT pk_livros PRIMARY KEY (id)
);
