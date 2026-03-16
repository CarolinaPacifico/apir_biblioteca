package com.github.carolinapacifico.apir_biblioteca.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/biblioteca")
public class LivroController {

    @GetMapping ("/livro/{livro}")
    public String situacaoLivro(@PathVariable String livro){
        return "Livro " + livro + " disponível!";

    }
}
