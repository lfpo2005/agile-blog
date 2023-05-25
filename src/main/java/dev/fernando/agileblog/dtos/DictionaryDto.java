package dev.fernando.agileblog.dtos;

import dev.fernando.agileblog.models.DictionaryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDto {

    private UUID dictionaryId;
    private String word;
    private String link;
    private String reference;
    private int searchCount;
    public DictionaryDto(DictionaryModel dictionaryModel) {
    }
}
