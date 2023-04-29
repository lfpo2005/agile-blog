package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.DictionaryModel;
import dev.fernando.agileblog.repositories.DictionaryRepository;
import dev.fernando.agileblog.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Override
    public Page<DictionaryModel> findAll(Specification<DictionaryModel> spec, Pageable pageable) {
        return dictionaryRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<DictionaryModel> getDictionaryById(UUID dictionaryId) {
        return dictionaryRepository.findById(dictionaryId);
    }

    @Override
    public DictionaryModel createDictionary(DictionaryModel dictionaryModel) {
        return dictionaryRepository.save(dictionaryModel);
    }

    @Override
    public DictionaryModel updateDictionary(DictionaryModel dictionaryModel) {
        if (!dictionaryRepository.existsById(dictionaryModel.getDictionaryId())) {
            return null;
        }
        return dictionaryRepository.save(dictionaryModel);
    }

    @Override
    public boolean deleteDictionary(UUID dictionaryId) {
        if (!dictionaryRepository.existsById(dictionaryId)) {
            return false;
        }
        dictionaryRepository.deleteById(dictionaryId);
        return true;
    }
}
