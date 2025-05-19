package com.tjrj.cadastrolivros.controller;

import java.util.List;
import java.util.Optional;

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

import com.tjrj.cadastrolivros.dto.AutorDTO;
import com.tjrj.cadastrolivros.service.AutorService;

@RestController
@RequestMapping("/autores")
public class AutorController {

     @Autowired
    private AutorService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<AutorDTO> autores = service.listarTodos();
            return ResponseEntity.ok(autores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar autores.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<AutorDTO> autorOpt = service.buscarPorId(id);
            if (autorOpt.isPresent()) {
                return ResponseEntity.ok(autorOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Autor não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erro ao buscar autor.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AutorDTO dto) {
        try {
            AutorDTO salvo = service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException ex) {
            return ResponseEntity.badRequest()
                    .body("Erro ao salvar autor: dados inválidos ou já existentes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao salvar autor.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AutorDTO dto) {
        try {
            AutorDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar autor.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest()
                    .body("Não é possí­vel excluir: autor vinculado a livros.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir autor.");
        }
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Long> contarLivros() {
        try {
            long count = service.listarTodos().size();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
