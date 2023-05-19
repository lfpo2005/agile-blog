package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.ImageBodyDto;
import dev.fernando.agileblog.models.ImageBodyModel;
import dev.fernando.agileblog.services.ImageBodyService;
import dev.fernando.agileblog.util.ConvertImage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageBodyController {

    @Autowired
    ImageBodyService imageBodyService;

    @PostMapping(value = "/upload-body-img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadBodyImage(@RequestParam("postImages") MultipartFile[] images, ImageBodyDto imageBodyDto) {
        try {
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile image : images) {
                String imagePath = ConvertImage.convertImagePostBody(image, 400, 300, "src/main/resources/static/img/body-post");
                imagePaths.add(imagePath);
            }
            ImageBodyModel imageBodyModel = new ImageBodyModel();
            BeanUtils.copyProperties(imageBodyDto, imageBodyModel);

            imageBodyService.save(imageBodyModel);

            // Retorne os caminhos ou URLs das imagens carregadas
            return ResponseEntity.ok().body(imagePaths);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}






