package com.tjrj.cadastrolivros.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "vw_relatorio_livros_autor")
public class LivroAutorView {

    @Id
    @Column(name = "cod_livro")
    private Long codLivro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "nome_autor")
    private String nomeAutor;

    @Column(name = "descricao")
    private String descricaoAssunto;

    @Column(name = "valor")
    private BigDecimal valor;
}
