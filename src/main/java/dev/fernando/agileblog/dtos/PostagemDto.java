package dev.fernando.agileblog.dtos;

import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.models.PostModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PostagemDto {

    private UUID postagemId;

    private String titulo;

     private String conteudo;

    private String description;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private String imageUrl;

    private CategoryModel category;

    public PostModel convertToUserModel(){
        var postagemModel = new PostModel();
        BeanUtils.copyProperties(this, postagemModel);
        return postagemModel;
    }

}
