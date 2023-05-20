package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.ImageBodyModel;
import dev.fernando.agileblog.repositories.ImageBodyRepository;
import dev.fernando.agileblog.services.ImageBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageBodyServiceImpl implements ImageBodyService {

    @Autowired
    ImageBodyRepository imageBodyRepository;

    @Override
    public void save(ImageBodyModel imageBodyModel) {
        imageBodyRepository.save(imageBodyModel);
    }
}
