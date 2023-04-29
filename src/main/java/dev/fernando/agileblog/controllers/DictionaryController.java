package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.DictionaryDto;
import dev.fernando.agileblog.models.DictionaryModel;
import dev.fernando.agileblog.services.DictionaryService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/dictionaries")
public class DictionaryController {


    @Autowired
    DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<Page<DictionaryModel>> getAllDictionaries(SpecificationTemplate.DictionarySpec spec,
                                                                    @PageableDefault(page = 0, size = 10, sort = "dictionaryId",
                                                                          direction = Sort.Direction.ASC) Pageable pageable) {
        Page<DictionaryModel> dictionaryModelPage = dictionaryService.findAll(spec, pageable);
        if (!dictionaryModelPage.isEmpty()){
            for (DictionaryModel dictionary : dictionaryModelPage.toList()) {
                dictionary.add(linkTo(methodOn(DictionaryController.class).getOneDictionary(dictionary.getDictionaryId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(dictionaryModelPage);
    }

    @GetMapping("/{dictionaryId}")
    public ResponseEntity<Object> getOneDictionary(@PathVariable(value = "dictionaryId") UUID dictionaryId) {

        Optional<DictionaryModel> dictionaryModelOptional = dictionaryService.getDictionaryById(dictionaryId);
        if (!dictionaryModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Palavra não existe no dicionário.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(dictionaryModelOptional.get());

    }

    @PostMapping
    public ResponseEntity<DictionaryDto> createDictionary(@RequestBody DictionaryDto dictionaryDto) {
        DictionaryModel dictionaryModel = convertDtoToModel(dictionaryDto);
        DictionaryModel createdDictionaryModel = dictionaryService.createDictionary(dictionaryModel);
        DictionaryDto createdDictionaryDto = convertModelToDto(createdDictionaryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDictionaryDto);
    }

    @PutMapping("/{dictionaryId}")
    public ResponseEntity<DictionaryDto> updateDictionary(@PathVariable UUID dictionaryId,
                                                          @RequestBody DictionaryDto dictionaryDto) {
        DictionaryModel dictionaryModel = convertDtoToModel(dictionaryDto);
        dictionaryModel.setDictionaryId(dictionaryId);
        DictionaryModel updatedDictionaryModel = dictionaryService.updateDictionary(dictionaryModel);
        if (updatedDictionaryModel == null) {
            return ResponseEntity.notFound().build();
        } else {
            DictionaryDto updatedDictionaryDto = convertModelToDto(updatedDictionaryModel);
            return ResponseEntity.ok(updatedDictionaryDto);
        }
    }

    @DeleteMapping("/{dictionaryId}")
    public ResponseEntity<Void> deleteDictionary(@PathVariable UUID dictionaryId) {
        boolean deleted = dictionaryService.deleteDictionary(dictionaryId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private DictionaryDto convertModelToDto(DictionaryModel dictionaryModel) {
        DictionaryDto dictionaryDto = new DictionaryDto(dictionaryModel);
        BeanUtils.copyProperties(dictionaryModel, dictionaryDto);
        return dictionaryDto;
    }

    private List<DictionaryDto> convertModelListToDtoList(List<DictionaryModel> dictionaryModels) {
        return dictionaryModels.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }

    private DictionaryModel convertDtoToModel(DictionaryDto dictionaryDto) {
        DictionaryModel dictionaryModel = new DictionaryModel();
        BeanUtils.copyProperties(dictionaryDto, dictionaryModel);
        return dictionaryModel;
    }
}