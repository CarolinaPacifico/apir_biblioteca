package com.github.carolinapacifico.apir_biblioteca.controller;

import com.github.carolinapacifico.apir_biblioteca.model.Livro;
import com.github.carolinapacifico.apir_biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroRepository livroRepository;

    @PostMapping
    public ResponseEntity<Livro> create(@RequestBody Livro livro) {
        livro.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroRepository.save(livro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        return livroRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.ok(livroRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id,
                                         @RequestBody Livro livro) {

        Optional<Livro> optLivro = livroRepository.findById(id);

        if (optLivro.isPresent()) {
            livro.setId(id);
            Livro livroAlterado = livroRepository.save(livro);
            return ResponseEntity.ok(livroAlterado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
