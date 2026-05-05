CREATE TABLE clientes (
    id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nome_livro VARCHAR(255),
    duracao_aluguel VARCHAR(100),
    telefone VARCHAR(50),
    PRIMARY KEY (id)
);