package dev.fernando.agileblog.dtos;

import lombok.Data;
import java.util.UUID;
@Data
public class ImageBodyDto {

    private UUID imgId;
    private String img;
    private String alt;
}
