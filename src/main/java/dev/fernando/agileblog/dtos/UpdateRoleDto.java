package dev.fernando.agileblog.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UpdateRoleDto {
    @NotNull
    private UUID userId;
}
