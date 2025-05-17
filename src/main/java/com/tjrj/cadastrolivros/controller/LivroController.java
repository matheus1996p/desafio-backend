package com.tjrj.cadastrolivros.controller;

import java.util.List;

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

import com.tjrj.cadastrolivros.entity.Livro;
import com.tjrj.cadastrolivros.repository.LivroRepository;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Livro> lista = repository.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar livros.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Livro livro) {
        try {
            Livro salvo = repository.save(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest()
                    .body("Erro ao salvar livro: verifique se todos os autores e assuntos existem.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar livro.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        return repository.findById(id)
                .map(existente -> {
                    try {
                        livro.setId(id);
                        Livro atualizado = repository.save(livro);
                        return ResponseEntity.ok(atualizado);
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erro ao atualizar livro.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return repository.findById(id)
                .map(existente -> {
                    try {
                        repository.delete(existente);
                        return ResponseEntity.noContent().build();
                    } catch (DataIntegrityViolationException ex) {
                        return ResponseEntity.badRequest()
                                .body("Não é possível excluir: livro vinculado a autores ou assuntos.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado."));
    }
}