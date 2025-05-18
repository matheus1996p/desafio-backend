package com.tjrj.cadastrolivros.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjrj.cadastrolivros.dto.AssuntoDTO;
import com.tjrj.cadastrolivros.entity.Assunto;
import com.tjrj.cadastrolivros.repository.AssuntoRepository;

@Service
public class AssuntoService {

    @Autowired
    private AssuntoRepository repository;

    public List<AssuntoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(AssuntoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AssuntoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(AssuntoDTO::new);
    }

    public AssuntoDTO criar(AssuntoDTO dto) {
        Assunto salvo = repository.save(dto.toEntity());
        return new AssuntoDTO(salvo);
    }

    public AssuntoDTO atualizar(Long id, AssuntoDTO dto) {
        Assunto existente = repository.findById(id).orElseThrow();
        existente.setDescricao(dto.getDescricao());
        return new AssuntoDTO(repository.save(existente));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
