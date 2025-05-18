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

import com.tjrj.cadastrolivros.dto.LivroDTO;
import com.tjrj.cadastrolivros.entity.Livro;
import com.tjrj.cadastrolivros.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Livro> lista = livroService.listarTodos();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar livros.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Livro livro = livroService.buscarPorId(id);
            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livro não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody LivroDTO dto) {
        try {
            Livro salvo = livroService.salvarOuAtualizar(dto);
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
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LivroDTO dto) {
        try {
            dto.setId(id);
            Livro atualizado = livroService.salvarOuAtualizar(dto);
            return ResponseEntity.ok(atualizado);
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest()
                    .body("Erro ao atualizar livro: dados inválidos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao atualizar livro.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            livroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest()
                    .body("Não é possível excluir: livro vinculado a autores ou assuntos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Livro não encontrado.");
        }
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Long> contarLivros() {
        try {
            long count = livroService.listarTodos().size();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}