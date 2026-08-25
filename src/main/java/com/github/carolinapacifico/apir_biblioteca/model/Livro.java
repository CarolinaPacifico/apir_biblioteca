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
@Table(name = "livros")
public class Livro {

    @Id
    private Long id;

    @Column(name = "nome_livro")
    private String nome_livro;

    @Column(name = "genero")
    private String genero;

    @Column(name = "autor")
    private String autor;

    @Column(name = "qtd_paginas")
    private String qtd_paginas;
}
