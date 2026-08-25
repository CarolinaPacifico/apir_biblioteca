package com.github.carolinapacifico.apir_biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nome_livro")
    private String nome_livro;

    @Column(name = "duracao_aluguel")
    private String duracao_aluguel;

    @Column(name = "telefone")
    private String telefone;
}