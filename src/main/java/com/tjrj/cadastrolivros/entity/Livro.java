package com.tjrj.cadastrolivros.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "livro", schema = "public")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_livro")
    private Long id;

    private String titulo;
    private String editora;
    private Integer edicao;

    @Column(name = "ano_publicacao")
    private String anoPublicacao;

    private BigDecimal valor;

    @ManyToMany
    @JoinTable(name = "livro_autor",
                schema = "public",
                joinColumns = @JoinColumn(name = "cod_livro"),
                inverseJoinColumns = @JoinColumn(name = "cod_autor"))
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(name = "livro_assunto",
            schema = "public",
            joinColumns = @JoinColumn(name = "cod_livro"),
            inverseJoinColumns = @JoinColumn(name = "cod_assunto"))
    private List<Assunto> assuntos;
}
