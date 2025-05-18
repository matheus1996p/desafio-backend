package com.tjrj.cadastrolivros.dto;

import com.tjrj.cadastrolivros.entity.Assunto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssuntoDTO {
    private Long id;
    private String descricao;

    public AssuntoDTO(Assunto assunto) {
        this.id = assunto.getId();
        this.descricao = assunto.getDescricao();
    }

    public Assunto toEntity() {
        Assunto assunto = new Assunto();
        assunto.setId(this.id);
        assunto.setDescricao(this.descricao);
        return assunto;
    }
}
