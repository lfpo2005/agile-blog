package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.DictionaryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface  DictionaryService {

    Page<DictionaryModel> findAll(Specification<DictionaryModel> spec, Pageable pageable);
    Optional<DictionaryModel> getDictionaryById(UUID dictionaryId);
    DictionaryModel createDictionary(DictionaryModel dictionaryModel);
    DictionaryModel updateDictionary(DictionaryModel dictionaryModel);
    boolean deleteDictionary(UUID dictionaryId);
}
