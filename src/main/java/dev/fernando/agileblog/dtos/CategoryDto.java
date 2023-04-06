package dev.fernando.agileblog.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {

   private UUID categoryId;

    private String name;

}
