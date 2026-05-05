CREATE TABLE livros (
    id BIGINT NOT NULL,
    nome_livro VARCHAR(255) NOT NULL,
    genero VARCHAR(100),
    autor VARCHAR(255),
    qtd_paginas VARCHAR(50),
    PRIMARY KEY (id)
);