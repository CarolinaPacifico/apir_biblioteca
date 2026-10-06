package com.github.carolinapacifico.apir_biblioteca.controller;

import com.github.carolinapacifico.apir_biblioteca.model.Cliente;
import com.github.carolinapacifico.apir_biblioteca.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        cliente.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return clienteRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id,
                                          @RequestBody Cliente cliente) {

        Optional<Cliente> optCliente = clienteRepository.findById(id);

        if (optCliente.isPresent()) {
            cliente.setId(id);
            Cliente clienteAlterado = clienteRepository.save(cliente);
            return ResponseEntity.ok(clienteAlterado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
