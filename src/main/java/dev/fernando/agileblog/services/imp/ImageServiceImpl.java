package dev.fernando.agileblog.services.imp;


import dev.fernando.agileblog.models.ImageModel;
import dev.fernando.agileblog.repositories.ImageRepository;
import dev.fernando.agileblog.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public ImageModel save(ImageModel imageModel) {
        return imageRepository.save(imageModel);
    }
}
