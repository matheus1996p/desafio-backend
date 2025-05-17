package com.tjrj.cadastrolivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjrj.cadastrolivros.entity.Assunto;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {}
