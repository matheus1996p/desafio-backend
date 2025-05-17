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

import com.tjrj.cadastrolivros.entity.Assunto;
import com.tjrj.cadastrolivros.repository.AssuntoRepository;

@RestController
@RequestMapping("/assuntos")
public class AssuntoController {

    @Autowired
    private AssuntoRepository repository;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Assunto> lista = repository.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar assuntos.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Assunto assunto) {
        try {
            Assunto salvo = repository.save(assunto);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest()
                    .body("Erro ao salvar assunto: dados inválidos ou já existentes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar assunto.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Assunto assunto) {
        return repository.findById(id)
                .map(existente -> {
                    try {
                        assunto.setId(id);
                        Assunto atualizado = repository.save(assunto);
                        return ResponseEntity.ok(atualizado);
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erro ao atualizar assunto.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assunto não encontrado."));
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
                                .body("Não é possível excluir: assunto vinculado a livros.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assunto não encontrado."));
    }
}
