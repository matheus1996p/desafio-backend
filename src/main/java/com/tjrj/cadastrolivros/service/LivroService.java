package com.tjrj.cadastrolivros.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjrj.cadastrolivros.dto.LivroDTO;
import com.tjrj.cadastrolivros.entity.Assunto;
import com.tjrj.cadastrolivros.entity.Autor;
import com.tjrj.cadastrolivros.entity.Livro;
import com.tjrj.cadastrolivros.repository.AssuntoRepository;
import com.tjrj.cadastrolivros.repository.AutorRepository;
import com.tjrj.cadastrolivros.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    public Livro salvarOuAtualizar(LivroDTO dto) {
        Livro livro = dto.getId() != null ?
            livroRepository.findById(dto.getId()).orElse(new Livro()) :
            new Livro();

        livro.setTitulo(dto.getTitulo());
        livro.setEditora(dto.getEditora());
        livro.setEdicao(dto.getEdicao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setValor(dto.getValor());

        List<Autor> autores = autorRepository.findAllById(dto.getAutores());
        livro.setAutores(autores);

        List<Assunto> assuntos = assuntoRepository.findAllById(dto.getAssuntos());
        livro.setAssuntos(assuntos);

        return livroRepository.save(livro);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public void excluir(Long id) {
        livroRepository.deleteById(id);
    }
}

