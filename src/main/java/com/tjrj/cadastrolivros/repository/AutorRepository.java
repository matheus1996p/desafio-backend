package com.tjrj.cadastrolivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjrj.cadastrolivros.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {}
