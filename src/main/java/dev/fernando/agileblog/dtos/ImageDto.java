package dev.fernando.agileblog.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {

    private UUID imageId;

    private String imageMin;
}
