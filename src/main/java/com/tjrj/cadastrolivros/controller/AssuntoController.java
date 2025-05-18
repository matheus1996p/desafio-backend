package com.tjrj.cadastrolivros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.tjrj.cadastrolivros.dto.AssuntoDTO;
import com.tjrj.cadastrolivros.service.AssuntoService;

@RestController
@RequestMapping("/assuntos")
public class AssuntoController {

     @Autowired
    private AssuntoService service;

    @GetMapping
    public ResponseEntity<List<AssuntoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<AssuntoDTO> assuntoOpt = service.buscarPorId(id);
            if (assuntoOpt.isPresent()) {
                return ResponseEntity.ok(assuntoOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Assunto não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erro ao buscar assunto.");
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AssuntoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro ao criar: descrição já cadastrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar assunto.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AssuntoDTO dto) {
        try {
            return ResponseEntity.ok(service.atualizar(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar assunto.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Não é possível excluir: assunto vinculado a livros.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir assunto.");
        }
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Long> contar() {
        try {
            long count = service.listarTodos().size();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
