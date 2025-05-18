package com.tjrj.cadastrolivros.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class LivroDTO {
    private Long id;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private BigDecimal valor;

    private List<Long> autores;  // apenas IDs
    private List<Long> assuntos; // apenas IDs
}
