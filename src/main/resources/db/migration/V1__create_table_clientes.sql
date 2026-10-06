CREATE TABLE clientes (
    id BIGINT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nome_livro VARCHAR(255),
    duracao_aluguel VARCHAR(100),
    telefone VARCHAR(50),
    CONSTRAINT pk_clientes PRIMARY KEY (id)
);
