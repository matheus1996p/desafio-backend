package com.tjrj.cadastrolivros.dto;

import com.tjrj.cadastrolivros.entity.Autor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutorDTO {
  private Long id;
    private String nome;

    public AutorDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
    }

    public Autor toEntity() {
        Autor autor = new Autor();
        autor.setId(this.id);
        autor.setNome(this.nome);
        return autor;
    }
}
