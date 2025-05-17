package com.tjrj.cadastrolivros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjrj.cadastrolivros.entity.Autor;
import com.tjrj.cadastrolivros.repository.AutorRepository;

@RestController
@RequestMapping("/autores")
public class AutorController {

     @Autowired
    private AutorRepository repository;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar autores.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Autor autor) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(autor));
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest().body("Erro ao salvar autor: dados inválidos ou já existentes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado ao salvar autor.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Autor autor) {
        return repository.findById(id)
                .map(existing -> {
                    try {
                        autor.setId(id);
                        return ResponseEntity.ok(repository.save(autor));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar autor.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    try {
                        repository.delete(existing);
                        return ResponseEntity.noContent().build();
                    } catch (DataIntegrityViolationException ex) {
                        return ResponseEntity.badRequest().body("Não é possível excluir: autor vinculado a livros.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado."));
    }
}
