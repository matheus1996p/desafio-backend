package com.tjrj.cadastrolivros.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tjrj.cadastrolivros.dto.AutorDTO;
import com.tjrj.cadastrolivros.entity.Autor;
import com.tjrj.cadastrolivros.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;

    public List<AutorDTO> listarTodos() {
        return repository.findAll().stream()
                .map(AutorDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AutorDTO> buscarPorId(Long id) {
        return repository.findById(id).map(AutorDTO::new);
    }

    @Transactional
    public AutorDTO salvar(AutorDTO dto) {
        Autor salvo = repository.save(dto.toEntity());
        return new AutorDTO(salvo);
    }

    @Transactional
    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        existente.setNome(dto.getNome());
        return new AutorDTO(repository.save(existente));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
