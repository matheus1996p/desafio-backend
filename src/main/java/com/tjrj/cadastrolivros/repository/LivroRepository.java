package com.tjrj.cadastrolivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjrj.cadastrolivros.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {}