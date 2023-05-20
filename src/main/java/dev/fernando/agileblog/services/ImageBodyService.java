package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.ImageBodyModel;
import org.springframework.stereotype.Service;

public interface ImageBodyService {
    void save(ImageBodyModel imageBodyModel);
}
